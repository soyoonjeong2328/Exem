package training.server.insert;

import training.db.Postgresql;
import java.nio.ByteBuffer;
import java.sql.*;

public class DbStatInsert {
    public static void insert(ByteBuffer byteBuffer , byte clientId, long time, int count){
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = Postgresql.getConnection(); // DB 연결
            pstmt = con.prepareStatement(Postgresql.INSERT_DB_STAT);
            for (int i=0; i < count; i++){
                short statId = byteBuffer.getShort();
                long value = byteBuffer.getLong();

                pstmt.setInt(1, 0); // partion_key
                pstmt.setInt(2, clientId); // db_id
                pstmt.setTimestamp(3, new Timestamp(time)); // time
                pstmt.setShort(4, statId); // statId
                pstmt.setLong(5, value); // value
                pstmt.addBatch();
            }
            pstmt.executeBatch(); // 전부 다 담은 후 Batch 실행
            System.out.println("===== PostgreSQL DB insert 성공 =======");
        }catch (Exception e){
            System.out.println("Exception : " + e.toString());
        }finally {
            // 열었던 순서대로 닫아줘야 함
            if(pstmt != null){
                try {
                    pstmt.close();
                }catch (SQLException e){
                    System.out.println("SQLException : " + e.toString());
                }
            }
            if(con != null){
                try{
                    con.close();
                }catch (Exception ee){
                    System.out.println("Exception : " + ee.toString());
                }
            }
        }
    }
}
