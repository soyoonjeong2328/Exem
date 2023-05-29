package miniproject.server.insert;

import miniproject.db.Postgresql;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Arrays;

public class ClassStatDataInsert {
    public static void insert(ByteBuffer bodyBuffer, byte clientId, long time, int count){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = Postgresql.getConnection();
            pstmt = conn.prepareStatement(Postgresql.INSERT_CLASSSTAT);

            for(int i=0; i< count; i++){
                pstmt.setInt(1, clientId); // serverId
                pstmt.setTimestamp(2, new Timestamp(time)); // time
                pstmt.setString(3, Change(bodyBuffer)); // className
                pstmt.setString(4, Change(bodyBuffer)); // methodName
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("=========== ClassStatData insert 성공 ========================");
        }catch (Exception e){
            e.printStackTrace();
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
