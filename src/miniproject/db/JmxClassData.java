package miniproject.db;

public class JmxClassData {
    private int totalClassCount;
    private int loadClassCount;
    private Long unLoadClassCount;

    public int getTotalClassCount() {
        return totalClassCount;
    }

    public void setTotalClassCount(int totalClassCount) {
        this.totalClassCount = totalClassCount;
    }

    public int getLoadClassCount() {
        return loadClassCount;
    }

    public void setLoadClassCount(int loadClassCount) {
        this.loadClassCount = loadClassCount;
    }

    public Long getUnLoadClassCount() {
        return unLoadClassCount;
    }

    public void setUnLoadClassCount(Long unLoadClassCount) {
        this.unLoadClassCount = unLoadClassCount;
    }
}
