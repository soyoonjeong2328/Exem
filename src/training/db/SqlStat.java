package training.db;

public class SqlStat {
    private int partitionKey;
    private int dbId;
    private String time;
    private String sqlAddr;
    private Long sqlHash;
    private String sqlId;
    private Long sqlPlanHash;
    private String userName;
    private String program;
    private String module;
    private String action;
    private String machine;
    private String osUser;
    private Long elapsedTime;
    private Long cpuTime;
    private Long waitTime;
    private Long logicalReads;
    private Long physicalReads;
    private Long redoSize;
    private Long executionCount;
    private Long sortDisk;
    private Long sortRows;
    private Long tableFetchByRowId;
    private Long tableFetchContinuedByRowId;
    private Long tableScanBlocksGotten;
    private Long tableScanRowsGotten;

    public SqlStat() {
    }

    public int getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(int partitionKey) {
        this.partitionKey = partitionKey;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSqlAddr() {
        return sqlAddr;
    }

    public void setSqlAddr(String sqlAddr) {
        this.sqlAddr = sqlAddr;
    }

    public Long getSqlHash() {
        return sqlHash;
    }

    public void setSqlHash(Long sqlHash) {
        this.sqlHash = sqlHash;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public Long getSqlPlanHash() {
        return sqlPlanHash;
    }

    public void setSqlPlanHash(Long sqlPlanHash) {
        this.sqlPlanHash = sqlPlanHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getOsUser() {
        return osUser;
    }

    public void setOsUser(String osUser) {
        this.osUser = osUser;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Long getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(Long cpuTime) {
        this.cpuTime = cpuTime;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    public Long getLogicalReads() {
        return logicalReads;
    }

    public void setLogicalReads(Long logicalReads) {
        this.logicalReads = logicalReads;
    }

    public Long getPhysicalReads() {
        return physicalReads;
    }

    public void setPhysicalReads(Long physicalReads) {
        this.physicalReads = physicalReads;
    }

    public Long getRedoSize() {
        return redoSize;
    }

    public void setRedoSize(Long redoSize) {
        this.redoSize = redoSize;
    }

    public void setExecutionCount(Long executionCount) {
        this.executionCount = executionCount;
    }

    public Long getSortDisk() {
        return sortDisk;
    }

    public void setSortDisk(Long sortDisk) {
        this.sortDisk = sortDisk;
    }

    public Long getSortRows() {
        return sortRows;
    }

    public void setSortRows(Long sortRows) {
        this.sortRows = sortRows;
    }

    public Long getTableFetchByRowId() {
        return tableFetchByRowId;
    }

    public void setTableFetchByRowId(Long tableFetchByRowid) {
        this.tableFetchByRowId = tableFetchByRowid;
    }

    public Long getExecutionCount() {
        return executionCount;
    }

    public Long getTableFetchContinuedByRowId() {
        return tableFetchContinuedByRowId;
    }

    public void setTableFetchContinuedByRowId(Long tableFetchContinuedByRowid) {
        this.tableFetchContinuedByRowId = tableFetchContinuedByRowid;
    }

    public Long getTableScanBlocksGotten() {
        return tableScanBlocksGotten;
    }

    public void setTableScanBlocksGotten(Long tableScanBlocksGotten) {
        this.tableScanBlocksGotten = tableScanBlocksGotten;
    }

    public Long getTableScanRowsGotten() {
        return tableScanRowsGotten;
    }

    public void setTableScanRowsGotten(Long tableScanRowsGotten) {
        this.tableScanRowsGotten = tableScanRowsGotten;
    }
}
