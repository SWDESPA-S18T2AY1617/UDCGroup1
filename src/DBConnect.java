import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
	private Connection con;
	private String query;
	private Statement st;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public DBConnect() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/UDC?useSSL=false";
			String username = "root";
			String password = "";
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			st = con.createStatement();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

}
