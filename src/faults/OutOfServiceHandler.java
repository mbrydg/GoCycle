package faults;

import java.util.HashMap;
import java.util.Map;

public class OutOfServiceHandler implements FaultObserver {

    private final Map<String, FaultTarget> vehicles = new HashMap<>();

    public void register(FaultTarget vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
    }

    @Override
    public void onFaultReported(FaultReport report) {
        FaultTarget vehicle = vehicles.get(report.getVehicleId());
        if (vehicle != null) {
            vehicle.markOutOfService();
        }
    }
}
