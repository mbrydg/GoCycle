package faults;

public class StatusReport {

    public enum Source {
        VEHICLE,
        E_VEHICLE,
        DOCKINGSTATION,
        DOCKINGPOINT
    }

    private final Source source;

    private final String statusString;

    public StatusReport (Source source, String statusString) {
        this.source = source;
        this.statusString = statusString;
    }

    public Source getSource() {
        return source;
    }

    public String getStatusString() {
        return statusString;
    }
}
