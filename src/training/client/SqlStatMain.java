package training.client;

import training.db.Oracle;
import training.db.Postgresql;
import training.db.SqlStat;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SqlStatMain {
    private byte CLIENT_ID; // Client 고유 id
    final short type = 4;
    public static int size;

    public SqlStatMain(byte clientId) {
        this.CLIENT_ID = type;
    }

    public ByteBuffer getSqlStat(){
        ArrayList<SqlStat> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long time = System.currentTimeMillis(); // 수집시간

        int tempSize = 0;

        try{
            conn = Oracle.getConnection();
            StringBuffer sql = new StringBuffer();
            sql.append(Oracle.ORA_SQL_STAT);
            pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                SqlStat sqlStat = new SqlStat();
                sqlStat.setSqlAddr(NullStr(rs.getString(1)));
                sqlStat.setSqlHash(rs.getLong(2));
                sqlStat.setSqlId(NullStr(rs.getString(3)));
                sqlStat.setSqlPlanHash(rs.getLong(4));
                sqlStat.setUserName(NullStr(rs.getString(5)));
                sqlStat.setProgram(NullStr(rs.getString(6)));
                sqlStat.setModule(NullStr(rs.getString(7)));
                sqlStat.setAction(NullStr(rs.getString(8)));
                sqlStat.setMachine(NullStr(rs.getString(9)));
                sqlStat.setOsUser(NullStr(rs.getString(10)));
                sqlStat.setElapsedTime(rs.getLong(11));
                sqlStat.setCpuTime(rs.getLong(12));
                sqlStat.setWaitTime(rs.getLong(13));
                sqlStat.setLogicalReads(rs.getLong(14));
                sqlStat.setPhysicalReads(rs.getLong(15));
                sqlStat.setRedoSize(rs.getLong(16));
                sqlStat.setExecutionCount(rs.getLong(17));
                sqlStat.setSortDisk(rs.getLong(18));
                sqlStat.setSortRows(rs.getLong(19));
                sqlStat.setTableFetchByRowId(rs.getLong(20));
                sqlStat.setTableFetchContinuedByRowId(rs.getLong(21));
                sqlStat.setTableScanBlocksGotten(rs.getLong(22));
                sqlStat.setTableScanRowsGotten(rs.getLong(23));

                int strLen = 4;
                tempSize = (Long.BYTES * 15) + (strLen * 8)
                        + sqlStat.getSqlAddr().getBytes().length
                        + sqlStat.getSqlId().getBytes().length
                        + sqlStat.getUserName().getBytes().length
                        + sqlStat.getProgram().getBytes().length
                        + sqlStat.getModule().getBytes().length
                        + sqlStat.getAction().getBytes().length
                        + sqlStat.getMachine().getBytes().length
                        + sqlStat.getOsUser().getBytes().length;
                size += tempSize;
                list.add(sqlStat);
            }
            int result = (Short.BYTES + Byte.BYTES + Long.BYTES + Long.BYTES + Integer.BYTES);
            ByteBuffer buf = ByteBuffer.allocate(result  + size);

            // 헤더
            buf.putShort((short) type); // type
            buf.put((byte) CLIENT_ID); // clientId
            buf.putLong((long) size); // size
            buf.putLong((long) time); // time
            buf.putInt((int) list.size());  // count
            System.out.println("header type : " + type + " clientId : " + CLIENT_ID + " size : " + size + "  time : " + time +" count : " + list.size());

            // 바디
            for(SqlStat stat : list){
                buf.putInt(stat.getSqlAddr().getBytes().length);
                buf.put(stat.getSqlAddr().getBytes()); // SQL_ADDRESS
                buf.putLong(stat.getSqlHash()); // SQL_HASH_VALUE
                buf.putInt(stat.getSqlId().getBytes().length);
                buf.put(stat.getSqlId().getBytes()); // SQL_ID
                buf.putLong(stat.getSqlPlanHash()); // Plan_Hash_value
                buf.putInt(stat.getUserName().getBytes().length);
                buf.put(stat.getUserName().getBytes()); // Username
                buf.putInt(stat.getProgram().getBytes().length);
                buf.put(stat.getProgram().getBytes()); // Program
                buf.putInt(stat.getModule().getBytes().length);
                buf.put(stat.getModule().getBytes()); // Module
                buf.putInt(stat.getAction().getBytes().length);
                buf.put(stat.getAction().getBytes()); // Action
                buf.putInt(stat.getMachine().getBytes().length);
                buf.put(stat.getMachine().getBytes()); // Machine
                buf.putInt(stat.getOsUser().getBytes().length);
                buf.put(stat.getOsUser().getBytes()); // OsUser
                buf.putLong(stat.getElapsedTime()); // Elapsed_Time
                buf.putLong(stat.getCpuTime()); // cpuTime
                buf.putLong(stat.getWaitTime()); // waitTime
                buf.putLong(stat.getLogicalReads()); // LogicalRead
                buf.putLong(stat.getPhysicalReads()); // PhysicalReads
                buf.putLong(stat.getRedoSize()); // RedoSize
                buf.putLong(stat.getExecutionCount()); // Executions
                buf.putLong(stat.getSortDisk()); // sort_disk
                buf.putLong(stat.getSortRows()); // sort_rows
                buf.putLong(stat.getTableFetchByRowId()); // Table_Fetch_By_Rowid
                buf.putLong(stat.getTableFetchContinuedByRowId()); // Table_Fetch_Continued_By_Rowid
                buf.putLong(stat.getTableScanBlocksGotten()); // Table_Scan_Blocks_Gotten
                buf.putLong(stat.getTableScanRowsGotten()); // Table_Scan_Rows_Gotten
            }
            return buf;

        }catch (Exception e){
            System.out.println("error :  " + e.toString());
        }finally {
            if(rs != null)
                try {
                    rs.close();
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            if(pstmt != null)
                try{
                    pstmt.close();
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            if(conn != null)
                try {
                    conn.close();
                }catch (Exception ee){
                    ee.printStackTrace();
                }
        }
        return null;
    }

    public static String NullStr(String str) {
        return (str == null) ? "" : str;
    }

    public static void main(String[] args) {
        SqlStatMain sqlStatMain = new SqlStatMain((byte) 10);
        sqlStatMain.getSqlStat();
    }
}
