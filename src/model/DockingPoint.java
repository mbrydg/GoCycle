package model;

import faults.StatusObserver;
import faults.StatusReport;
import faults.StatusReportable;

import java.util.ArrayList;
import java.util.List;

public class DockingPoint implements StatusReportable {

    private final String id;
    private DockingStation station;
    private Vehicle vehicle;

    private final List<StatusObserver> observers = new ArrayList<>();

    public DockingPoint(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Docking point ID is required");
        }

        this.id = id;
    }

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
        StatusReport status = new StatusReport(StatusReport.Source.DOCKINGPOINT,
                this.getId(),
                new StringBuilder()
                        .append(getId())
                        .append(" | ")
                        .append("Location: ")
                        .append(getStation().getName())
                        .append(" | ")
                        .append(isOccupied() ? getVehicle().getVehicleType() : "None")
                        .toString());
        for (StatusObserver observer : new ArrayList<>(observers)) {
            observer.onStatusReported(status);
        }
    }

    public DockingStation getStation() {
        return station;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isOccupied() {
        return vehicle != null;
    }

    public boolean isFree() {
        return vehicle == null;
    }

    void setStation(DockingStation station) {
        if (this.station != null && this.station != station) {
            throw new IllegalStateException("Docking point already belongs to a station");
        }

        this.station = station;
    }

    public void dockVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }

        if (!isFree()) {
            throw new IllegalStateException("Docking point is occupied");
        }

        if (vehicle.isDocked()) {
            throw new IllegalStateException("Vehicle is already docked");
        }

        if (vehicle.getStatus() != Vehicle.Status.IN_USE && vehicle.getStatus() != Vehicle.Status.AVAILABLE && vehicle.getStatus() != Vehicle.Status.OUT_OF_SERVICE) {
            throw new IllegalStateException("Vehicle cannot be docked in its current state");
        }

        this.vehicle = vehicle;
        vehicle.setDockingPoint(this);
        notifyStatusObservers();
    }

    public Vehicle releaseVehicle() {
        if (isFree()) {
            throw new IllegalStateException("Docking point is empty");
        }

        Vehicle released = vehicle;
        vehicle = null;
        released.setDockingPoint(null);
        notifyStatusObservers();

        return released;
    }
}
