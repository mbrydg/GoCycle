package model;

import faults.FaultTarget;
import faults.StatusObserver;
import faults.StatusReport;
import faults.StatusReportable;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements FaultTarget, StatusReportable {

    public enum Status {
        AVAILABLE,
        IN_USE,
        OUT_OF_SERVICE
    }

    private final String id;
    private Status status;
    private DockingPoint dockingPoint;

    private final List<StatusObserver> observers = new ArrayList<>();

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

    @Override
    public void attachStatusObserver(StatusObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeStatusObserver(StatusObserver observer) {
        observers.remove(observer);
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

        StatusReport status = new StatusReport(StatusReport.Source.DOCKINGPOINT,
                this.getId(),statusString.toString());
        for (StatusObserver observer : new ArrayList<>(observers)) {
            observer.onStatusReported(status);
        }
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
        notifyStatusObservers();
    }

    public void markInUse() {
        if (!canBeRented() || isDocked()) {
            throw new IllegalStateException("Vehicle is not available to rent");
        }

        status = Status.IN_USE;
        notifyStatusObservers();
    }

    public void markAvailable() {
        if (status != Status.OUT_OF_SERVICE) {
            status = Status.AVAILABLE;
            notifyStatusObservers();
        }
    }

    @Override
    public void markOutOfService() {
        status = Status.OUT_OF_SERVICE;
        notifyStatusObservers();
    }

    public abstract String getVehicleType();
}
