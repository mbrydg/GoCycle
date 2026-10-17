package gocycle.fault;

public interface FaultObserver {
    void onFaultReported(FaultReport report);
}
