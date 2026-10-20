package model;

import faults.FaultNotifier;
import faults.FaultReport;
import faults.OutOfServiceHandler;
import pricing.CasualPricing;
import pricing.PricingStrategy;
import pricing.StudentPricing;
import pricing.TripCostCalculator;

public class ModelTest {

    private static void check(String name, boolean condition) {
        if (!condition) {
            throw new AssertionError(name + " failed.");
        }
        System.out.println("PASS: " + name);
    }

    private static void check(String name, double actual, double expected) {
        if (Math.abs(actual - expected) > 0.00001) {
            throw new AssertionError(name + " failed. Expected: "
                    + expected + ", Got: " + actual);
        }
        System.out.println("PASS: " + name);
    }

    // Returns true if the action throws an exception
    private static boolean fails(Runnable action) {
        try {
            action.run();
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Test 1: electric vehicles need at least 20% battery
        ElectricBike bike1 = new ElectricBike("E1", 19);
        ElectricScooter scooter1 = new ElectricScooter("E2", 20);
        check("Battery below 20% cannot be rented", !bike1.canBeRented());
        check("Battery at exactly 20% can be rented", scooter1.canBeRented());
        check("Pedal bike can always be rented", new PedalBike("P1").canBeRented());
        check("Battery above 100 is rejected",
                fails(() -> new ElectricBike("E3", 101)));

        // Test 2: an out-of-service vehicle cannot be rented
        PedalBike bike2 = new PedalBike("P2");
        bike2.markOutOfService();
        bike2.markAvailable();
        check("Out-of-service vehicle cannot be rented", !bike2.canBeRented());
        check("Vehicle stays out of service",
                bike2.getStatus() == Vehicle.Status.OUT_OF_SERVICE);

        // Test 3: a vehicle can be docked and released
        DockingStation station3 = new DockingStation("S3", "Downtown");
        DockingPoint point3 = new DockingPoint("S3-1");
        station3.addDockingPoint(point3);
        PedalBike bike3 = new PedalBike("P3");
        point3.dockVehicle(bike3);
        check("Docking point is occupied", point3.isOccupied());
        check("Vehicle knows its docking point", bike3.getDockingPoint() == point3);
        check("Released vehicle is returned", point3.releaseVehicle() == bike3);
        check("Docking point is free after release", point3.isFree());

        // Test 4: a docking point holds only one vehicle
        DockingStation station4 = new DockingStation("S4", "Uptown");
        DockingPoint point4 = new DockingPoint("S4-1");
        station4.addDockingPoint(point4);
        point4.dockVehicle(new PedalBike("P4"));
        check("Occupied point rejects a second vehicle",
                fails(() -> point4.dockVehicle(new PedalBike("P5"))));
        check("Full station has no free point", station4.findFreePoint() == null);
        check("Full station has no available capacity",
                station4.getAvailableCapacity() == 0);

        // Test 5: a rider needs a pricing strategy and can change it
        Rider rider5 = new Rider("R5", "Sam", new CasualPricing(0.30));
        PricingStrategy student5 = new StudentPricing(0.10);
        rider5.setPricingStrategy(student5);
        check("Rider pricing strategy can be changed",
                rider5.getPricingStrategy() == student5);
        check("Rider without a pricing strategy is rejected",
                fails(() -> new Rider("R6", "Alex", null)));

        // Test 6: a full trip charges the rider's rate and releases the deposit
        TripCostCalculator calculator = new TripCostCalculator(1.00, 5.00);
        DockingStation stationA = new DockingStation("A", "Station A");
        DockingStation stationB = new DockingStation("B", "Station B");
        DockingPoint pickup6 = new DockingPoint("A-1");
        DockingPoint return6 = new DockingPoint("B-1");
        stationA.addDockingPoint(pickup6);
        stationB.addDockingPoint(return6);

        PedalBike bike6 = new PedalBike("P6");
        pickup6.dockVehicle(bike6);
        Trip trip6 = new Trip(new Rider("R7", "Casual Rider", new CasualPricing(0.30)),
                bike6, calculator, 5.00);

        trip6.beginTrip(pickup6);
        check("Trip is active after it begins", trip6.isActive());
        check("Deposit is held during the trip", trip6.isDepositHeld());
        check("Vehicle is in use during the trip",
                bike6.getStatus() == Vehicle.Status.IN_USE);

        Thread.sleep(5);
        double cost6 = trip6.endTrip(return6);
        check("Casual trip cost", cost6, 1.30);
        check("Trip is not active after it ends", !trip6.isActive());
        check("Deposit is released after the trip", trip6.isDepositReleased());
        check("Vehicle is available after the trip",
                bike6.getStatus() == Vehicle.Status.AVAILABLE);
        check("Vehicle is docked at the return point", return6.getVehicle() == bike6);

        // Test 7: a vehicle that reports a fault during a trip stays out of service
        DockingPoint pickup7 = new DockingPoint("A-2");
        DockingPoint return7 = new DockingPoint("B-2");
        stationA.addDockingPoint(pickup7);
        stationB.addDockingPoint(return7);

        PedalBike bike7 = new PedalBike("P7");
        pickup7.dockVehicle(bike7);
        OutOfServiceHandler handler7 = new OutOfServiceHandler();
        handler7.register(bike7);
        FaultNotifier notifier7 = new FaultNotifier();
        notifier7.attach(handler7);

        Trip trip7 = new Trip(new Rider("R8", "Casual Rider", new CasualPricing(0.30)),
                bike7, calculator, 5.00);
        trip7.beginTrip(pickup7);
        notifier7.reportFault(new FaultReport("P7", "Broken brakes", FaultReport.Source.RIDER));
        trip7.endTrip(return7);
        check("Faulty vehicle stays out of service after the trip",
                bike7.getStatus() == Vehicle.Status.OUT_OF_SERVICE);
        check("Faulty vehicle cannot be rented again", !bike7.canBeRented());

        // Test 8: a low-battery scooter cannot start a trip
        DockingPoint pickup8 = new DockingPoint("A-3");
        stationA.addDockingPoint(pickup8);

        ElectricScooter scooter8 = new ElectricScooter("E8", 15);
        pickup8.dockVehicle(scooter8);
        Trip trip8 = new Trip(new Rider("R9", "Casual Rider", new CasualPricing(0.30)),
                scooter8, calculator, 5.00);
        check("Low-battery scooter cannot start a trip",
                fails(() -> trip8.beginTrip(pickup8)));
        check("Rejected trip is not active", !trip8.isActive());
        check("Rejected trip holds no deposit", !trip8.isDepositHeld());

        System.out.println("All model tests passed!");
    }
}
