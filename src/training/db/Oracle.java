package training.db;

import java.sql.*;

/*
    oracle 연결부분 싹 다 갈아엎기
    - 오라클이 실행 될때마다 class.forName()이 실행되어야 하는지 생각!
    - driver, url, user, password 전부 밖으로 빼주기
    - return null이 되는게 맞는지 다시 생각해보기
 */
public class Oracle {
    public static Connection conn = null;
    /*
        final 변수
        - final로 선언된 변수는, 값의 초기화만 가능하고 그 값의 변경 및 새로운 할당 불가능
        - 일반적으로 final이 붙은 상수 변수는 static을 함께 붙여서 사용

     */
    public static final String driver = "oracle.jdbc.driver.OracleDriver";
    public static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:user";
    public static final String ORACLE_USER = "system";
    public static final String ORACLE_PASSWORD = "Exem1234";
    /*
        static 초기화 블럭
        - static 초기화를 사용하면 맨 처음 static을 부르는 상황이 오면 자동으로 초기화
        - static은 단 한번만 호출된다는 차이점
     */
    static {
        try{
            Class.forName(driver);
            System.out.println("드라이버 연결 성공 ");
        }catch (ClassNotFoundException e){
            System.out.println("드라이버 연결 실패 : " + e.toString());
            System.exit(-1); // 예외 발생 비정상 종료
        }
    }

    public static Connection getConnection() throws SQLException{

        try{
            conn = DriverManager.getConnection(ORACLE_URL, ORACLE_USER, ORACLE_PASSWORD);
            System.out.println("연결 성공");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException {
        Oracle.getConnection();
    }

    public static final String DBSTAT = "SELECT STATISTIC# , value FROM \"V$SYSSTAT\"";

    public static final String SessionStatSql = "SELECT  sid, logon_time, con_id, serial#, status, taddr, row_wait_file#, row_wait_block#, row_wait_row#, row_wait_obj#, schemaname, module,\n" +
            "action, client_info, command, sql_address, sql_hash_value, sql_id, prev_sql_addr, prev_hash_value, prev_sql_id\n" +
            "FROM    v$session";

    public static final String ORA_SQL_STAT= "WITH sesstat AS (\n" +
            "  SELECT  sid,\n" +
            "          MAX ( CASE WHEN statistic# = 14   THEN value ELSE 0 END ) as logical_reads,\n" +
            "          MAX ( CASE WHEN statistic# = 148  THEN value ELSE 0 END ) as physical_reads,\n" +
            "          MAX ( CASE WHEN statistic# = 288  THEN value ELSE 0 END ) as redo_size,\n" +
            "          MAX ( CASE WHEN statistic# = 1718 THEN value ELSE 0 END ) as sort_disk,\n" +
            "          MAX ( CASE WHEN statistic# = 1719 THEN value ELSE 0 END ) as sort_rows,\n" +
            "          MAX ( CASE WHEN statistic# = 946  THEN value ELSE 0 END ) as table_fetch_by_rowid,\n" +
            "          MAX ( CASE WHEN statistic# = 947  THEN value ELSE 0 END ) as table_fetch_continued_by_rowid,\n" +
            "          MAX ( CASE WHEN statistic# = 1010  THEN value ELSE 0 END ) as table_scan_rows_gotten,\n" +
            "          MAX ( CASE WHEN statistic# = 1015  THEN value ELSE 0 END ) as table_scan_blocks_gotten\n" +
            "        \n" +
            "  FROM v$sesstat\n" +
            "  GROUP BY sid\n" +
            ")\n" +
            "\n" +
            "SELECT  s.sql_address,\n" +
            "        s.sql_hash_value,\n" +
            "        s.sql_id,\n" +
            "        t.plan_hash_value,\n" +
            "        s.username,\n" +
            "        s.program,\n" +
            "        s.module,\n" +
            "        s.action,\n" +
            "        s.machine,\n" +
            "        s.osuser,\n" +
            "        t.elapsed_time,\n" +
            "        t.cpu_time,\n" +
            "        s.wait_time,\n" +
            "        s.logical_reads,\n" +
            "        s.physical_reads,\n" +
            "        s.redo_size,\n" +
            "        t.executions,\n" +
            "        s.sort_disk,\n" +
            "        s.sort_rows,\n" +
            "        s.table_fetch_by_rowid,\n" +
            "        s.table_fetch_continued_by_rowid,\n" +
            "        s.table_scan_rows_gotten,\n" +
            "        s.table_scan_blocks_gotten\n" +
            "FROM (\n" +
            "  SELECT  ss.sql_address as sql_address,\n" +
            "          ss.sql_hash_value as sql_hash_value,\n" +
            "          ss.sql_id as sql_id,\n" +
            "          ss.username as username,\n" +
            "          ss.program as program,\n" +
            "          ss.module as module,\n" +
            "          ss.action as action,\n" +
            "          ss.machine as machine,\n" +
            "          ss.osuser as osuser,\n" +
            "          ss.wait_time as wait_time,\n" +
            "          st.logical_reads as logical_reads,\n" +
            "          st.physical_reads as physical_reads,\n" +
            "          st.redo_size as redo_size,\n" +
            "          st.sort_disk as sort_disk,\n" +
            "          st.sort_rows as sort_rows,\n" +
            "          st.table_fetch_by_rowid as table_fetch_by_rowid,\n" +
            "          st.table_fetch_continued_by_rowid as table_fetch_continued_by_rowid,\n" +
            "          st.table_scan_rows_gotten as table_scan_rows_gotten,\n" +
            "          st.table_scan_blocks_gotten as table_scan_blocks_gotten\n" +
            "  FROM    v$session ss, sesstat st\n" +
            "  WHERE   ss.sid = st.sid\n" +
            ") s, v$sqlstats t\n" +
            "WHERE s.sql_id = t.sql_id";
}

/*
    Class.Name() 동작방식
    - 클래스 로더라는 녀석을 통해 해당 데이터 베이스 드라이버를 로드할뿐 데이터베이스 연결과 관해 아무런 동작 x
    - Class.forName() 메소드를 호출하면 인스턴스 생성과 초기화가 이루어짐.
 */