package shm.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBUtil {
	
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3307/SHM?useUnicode=true&characterEncoding=utf-8";
	private static String database_name = "root";
	private static String database_password = "951030";	
	
	public static Connection open(){
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, database_name, database_password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
