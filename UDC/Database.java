import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Database {
	private static String url;
	private static String username;
	private static String password;
	private static String driverName;
	private static Statement query;
	private static BufferedReader reader;
	private static Connection connection;
	private static final String database = "mydb";
	private static final String file = "C:\\Users\\Angel\\Documents\\2nd year - 2nd Term\\SWDESPA\\UDC\\db.config";
	
	
	private static void load () {
		try {
			reader = new BufferedReader(new FileReader(file));
			driverName = reader.readLine();
			url = reader.readLine();
			username = reader.readLine();
			password = reader.readLine();
		} catch (IOException e) {
			System.err.println("Error! " + file + " not loaded!");
		}
	}
	
	public static boolean openConnection () {
		try {
			if(connection == null || connection.isClosed()) {
				if(connection == null)
					load();
				
				try {
					Class.forName(driverName);
					connection = DriverManager.getConnection(url + database + "?autoReconnect=true&useSSL=false", username, password);
					query = connection.createStatement();
					System.out.println("Connection to Database successful!");
					return true;
				} catch (Exception ex) {
					System.err.println("Exception Caught! Unable to connect to " + database);
					return false;
				}
			} else {
				System.err.println("Connection already open!");
				return false;
			}
		} catch (Exception ex) {
			System.err.println("Cannot determine connection!");
			return false;
		}
	}
		
	public static Statement getStatement () {
		return query;
	}
	
	public static void closeConnection() {
        try {
            System.out.println("Closed Connection to Database");
            connection.close();
        } catch (SQLException e) {
           System.err.println("Cannot close connection");
        } catch (NullPointerException n) {
        	System.err.println("No connection open");
        }
	}

	public static boolean isOpen() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			return false;
		} catch (NullPointerException n) {
			return false;
		}
	}
}