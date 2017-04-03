
import java.sql.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;


public class DBConnect {
	private Connection con;
	private Statement st;
	private PreparedStatement ps;
	private ResultSet rs;
	
	String filepath = "src\\DBConfig.txt";
	
	public DBConnect() {
		try {
			String[] configData = OpenFile();
			
			String driver = configData[0];
			String url = configData[1];
			String username = configData[2];
			String password = configData[3];
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			st = con.createStatement();

		}catch (Exception e) {
			System.out.println(e);
		}	
	}
	
	public String[] OpenFile() throws IOException{
		FileReader fr = new FileReader(filepath);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numOfLines = 4;
		String[] configData = new String[numOfLines];
		
		int i;
		for(i = 0; i < numOfLines; i++){
			configData[i] = textReader.readLine();
		}
		
		textReader.close();
		return configData;
	}
/*	
	public String GetFilePath() throws IOException{
		try{
			
    	    File temp = new File("");
    	    InputStream stream = new InputStream(temp.getPath());
    	    String filepath = stream.toString();
 
    	    return filePath;
   
    	}catch(IOException e){
    	    e.printStackTrace();
    	}

    }
	}
*/
	//not sure if getDay returns the day or day_of_week - FIXED
	public int matchSlotId(Connection con, Time fromTime, Time endTime, Date date){
		PreparedStatement ps;
		int slotId;
		try{
			ps = con.prepareStatement("SELECT * FROM mydb.availability;");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if(fromTime == rs.getTime(3) && endTime == rs.getTime(4) && date.toLocalDate().getDayOfWeek() == rs.getDate(5).toLocalDate().getDayOfWeek()){
					slotId = rs.getInt(1);
					return slotId;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -999;
	}
	
	public int matchDocId(Connection con, int slotId){
		PreparedStatement ps;
		
		try{
			ps = con.prepareStatement("SELECT slotId, doctorId FROM mydb.availability);");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(slotId == rs.getInt(1))
					return rs.getInt(2);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -999;
	}
	
	public boolean schedReserved(Connection con, int slotId /*to be change soon*/){
		PreparedStatement psTime;
		try {
			psTime = con.prepareStatement("SELECT a.availId, r.Date FROM mydb.reservations r, mydb.availability a;");
			ResultSet rsTime = psTime.executeQuery();

			while(rsTime.next()){
				if(slotId == rsTime.getInt(1)){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		PreparedStatement psDate = con.prepareStatement("SELECT Date FROM mydb.reservations;");
		
		return false;
	}
	
	public void freeSched(Connection con, int slotId, int doctorId /*to change rin*/){
		boolean hasReserved;
		PreparedStatement psFree;
		try{
			psFree = con.prepareStatement("SELECT AvailId, DoctorId, DayOfWeek FROM mydb.availability;");
			ResultSet rsFree = psFree.executeQuery();
			
			hasReserved = schedReserved(con, slotId);
			
			if(hasReserved == false)
				while(rs.next()){
					if(rsFree.getInt(1) == slotId){
						rsFree.deleteRow();
						if(rsFree.rowDeleted() == true)
							break;
					}
				}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void writeReservation(Connection con, Time fromTime, Time toTime, Date date){
		PreparedStatement reservation;
		
		try{
			reservation = con.prepareStatement("INSERT INTO reservations (doctorId, patiendId, slotId, Date)"
											   + "VALUES (, ,"+matchSlotId(con, fromTime, toTime, date)+"," + date + ";");
			ResultSet rs = reservation.executeQuery();
			if(rs.rowInserted() == true)
				System.out.println("Table updated!");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
