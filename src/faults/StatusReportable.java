package faults;

public interface StatusReportable {

    String getId();

    void attachStatusObserver(StatusObserver observer);
    void removeStatusObserver(StatusObserver observer);

    void notifyStatusObservers();
}
