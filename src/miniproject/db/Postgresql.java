package miniproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static final String INSERT_JMXHEAPDATA_STAT = "insert into jmx_heap_data(server_id, time, memory_name, memory_init, memory_max, memory_used, memory_committed) values (?,?,?,?,?,?,?)";
    public static final String INSERT_JMXCLASSDATA = "insert into jmx_class_data(server_id, time, total_class_count, load_class_count, unload_class_count) values (?,?,?,?,?)";
    public static final String INSERT_JMXTHREADDATA = "insert into jmx_thread_data(server_id, time, thread_id, thread_cpu_time, thread_user_time) values (?,?,?,?,?)";
    public static final String INSERT_JMXGCDATA = "insert into jmx_gc_data(server_id, time, gc_time, gc_count, gcname) values (?,?,?,?,?)";
    public static final String INSERT_CLASSSTAT = "insert into class_stat_data(server_id, time, class_name, method_name) values (?,?,?,?)";
}
