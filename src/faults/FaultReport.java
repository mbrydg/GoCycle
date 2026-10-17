package faults;

public class FaultReport {

    public enum Source {
        RIDER,
        VEHICLE
    }

    private final String vehicleId;
    private final String description;
    private final Source source;

    public FaultReport(String vehicleId, String description, Source source) {
        this.vehicleId = vehicleId;
        this.description = description;
        this.source = source;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public Source getSource() {
        return source;
    }
}
