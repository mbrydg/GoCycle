package model;

public abstract class ElectricVehicle extends Vehicle {

    public static final int MinimumRentalBattery = 20;

    private int batteryLevel;

    protected ElectricVehicle(String id, int batteryLevel) {
        super(id);
        setBatteryLevel(batteryLevel);
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Battery level must be between 0 and 100");
        }

        this.batteryLevel = batteryLevel;
    }

    public boolean hasSufficientBattery() {
        return batteryLevel >= MinimumRentalBattery;
    }

    @Override
    public boolean canBeRented() {
        return super.canBeRented() && hasSufficientBattery();
    }
}
