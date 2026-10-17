package faults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaintenanceCrew implements FaultObserver {

    private final List<String> serviceTickets = new ArrayList<>();

    @Override
    public void onFaultReported(FaultReport report) {
        String ticket = "Service needed for vehicle " + report.getVehicleId()
                + ": " + report.getDescription()
                + " (reported by " + report.getSource() + ")";
        serviceTickets.add(ticket);
        System.out.println("[Maintenance] " + ticket);
    }

    public List<String> getServiceTickets() {
        return Collections.unmodifiableList(serviceTickets);
    }
}