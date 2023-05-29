package training.server.insert;

import training.db.Postgresql;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.Arrays;


public class SessionStatInsert {
    public static void insert(ByteBuffer bodyBuffer, byte clientId, long time, int count) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Postgresql.getConnection();
            pstmt = conn.prepareStatement(Postgresql.INSERT_ORA_SESSION_STAT);
            /*
                받을때도 사이즈먼저 읽고
                해당 사이즈만큼 바이트 Array 만들어서 사이즈만큼 읽어서
                String 변환
             */
            for (int i = 0; i <= count; i++) {
                while (bodyBuffer.hasRemaining()) {
                    pstmt.setInt(1, 0); // partition_key
                    pstmt.setInt(2, clientId); // db_Id
                    pstmt.setTimestamp(3, new Timestamp(time)); // time
                    pstmt.setLong(4, bodyBuffer.getLong()); // sid
                    pstmt.setTimestamp(5, new Timestamp((int) bodyBuffer.getLong())); // LogonTime
                    pstmt.setLong(6, bodyBuffer.getLong()); // conId
                    pstmt.setInt(7, bodyBuffer.getInt()); // serial
                    pstmt.setString(8, bb_to_str(bodyBuffer)); // status
                    pstmt.setLong(9, bodyBuffer.getLong()); // taddr
                    pstmt.setLong(10, bodyBuffer.getLong()); // rowWaitFile
                    pstmt.setLong(11, bodyBuffer.getLong()); // rowWaitBlock
                    pstmt.setLong(12, bodyBuffer.getLong()); // rowWaitRow
                    pstmt.setLong(13, bodyBuffer.getLong()); // rowWaitObject
                    pstmt.setString(14, bb_to_str(bodyBuffer)); // SchemaName
                    pstmt.setString(15, bb_to_str(bodyBuffer)); // Module
                    pstmt.setString(16, bb_to_str(bodyBuffer)); // Action
                    pstmt.setString(17, bb_to_str(bodyBuffer)); // ClientInfo
                    pstmt.setLong(18, bodyBuffer.getLong()); // CommandType
                    pstmt.setString(19, bb_to_str(bodyBuffer)); // sqlAddr
                    pstmt.setLong(20, bodyBuffer.getLong());//sqlHash
                    pstmt.setString(21, bb_to_str(bodyBuffer)); // sqlId
                    pstmt.setString(22, bb_to_str(bodyBuffer)); // PrevSqlAddr
                    pstmt.setLong(23, bodyBuffer.getLong()); // PrevSqlHash
                    pstmt.setString(24, bb_to_str(bodyBuffer));// PrevSqlId
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
            System.out.println("============= Session Stat insert 성공 ===============================");
        } catch (Exception e) {
            System.out.println("Exception : " + e.toString());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("SQLException : " + e.toString());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ee) {
                    System.out.println("Exception : " + ee.toString());
                }
            }
        }
    }

    public static String bb_to_str(ByteBuffer byteBuffer) throws UnsupportedEncodingException {
        int str_length = byteBuffer.getInt();
        byte[] StrName = new byte[str_length];
        Arrays.fill(StrName, (byte) 0);
         /*
            bodyBuffer.get(bytes)로 해도 되나, 받는게 없어서 실패쓰!!!
            bodyBuffer.get(bytes, 시작위치, 끝위치)해서 성공쓰
         */
        byteBuffer.get(StrName, 0, str_length);
        String Name = new String(StrName, "UTF-8").trim();
        return Name;
    }
}
