package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DockingStation {

    private final String id;
    private final String name;
    private final List<DockingPoint> dockingPoints;

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
