package model;

public class ElectricScooter extends ElectricVehicle {

    public ElectricScooter(String id, int batteryLevel) {
        super(id, batteryLevel);
    }

    @Override
    public String getVehicleType() {
        return "Electric Scooter";
    }
}
