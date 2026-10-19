package model;

import pricing.TripCostCalculator;

public class Trip {

    private final Rider rider;
    private final Vehicle vehicle;
    private final TripCostCalculator costCalculator;
    private final double depositAmount;

    private long startTime;
    private long endTime;
    private boolean active;
    private boolean depositHeld;
    private boolean depositReleased;
    private double finalCost;

    public Trip(
            Rider rider,
            Vehicle vehicle,
            TripCostCalculator costCalculator,
            double depositAmount
    ) {
        if (rider == null || vehicle == null || costCalculator == null) {
            throw new IllegalArgumentException("Rider, vehicle, and calculator are required");
        }

        if (!Double.isFinite(depositAmount) || depositAmount < 0) {
            throw new IllegalArgumentException("Deposit must be a non-negative amount");
        }

        this.rider = rider;
        this.vehicle = vehicle;
        this.costCalculator = costCalculator;
        this.depositAmount = depositAmount;
        this.active = false;
        this.depositHeld = false;
        this.depositReleased = false;
    }

    public Rider getRider() {
        return rider;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isDepositHeld() {
        return depositHeld;
    }

    public boolean isDepositReleased() {
        return depositReleased;
    }

    public void beginTrip(DockingPoint pickupPoint) {
        if (active || startTime != 0) {
            throw new IllegalStateException("Trip has already started");
        }

        if (pickupPoint == null || pickupPoint.getVehicle() != vehicle) {
            throw new IllegalArgumentException("Vehicle must be at the pickup point");
        }

        if (!vehicle.canBeRented()) {
            throw new IllegalStateException("Vehicle is unavailable or has insufficient battery");
        }

        // The deposit hold is recorded before unlocking.
        depositHeld = true;

        pickupPoint.releaseVehicle();
        vehicle.markInUse();

        startTime = System.currentTimeMillis();
        active = true;
    }

    public double endTrip(DockingPoint returnPoint) {
        if (!active) {
            throw new IllegalStateException("Trip is not active");
        }

        if (returnPoint == null || !returnPoint.isFree()) {
            throw new IllegalStateException("Return point must be free");
        }

        // Docking succeeds before the trip ends or charging stops.
        returnPoint.dockVehicle(vehicle);

        endTime = System.currentTimeMillis();

        long elapsedMillis = endTime - startTime;
        int minutes = (int) Math.ceil(elapsedMillis/60000.0);

        finalCost = costCalculator.calculateCost(minutes,rider.getPricingStrategy());

        active = false;
        depositReleased = depositHeld;
        depositHeld = false;

        // A faulty vehicle remains out of service.
        vehicle.markAvailable();

        return finalCost;
    }

    public int getDurationMinutes() {
        if (startTime == 0) {
            return 0;
        }

        long finish;

	if (active) {
		finish = System.currentTimeMillis();
	} else {
    		finish = endTime;
	}

        return (int) Math.ceil((finish - startTime)/60000.0);
    }

    public double getFinalCost() {
        if (active || startTime == 0 || endTime == 0) {
            throw new IllegalStateException("Trip has not ended");
        }

        return finalCost;
    }
}
