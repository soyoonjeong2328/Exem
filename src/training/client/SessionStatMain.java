package training.client;


import training.db.Oracle;
import training.db.SessionStat;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;


public class SessionStatMain {
    private byte CLIENT_ID; // Client 고유 id
    final short type = 3;
    public static int size;

    public SessionStatMain(byte client_id) {
        this.CLIENT_ID = type;
    }

    public ByteBuffer getSessionStat() {
        ArrayList<SessionStat> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long time = System.currentTimeMillis();

        int tempSize = 0;
        Timestamp timestamp = null;

        try {
            conn = Oracle.getConnection();
            StringBuffer sql = new StringBuffer();
            sql.append(Oracle.SessionStatSql);
            pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SessionStat sessionStat = new SessionStat();
                sessionStat.setSid(rs.getLong("sid"));
                timestamp = rs.getTimestamp("logon_time");
                sessionStat.setLogonTime(timestamp.getTime());
                sessionStat.setConId(rs.getLong("con_id"));
                sessionStat.setSerial(rs.getInt("serial#"));
                sessionStat.setStatus(NullStr(rs.getString("status")));
                sessionStat.setTaddr(rs.getLong("taddr"));
                sessionStat.setRowWaitFile(rs.getLong("row_wait_file#"));
                sessionStat.setRowWaitBlock(rs.getLong("row_wait_block#"));
                sessionStat.setRowWaitRow(rs.getLong("row_wait_row#"));
                sessionStat.setRowWaitObject(rs.getLong("row_wait_obj#"));
                sessionStat.setSchemaName(NullStr(rs.getString("schemaName")));
                sessionStat.setModule(NullStr(rs.getString("module")));
                sessionStat.setAction(NullStr(rs.getString("action")));
                sessionStat.setClientInfo(NullStr(rs.getString("client_info")));
                sessionStat.setCommandType(rs.getLong("command"));
                sessionStat.setSqlAddr(NullStr(rs.getString("sql_address")));
                sessionStat.setSqlHash(rs.getLong("sql_hash_value"));
                sessionStat.setSqlId(NullStr(rs.getString("sql_id")));
                sessionStat.setPrevSqlAddr(NullStr(rs.getString("prev_sql_addr")));
                sessionStat.setPrevSqlHash(rs.getLong("prev_hash_value"));
                sessionStat.setPrevSqlId(NullStr(rs.getString("prev_sql_id")));

                // int size가 4Byte라 4라고 지정.
                int strByteArr = 4;
                tempSize = (Long.BYTES * 11) + (strByteArr * 9) + Integer.BYTES
                        + sessionStat.getStatus().getBytes().length
                        + sessionStat.getModule().getBytes().length
                        + sessionStat.getSchemaName().getBytes().length
                        + sessionStat.getAction().getBytes().length
                        + sessionStat.getClientInfo().getBytes().length
                        + sessionStat.getSqlAddr().getBytes().length
                        + sessionStat.getSqlId().getBytes().length
                        + sessionStat.getPrevSqlAddr().getBytes().length
                        + sessionStat.getPrevSqlId().getBytes().length;
                size += tempSize;

                list.add(sessionStat);
            }
            int result = (Short.BYTES + Byte.BYTES + Long.BYTES + Long.BYTES + Integer.BYTES);

            /*
                String 가변적이여서 size함께 넘겨주기
                헤더 : string 개수 만큼 size 추가
                바디 : put할때 사이즈랑 byte[] put
             */

            ByteBuffer buf = ByteBuffer.allocate(result+size); // header + body


            // 헤더
            buf.putShort((short) type); // type
            buf.put((byte) CLIENT_ID); // clientId
            buf.putLong((long) size); // size
            buf.putLong((long) time); // time
            buf.putInt((int) list.size());  // count
            System.out.println("header type : " + type + " clientId : " + CLIENT_ID + " size : " + size + "  time : " + time +" count : " + list.size());

            //바디
            for (SessionStat stat : list) {
                /*
                    String 사이즈랑 String 같이 put해서 넘기기
                 */
                buf.putLong(stat.getSid()); // sid
                buf.putLong(stat.getLogonTime()); // Logon_Time
                buf.putLong(stat.getConId()); // conId
                buf.putInt((int)stat.getSerial()); // serial
                buf.putInt((int)stat.getStatus().getBytes().length); // status 길이
                buf.put(stat.getStatus().getBytes()); // status
                buf.putLong(stat.getTaddr());  // taddr
                buf.putLong(stat.getRowWaitFile()); // rowwaitfile
                buf.putLong(stat.getRowWaitBlock()); // rowwaitblock
                buf.putLong(stat.getRowWaitRow()); // rowwaitrow
                buf.putLong(stat.getRowWaitObject()); // rowwaitObject
                buf.putInt((int)stat.getSchemaName().getBytes().length); // 스키마 길이
                buf.put(stat.getSchemaName().getBytes()); // schemaname
                buf.putInt((int)stat.getModule().getBytes().length);
                buf.put(stat.getModule().getBytes());// module
                buf.putInt((int)stat.getAction().getBytes().length);
                buf.put(stat.getAction().getBytes()); // action
                buf.putInt((int)stat.getClientInfo().getBytes().length);
                buf.put(stat.getClientInfo().getBytes()); // clientInfo
                buf.putLong(stat.getCommandType()); // commandType
                buf.putInt((int)stat.getSqlAddr().getBytes().length);
                buf.put(stat.getSqlAddr().getBytes()); // sqladdr
                buf.putLong(stat.getSqlHash()); // sqlhash
                buf.putInt((int)stat.getSqlId().getBytes().length);
                buf.put(stat.getSqlId().getBytes()); // sqlId
                buf.putInt((int)stat.getPrevSqlAddr().getBytes().length);
                buf.put(stat.getPrevSqlAddr().getBytes()); // prevsqlAddr
                buf.putLong(stat.getPrevSqlHash()); // prevsqlhash
                buf.putInt((int)stat.getPrevSqlId().getBytes().length);
                buf.put(stat.getPrevSqlId().getBytes()); // prevsqlId

            }
            return buf;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
        }
        return null;
    }

    public static String NullStr(String str) {
        return (str == null) ? "" : str;
    }

    public static void main(String[] args) {
        SessionStatMain sessionStatMain = new SessionStatMain((byte) 10);
        sessionStatMain.getSessionStat();
    }
}
