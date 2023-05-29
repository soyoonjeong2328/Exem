package miniproject.db;

public class JmxHeapDataStat {
    private int memoryNameLength;
    private String memoryName;
    private Long init;
    private Long max;
    private Long used;
    private Long committed;

    public int getMemoryNameLength() {
        return memoryNameLength;
    }

    public void setMemoryNameLength(int memoryNameLength) {
        this.memoryNameLength = memoryNameLength;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public void setMemoryName(String memoryName) {
        this.memoryName = memoryName;
    }

    public Long getInit() {
        return init;
    }

    public void setInit(Long init) {
        this.init = init;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public Long getCommitted() {
        return committed;
    }

    public void setCommitted(Long committed) {
        this.committed = committed;
    }
}
