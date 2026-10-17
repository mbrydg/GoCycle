package faults;

import java.util.ArrayList;
import java.util.List;

public class FaultNotifier {

    private final List<FaultObserver> observers = new ArrayList<>();

    public void attach(FaultObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(FaultObserver observer) {
        observers.remove(observer);
    }

    public void reportFault(FaultReport report) {
        for (FaultObserver observer : new ArrayList<>(observers)) {
            observer.onFaultReported(report);
        }
    }
}