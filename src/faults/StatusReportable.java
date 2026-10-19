package faults;

public interface StatusReportable {
    String getId();

    StatusReport reportStatus();

    void attachStatusObserver(StatusObserver observer);
    void removeStatusObserver(StatusObserver observer);

    void notifyStatusObservers();
}
