package model;

import faults.StatusObserver;
import faults.StatusReport;

import java.util.ArrayList;

public abstract class ElectricVehicle extends Vehicle {

    public static final int MinimumRentalBattery = 20;

    private int batteryLevel;

    protected ElectricVehicle(String id, int batteryLevel) {
        super(id);
        setBatteryLevel(batteryLevel);
    }

    @Override
    public void notifyStatusObservers() {
        StringBuilder statusString = new StringBuilder()
                .append(getId())
                .append(" | ")
                .append(getVehicleType())
                .append(" | ")
                .append("Status: ");
        String currStatus = "";
        switch (getStatus()) {
            case AVAILABLE -> currStatus = "Available";
            case IN_USE -> currStatus = "In Use";
            case OUT_OF_SERVICE -> currStatus = "Out of Service";
        }
        statusString.append(currStatus);

        statusString.append(" | ");
        statusString.append("Battery Level: ");
        statusString.append(getBatteryLevel());

        StatusReport status = new StatusReport(StatusReport.Source.E_VEHICLE,
                this.getId(),statusString.toString());
        for (StatusObserver observer : new ArrayList<>(observers)) {
            observer.onStatusReported(status);
        }
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Battery level must be between 0 and 100");
        }

        this.batteryLevel = batteryLevel;
        notifyStatusObservers();
    }

    public boolean hasSufficientBattery() {
        return batteryLevel >= MinimumRentalBattery;
    }

    @Override
    public boolean canBeRented() {
        return super.canBeRented() && hasSufficientBattery();
    }
}
