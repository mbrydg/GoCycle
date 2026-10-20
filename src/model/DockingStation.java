package model;

import faults.StatusObserver;
import faults.StatusReport;
import faults.StatusReportable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DockingStation implements StatusReportable {

    private final String id;
    private final String name;
    private final List<DockingPoint> dockingPoints;

    private final List<StatusObserver> observers = new ArrayList<>();

    public DockingStation(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Station ID is required");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Station name is required");
        }

        this.id = id;
        this.name = name;
        this.dockingPoints = new ArrayList<>();
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
                        .append(getName())
                        .append(" | ")
                        .append("Docking Points: ")
                        .append(getDockingPoints().size())
                        .toString());
        for (StatusObserver observer : new ArrayList<>(observers)) {
            observer.onStatusReported(status);
        }
    }

    public String getName() {
        return name;
    }

    public List<DockingPoint> getDockingPoints() {
        return Collections.unmodifiableList(dockingPoints);
    }

    public void addDockingPoint(DockingPoint point) {
        if (point == null) {
            throw new IllegalArgumentException("Docking point is required");
        }

        for (DockingPoint existing : dockingPoints) {
            if (existing.getId().equals(point.getId())) {
                throw new IllegalArgumentException("Duplicate docking point ID");
            }
        }

        point.setStation(this);
        dockingPoints.add(point);
    }

    public int getTotalCapacity() {
        return dockingPoints.size();
    }

    public int getAvailableCapacity() {
        int count = 0;

        for (DockingPoint point : dockingPoints) {
            if (point.isFree()) {
                count++;
            }
        }

        return count;
    }

    public boolean hasAvailablePoint() {
        return getAvailableCapacity() > 0;
    }

    public DockingPoint findFreePoint() {
        for (DockingPoint point : dockingPoints) {
            if (point.isFree()) {
                return point;
            }
        }

        return null;
    }
}
