package model;

public class PedalBike extends Vehicle {

    public PedalBike(String id) {
        super(id);
    }

    @Override
    public String getVehicleType() {
        return "Pedal Bike";
    }
}
