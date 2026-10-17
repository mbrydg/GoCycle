package faults;

public class FaultTest {

    // Simple stand-in vehicle used only for testing
    static class TestVehicle implements FaultTarget {
        private final String id;
        private boolean outOfService = false;

        TestVehicle(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void markOutOfService() {
            outOfService = true;
        }

        boolean isOutOfService() {
            return outOfService;
        }
    }

    private static void check(String name, boolean condition) {
        if (!condition) {
            throw new AssertionError(name + " failed.");
        }
        System.out.println("PASS: " + name);
    }

    public static void main(String[] args) {

        // Test 1: both observers are notified
        FaultNotifier notifier1 = new FaultNotifier();
        MaintenanceCrew crew1 = new MaintenanceCrew();
        ControlRoom room1 = new ControlRoom();
        notifier1.attach(crew1);
        notifier1.attach(room1);
        notifier1.reportFault(new FaultReport("B1", "Flat tire", FaultReport.Source.RIDER));
        check("Maintenance crew is notified", crew1.getServiceTickets().size() == 1);
        check("Control room is notified", room1.getAlerts().size() == 1);

        // Test 2: faulty vehicle is taken out of service
        FaultNotifier notifier2 = new FaultNotifier();
        OutOfServiceHandler handler2 = new OutOfServiceHandler();
        TestVehicle bike2 = new TestVehicle("B1");
        handler2.register(bike2);
        notifier2.attach(handler2);
        notifier2.reportFault(new FaultReport("B1", "Broken brakes", FaultReport.Source.RIDER));
        check("Faulty vehicle is taken out of service", bike2.isOutOfService());

        // Test 3: a fault on another vehicle does not affect this one
        FaultNotifier notifier3 = new FaultNotifier();
        OutOfServiceHandler handler3 = new OutOfServiceHandler();
        TestVehicle bike3 = new TestVehicle("B1");
        handler3.register(bike3);
        notifier3.attach(handler3);
        notifier3.reportFault(new FaultReport("B2", "Low battery", FaultReport.Source.VEHICLE));
        check("Other vehicle is not affected", !bike3.isOutOfService());

        // Test 4: a detached observer is no longer notified
        FaultNotifier notifier4 = new FaultNotifier();
        MaintenanceCrew crew4 = new MaintenanceCrew();
        notifier4.attach(crew4);
        notifier4.detach(crew4);
        notifier4.reportFault(new FaultReport("B1", "Flat tire", FaultReport.Source.RIDER));
        check("Detached observer is not notified", crew4.getServiceTickets().isEmpty());

        // Test 5: a vehicle can report its own fault
        FaultNotifier notifier5 = new FaultNotifier();
        ControlRoom room5 = new ControlRoom();
        notifier5.attach(room5);
        notifier5.reportFault(new FaultReport("E1", "Battery fault", FaultReport.Source.VEHICLE));
        check("Vehicle can report its own fault", room5.getAlerts().size() == 1);

        System.out.println("All fault tests passed!");
    }
}
