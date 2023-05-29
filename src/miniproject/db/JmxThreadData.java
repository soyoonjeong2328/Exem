package miniproject.db;

public class JmxThreadData {
    private Long threadId;
    private Long threadCpuTime;
    private Long threadUserTime;

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public Long getThreadCpuTime() {
        return threadCpuTime;
    }

    public void setThreadCpuTime(Long threadCpuTime) {
        this.threadCpuTime = threadCpuTime;
    }

    public Long getThreadUserTime() {
        return threadUserTime;
    }

    public void setThreadUserTime(Long threadUserTime) {
        this.threadUserTime = threadUserTime;
    }
}
