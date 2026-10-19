package model;

import faults.FaultTarget;

public abstract class Vehicle implements FaultTarget {

    public enum Status {
        AVAILABLE,
        IN_USE,
        OUT_OF_SERVICE
    }

    private final String id;
    private Status status;
    private DockingPoint dockingPoint;

    protected Vehicle(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Vehicle ID is required");
        }

        this.id = id;
        this.status = Status.AVAILABLE;
    }

    @Override
    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public DockingPoint getDockingPoint() {
        return dockingPoint;
    }

    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }

    public boolean canBeRented() {
        return isAvailable();
    }

    public boolean isDocked() {
        return dockingPoint != null;
    }

    void setDockingPoint(DockingPoint dockingPoint) {
        this.dockingPoint = dockingPoint;
    }

    public void markInUse() {
        if (!canBeRented() || isDocked()) {
            throw new IllegalStateException("Vehicle is not available to rent");
        }

        status = Status.IN_USE;
    }

    public void markAvailable() {
        if (status != Status.OUT_OF_SERVICE) {
            status = Status.AVAILABLE;
        }
    }

    @Override
    public void markOutOfService() {
        status = Status.OUT_OF_SERVICE;
    }

    public abstract String getVehicleType();
}
