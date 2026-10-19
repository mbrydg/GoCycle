package faults;

import java.util.ArrayList;
import java.util.List;

public class StatusNotifier {

    private final List<StatusObserver> observers = new ArrayList<>();

    public void attach (StatusObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(StatusObserver observer) {
        observers.remove(observer);
    }

    public void reportStatus(StatusReport report) {
        for (StatusObserver observer : new ArrayList<>(observers)) {
            observer.onStatusReported(report);
        }
    }
}
