package faults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlRoom implements FaultObserver {

    private final List<String> alerts = new ArrayList<>();

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
}