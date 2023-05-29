package training.db;

public class SessionStat {
    private int partitionKey;
    private int dbId;
    private String time;
    private Long sid;
    private Long logonTime;
    private Long conId;
    private int serial;
    private String status;
    private Long taddr;
    private Long rowWaitFile;
    private Long rowWaitBlock;
    private Long rowWaitRow;
    private Long rowWaitObject;
    private String schemaName;
    private String module;
    private String action;
    private String clientInfo;
    private Long commandType;
    private String sqlAddr;
    private Long sqlHash;
    private String sqlId;
    private String prevSqlAddr;
    private Long prevSqlHash;
    private String prevSqlId;

    public SessionStat() {}

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

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getLogonTime() {
        return logonTime;
    }

    public void setLogonTime(Long logonTime) {
        this.logonTime = logonTime;
    }

    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTaddr() {
        return taddr;
    }

    public void setTaddr(Long taddr) {
        this.taddr = taddr;
    }

    public Long getRowWaitFile() {
        return rowWaitFile;
    }

    public void setRowWaitFile(Long rowWaitFile) {
        this.rowWaitFile = rowWaitFile;
    }

    public Long getRowWaitBlock() {
        return rowWaitBlock;
    }

    public void setRowWaitBlock(Long rowWaitBlock) {
        this.rowWaitBlock = rowWaitBlock;
    }

    public Long getRowWaitRow() {
        return rowWaitRow;
    }

    public void setRowWaitRow(Long rowWaitRow) {
        this.rowWaitRow = rowWaitRow;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
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

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Long getCommandType() {
        return commandType;
    }

    public void setCommandType(Long commandType) {
        this.commandType = commandType;
    }

    public Long getRowWaitObject() {
        return rowWaitObject;
    }

    public void setRowWaitObject(Long rowWaitObject) {
        this.rowWaitObject = rowWaitObject;
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

    public String getPrevSqlAddr() {
        return prevSqlAddr;
    }

    public void setPrevSqlAddr(String prevSqlAddr) {
        this.prevSqlAddr = prevSqlAddr;
    }

    public Long getPrevSqlHash() {
        return prevSqlHash;
    }

    public void setPrevSqlHash(Long prevSqlHash) {
        this.prevSqlHash = prevSqlHash;
    }

    public String getPrevSqlId() {
        return prevSqlId;
    }

    public void setPrevSqlId(String prevSqlId) {
        this.prevSqlId = prevSqlId;
    }

    public SessionStat(int partitionKey, int dbId, String time, Long sid, Long logonTime, Long conId, int serial, String status, Long taddr, Long rowWaitFile, Long rowWaitBlock, Long rowWaitRow, Long rowWaitObject, String schemaName, String module, String action, String clientInfo, Long commandType, String sqlAddr, Long sqlHash, String sqlId, String prevSqlAddr, Long prevSqlHash, String prevSqlId) {
        this.partitionKey = partitionKey;
        this.dbId = dbId;
        this.time = time;
        this.sid = sid;
        this.logonTime = logonTime;
        this.conId = conId;
        this.serial = serial;
        this.status = status;
        this.taddr = taddr;
        this.rowWaitFile = rowWaitFile;
        this.rowWaitBlock = rowWaitBlock;
        this.rowWaitRow = rowWaitRow;
        this.rowWaitObject = rowWaitObject;
        this.schemaName = schemaName;
        this.module = module;
        this.action = action;
        this.clientInfo = clientInfo;
        this.commandType = commandType;
        this.sqlAddr = sqlAddr;
        this.sqlHash = sqlHash;
        this.sqlId = sqlId;
        this.prevSqlAddr = prevSqlAddr;
        this.prevSqlHash = prevSqlHash;
        this.prevSqlId = prevSqlId;
    }
}
