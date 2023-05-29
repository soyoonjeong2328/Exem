package miniproject.server.insert;

import miniproject.db.Postgresql;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JmxClassDataInsert {
    public static void insert(ByteBuffer bodyBuffer, byte clientId, long time, int count){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = Postgresql.getConnection();
            pstmt = conn.prepareStatement(Postgresql.INSERT_JMXCLASSDATA);

            for(int i=0; i< count ; i++){
                pstmt.setInt(1, clientId);
                pstmt.setTimestamp(2, new Timestamp(time));
                pstmt.setInt(3, bodyBuffer.getInt()); // totalClassCount
                pstmt.setInt(4, bodyBuffer.getInt()); // loadClassCount
                pstmt.setLong(5, bodyBuffer.getLong()); // unLoadClassCount
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("======== JmxClassData insert 완료 =======================");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch (SQLException ee){
                    System.out.println("SQLException = " + ee.toString());
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
