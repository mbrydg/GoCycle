package faults;

public interface FaultTarget {
    String getId();
    void markOutOfService();
}