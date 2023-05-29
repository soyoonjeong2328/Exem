package training.db;

import java.sql.*;

public class Postgresql {
    private static Connection conn;
    private static final String POSTGRE_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String POSTGRE_USER = "postgres";
    private static final String POSTGRE_PASSWORD = "1234";

    static {
        try{
            Class.forName("org.postgresql.Driver");
            System.out.println("드라이버 연결 성공");
        }catch (ClassNotFoundException e){
            System.out.println("Exception 발생 : " + e.toString());
        }
    }

    public static Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(POSTGRE_URL, POSTGRE_USER, POSTGRE_PASSWORD);
        return conn;
    }

    public static void main(String[] args) throws SQLException {
        Postgresql.getConnection();
    }

    public static final String INSERT_DB_STAT = "insert into DBStat(partition_Key, db_Id, time, stat_Id, value) values (?,?,?,?,?)";

    public static final String INSERT_ORA_SESSION_STAT = "insert into ORA_SESSION_STAT(partition_key, db_Id, time, sid, logon_Time, con_Id, " +
            "serial, status, taddr, row_Wait_File, row_Wait_Block, row_Wait_Row, row_Wait_Object, schema_Name, module,action, client_Info, " +
            "command_Type, sql_Addr, sql_Hash, sql_Id, prev_Sql_Addr, prev_Sql_Hash, prev_Sql_Id ) " +
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_ORA_SQL_STAT =
            "insert into ORA_SQL_STAT(partition_key, db_id, time, sql_addr, sql_hash, sql_id, sql_plan_hash, user_name, program,module, action, machine, os_user, elapsed_time, cpu_time, wait_time, logical_reads, physical_reads, redo_size, execution_count, sort_disk, sort_rows, table_fetch_by_rowid, table_fetch_continued_by_rowid, table_scan_blocks_gotten, table_scan_rows_gotten) " +
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}
