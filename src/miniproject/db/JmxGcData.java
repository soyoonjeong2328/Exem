package miniproject.db;

public class JmxGcData {
    private Long gcTime;
    private Long gcCount;
    private int GcNameLength;
    private String gcName;

    public Long getGcTime() {
        return gcTime;
    }

    public void setGcTime(Long gcTime) {
        this.gcTime = gcTime;
    }

    public Long getGcCount() {
        return gcCount;
    }

    public void setGcCount(Long gcCount) {
        this.gcCount = gcCount;
    }

    public int getGcNameLength() {
        return GcNameLength;
    }

    public void setGcNameLength(int gcNameLength) {
        GcNameLength = gcNameLength;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }
}
