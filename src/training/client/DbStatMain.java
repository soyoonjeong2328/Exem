package training.client;

import training.db.DBStat;
import training.db.Oracle;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;

public class DbStatMain {
    private byte CLIENT_ID; // Cient 고유 id
    final short type = 2;
    public static int size;

    public DbStatMain(byte clientId) {
        this.CLIENT_ID = type;
    }

    public ByteBuffer getDBSTAT() {
        ArrayList<DBStat> list = new ArrayList<>(); // 결과를 담을 ArrayList 생성
        Connection con = null;
        PreparedStatement pstmt = null; // 데이터 베이스에 sql문을 전송하고, 결과를 얻어내는 역할하는 클래스(con과 짝궁)
        ResultSet rs = null; // preparedStatement 결과를 받아 저장하는 클래스
        Long time = System.currentTimeMillis();

        try {
            con = Oracle.getConnection(); // DB 연결
            StringBuffer sql = new StringBuffer(); // StringBuffer 객체 준비
            sql.append(Oracle.DBSTAT);
            pstmt = con.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DBStat dbStat = new DBStat();
                dbStat.setStatId(rs.getShort(1));
                dbStat.setValue(rs.getLong(2));
                list.add(dbStat);
            }
            /*
                short = 2byte , byte = 1byte, Long = 8byte, Long = 8byte, Int = 4byte
             */
            size = (Short.BYTES + Long.BYTES) * list.size(); // 바디 사이즈
            int result = (Short.BYTES + Byte.BYTES + Long.BYTES + Long.BYTES + Integer.BYTES); // 헤더 사이즈

            // 헤더 & 바디 부분
            ByteBuffer buf = ByteBuffer.allocate(result + size);

            // 헤더
            buf.putShort((short) type); // type
            buf.put((byte) CLIENT_ID); // clientId
            buf.putLong((long) size); // size
            buf.putLong((long) time); // time
            buf.putInt((int) list.size()); // count


            // 바디
            for (DBStat db : list) {
                buf.putShort((short) db.getStatId());
                buf.putLong((long) db.getValue());
            }
            return buf;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*
                커넥션이 해제 될때 나머지는 자동으로 해제되는 게 정상이라 하지만 그 과정에서 예외가 발생할 경우 자동 해제가 되지 않아 문제가 발생하는 경우가 있음
                해제 순서는 만든 순서의 역순!!!!
                Result -> PreparedStatment -> Connection
             */
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
            if (con != null)
                try {
                    con.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
        }
        return null;
    }

    public static void main(String[] args) {
        DbStatMain dbStatMain = new DbStatMain((byte) 13);
        dbStatMain.getDBSTAT();
    }
}
