package model;

public class ElectricBike extends ElectricVehicle {

    public ElectricBike(String id, int batteryLevel) {
        super(id, batteryLevel);
    }

    @Override
    public String getVehicleType() {
        return "Electric Bike";
    }
}
