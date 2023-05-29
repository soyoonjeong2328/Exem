package miniproject.server.insert;

import miniproject.db.Postgresql;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class JmxHeapDataStatInsert {
    public static void insert(ByteBuffer bodyBuffer, byte clientId, long time, int count){
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = Postgresql.getConnection();
            pstmt = con.prepareStatement(Postgresql.INSERT_JMXHEAPDATA_STAT);

            // body 갯수만큼 insert하기
            for(int i =0 ; i < count; i++){
                pstmt.setInt(1, clientId); // serverId
                pstmt.setTimestamp(2, new Timestamp(time)); // time
                pstmt.setString(3, Change(bodyBuffer)); // memory_Name
                pstmt.setLong(4, bodyBuffer.getLong()); // memory_init
                pstmt.setLong(5, bodyBuffer.getLong()); // memory_max
                pstmt.setLong(6, bodyBuffer.getLong()); // memory_used
                pstmt.setLong(7, bodyBuffer.getLong()); // memory_committed
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("======= JmxHeapDataStat insert 완료 ==============");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch (SQLException e){
                    System.out.println("SQLException = " + e.toString());
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch (Exception ee){
                    System.out.println("Exception = " + ee.toString());
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
