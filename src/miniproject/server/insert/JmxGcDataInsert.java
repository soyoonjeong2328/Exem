package miniproject.server.insert;

import miniproject.db.Postgresql;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class JmxGcDataInsert {
    public static void insert(ByteBuffer bodyBuffer, byte clientId, long time, int count){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = Postgresql.getConnection();
            pstmt = conn.prepareStatement(Postgresql.INSERT_JMXGCDATA);

            for(int i=0; i< count; i++){
                pstmt.setInt(1, clientId); // serverId
                pstmt.setTimestamp(2, new Timestamp(time)); // time
                pstmt.setLong(3, bodyBuffer.getLong()); // gc_time
                pstmt.setLong(4, bodyBuffer.getLong()); // gc_count
                pstmt.setString(5, Change(bodyBuffer)); // gcName
            }
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
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        }
    }

    public static String Change(ByteBuffer byteBuffer) throws UnsupportedEncodingException {
        int str_length = byteBuffer.getInt();
        byte[] StrName = new byte[str_length];
        Arrays.fill(StrName, (byte) 0);
        byteBuffer.get(StrName, 0, str_length);
        String Name = new String(StrName, "UTF-8").trim();
        return Name;
    }
}
