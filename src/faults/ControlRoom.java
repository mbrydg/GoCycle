package faults;

import model.DockingPoint;
import model.DockingStation;
import model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlRoom implements FaultObserver, StatusObserver {

    private final List<String> alerts = new ArrayList<>();
    private final List<StatusReport> vehiclesList = new ArrayList<>();
    private final List<StatusReport> dockingPointsList = new ArrayList<>();

    private final List<StatusReport> dockingStationsList = new ArrayList<>();

    @Override
    public void onFaultReported(FaultReport report) {
        String alert = "ALERT: vehicle " + report.getVehicleId()
                + " has a fault - " + report.getDescription();
        alerts.add(alert);
        System.out.println("[Control Room] " + alert);
    }

    public List<String> getAlerts() {
        return Collections.unmodifiableList(alerts);
    }

    public List<StatusReport> getVehiclesList() {return Collections.unmodifiableList(vehiclesList); }

    public List<StatusReport> getDockingPointsList() {return Collections.unmodifiableList(dockingPointsList);}

    public List<StatusReport> getDockingStationsList() {return Collections.unmodifiableList(dockingStationsList);}

    @Override
    public void onStatusReported(StatusReport report) {
        switch (report.getSource()) {
            case VEHICLE:
            case E_VEHICLE:
                if (!vehiclesList.contains(report)) {
                    vehiclesList.add(report);
                } else {
                    for (int i = 0; i < vehiclesList.size(); i++) {
                        if (vehiclesList.get(i).equals(report)) {
                            vehiclesList.set(i, report);
                        }
                    }
                }
                break;
            case DOCKINGPOINT:
                if (!dockingPointsList.contains(report)) {
                    dockingPointsList.add(report);
                } else {
                    for (int i = 0; i < dockingPointsList.size(); i++) {
                        if (dockingPointsList.get(i).equals(report)) {
                            dockingPointsList.set(i, report);
                        }
                    }
                }
                break;
            case DOCKINGSTATION:
                if (!dockingStationsList.contains(report)) {
                    dockingStationsList.add(report);
                } else {
                    for (int i = 0; i < dockingStationsList.size(); i++) {
                        if (dockingStationsList.get(i).equals(report)) {
                            dockingStationsList.set(i, report);
                        }
                    }
                }
                break;
        }
    }
}