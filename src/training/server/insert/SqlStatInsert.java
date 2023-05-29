package training.server.insert;

import training.db.Postgresql;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class SqlStatInsert {
    public static void insert(ByteBuffer bodyBuffer, byte ClientId, long time, int count) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Postgresql.getConnection();
            pstmt = conn.prepareStatement(Postgresql.INSERT_ORA_SQL_STAT);

            for (int i = 0; i <= count; i++) {
                while (bodyBuffer.hasRemaining()){
                pstmt.setInt(1, 0); // partition_key
                pstmt.setInt(2, ClientId); // db_id
                pstmt.setTimestamp(3, new Timestamp(time)); // time

                int SqlAddr_length = bodyBuffer.getInt();
                byte[] SQLADDR = new byte[SqlAddr_length];
                Arrays.fill(SQLADDR, (byte) 0);
                bodyBuffer.get(SQLADDR, 0, SqlAddr_length);
                String sqladdr = new String(SQLADDR, "UTF-8").trim();
                pstmt.setString(4, sqladdr); // SQL_ADDR
                pstmt.setLong(5, bodyBuffer.getLong()); // SQL_Hash_Value

                int SqlId_length = bodyBuffer.getInt();
                byte[] SQLID = new byte[SqlId_length];
                Arrays.fill(SQLID, (byte) 0);
                bodyBuffer.get(SQLID, 0, SqlId_length);
                String sqlid = new String(SQLID, "UTF-8").trim();
                pstmt.setString(6, sqlid); // sqlId
                pstmt.setLong(7, bodyBuffer.getLong()); // plan_hash_value

                int UserName_length = bodyBuffer.getInt();
                byte[] UserName = new byte[UserName_length];
                Arrays.fill(UserName, (byte) 0);
                bodyBuffer.get(UserName, 0, UserName_length);
                String username = new String(UserName, "UTF-8").trim();

                pstmt.setString(8, username); // userName

                int Program_length = bodyBuffer.getInt();
                byte[] PROGRAM = new byte[Program_length];
                Arrays.fill(PROGRAM, (byte) 0);
                bodyBuffer.get(PROGRAM, 0, Program_length);
                String program = new String(PROGRAM, "UTF-8").trim();
                pstmt.setString(9, program); // Program

                int Module_length = bodyBuffer.getInt();
                byte[] MODULE = new byte[Module_length];
                Arrays.fill(MODULE, (byte) 0);
                bodyBuffer.get(MODULE, 0, Module_length);
                String module = new String(MODULE, "UTF-8").trim();
                pstmt.setString(10, module); // Module

                int Action_length = bodyBuffer.getInt();
                byte[] ACTION = new byte[Action_length];
                Arrays.fill(ACTION, (byte) 0);
                bodyBuffer.get(ACTION, 0, Action_length);
                String action = new String(ACTION, "UTF-8").trim();
                pstmt.setString(11, action); // Action

                int Machine_length = bodyBuffer.getInt();
                byte[] MACHINE = new byte[Machine_length];
                Arrays.fill(MACHINE, (byte) 0);
                bodyBuffer.get(MACHINE, 0, Machine_length);
                String machine = new String(MACHINE, "UTF-8").trim();
                pstmt.setString(12, machine); // Machine

                int Osuser_length = bodyBuffer.getInt();
                byte[] OSUSER = new byte[Osuser_length];
                Arrays.fill(OSUSER, (byte) 0);
                bodyBuffer.get(OSUSER, 0, Osuser_length);
                String osuser = new String(OSUSER, "UTF-8").trim();
                pstmt.setString(13, osuser); // OsUser
                pstmt.setLong(14, bodyBuffer.getLong()); // Elapsed_Time
                pstmt.setLong(15, bodyBuffer.getLong()); // cpuTime
                pstmt.setLong(16, bodyBuffer.getLong()); // waitTime
                pstmt.setLong(17, bodyBuffer.getLong()); // LogicalRead
                pstmt.setLong(18, bodyBuffer.getLong()); // PhysicalReads
                pstmt.setLong(19, bodyBuffer.getLong()); // RedoSize
                pstmt.setLong(20, bodyBuffer.getLong()); // Executions
                pstmt.setLong(21, bodyBuffer.getLong()); // sort_disk
                pstmt.setLong(22, bodyBuffer.getLong()); // sort_rows
                pstmt.setLong(23, bodyBuffer.getLong()); // Table_Fetch_By_Rowid
                pstmt.setLong(24, bodyBuffer.getLong()); // Table_Fetch_Continued_By_Rowid
                pstmt.setLong(25, bodyBuffer.getLong()); // Table_Scan_Blocks_Gotten
                pstmt.setLong(26, bodyBuffer.getLong()); // Table_Scan_Rows_Gotten

                pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
            System.out.println("====================== Sqlstat insert 성공 ==============================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }
}
