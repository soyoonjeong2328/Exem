package miniproject.server.insert;

import miniproject.db.Postgresql;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JmxThreadDataInsert {
    public static void insert(ByteBuffer bodyBuffer, byte clientId, long time, int count){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = Postgresql.getConnection();
            pstmt = conn.prepareStatement(Postgresql.INSERT_JMXTHREADDATA);

            for (int i=0; i< count; i++){
                pstmt.setInt(1, clientId);
                pstmt.setTimestamp(2, new Timestamp(time));
                pstmt.setLong(3, bodyBuffer.getLong()); // threadId
                pstmt.setLong(4, bodyBuffer.getLong()); // threadCpuTime
                pstmt.setLong(5, bodyBuffer.getLong()); // threadUserTime
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("=============== JmxThreadData insert 완료 ====================");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try{
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
