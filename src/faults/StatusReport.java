package faults;

import java.util.Objects;

public class StatusReport {

    public enum Source {
        VEHICLE,
        E_VEHICLE,
        DOCKINGSTATION,
        DOCKINGPOINT
    }

    private final Source source;

    private final String sourceId;

    private final String statusString;

    public StatusReport (Source source, String sourceId, String statusString) {
        this.source = source;
        this.sourceId = sourceId;
        this.statusString = statusString;
    }

    public Source getSource() {
        return source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getStatusString() {
        return statusString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        StatusReport other = (StatusReport) obj;
        return Objects.equals(this.getSourceId(), other.getSourceId());
    }
}
