package training.db;

public class DBStat {
    private int paritionKey;
    private int dbId;
    private Long time;
    private Short statId;
    private Long value;

    public int getParitionKey() {
        return paritionKey;
    }

    public void setParitionKey(int paritionKey) {
        this.paritionKey = paritionKey;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Short getStatId() {
        return statId;
    }

    public void setStatId(Short statId) {
        this.statId = statId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    // 생성자 오버로딩
    public DBStat() {
    }
}
