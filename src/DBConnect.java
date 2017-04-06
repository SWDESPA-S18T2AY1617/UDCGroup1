
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
	
	public void SecretaryAllView(Connection con, ArrayList<Task> SecreTask) throws IOException{
//		ArrayList<Task> everySingleEvent;
		
		PreparedStatement ps;
		PreparedStatement doctor;
		ResultSet rs;
		ResultSet rDoctor;
		
		try {
			ps = con.prepareStatement("SELECT * FROM mydb.reservations");
			rs = ps.executeQuery();
			doctor = con.prepareStatement("SELECT * FROM mydb.doctor");
			rDoctor = doctor.executeQuery();
			Type enumInstance = null;	
			while(rs.next()){
				int docID = rs.getInt(2);
				while(rDoctor.next()){
					int docIdKey = rDoctor.getInt(1);
					if(docIdKey == docID){
//						String docName = rDoctor.getString(2);
						String docType = Integer.toString(docIdKey);
						enumInstance = Type.values()[docIdKey];
						break;
					}
				}
				while(rDoctor.previous()){
					//just return it to the beginning
				}
				int ID = rs.getInt(1);
				int patientID = rs.getInt(3);
				Date onDate = rs.getDate(4);
				Time fromTime = rs.getTime(5);
				Time endTime = rs.getTime(6);
				
				GregorianCalendar stDate = null;
				stDate.setTime(onDate);
				stDate.setTime(fromTime);
				GregorianCalendar endDate = null;
				endDate.setTime(onDate);
				endDate.setTime(endTime);
				
				PreparedStatement patient = con.prepareStatement("SELECT * FROM mydb.patient");
				ResultSet rPatient = patient.executeQuery();
				String patientName = null;
				String color = null;					//idk what the color types are pls fill
				while(rPatient.next()){
					if(patientID == rPatient.getInt(1)){
						patientName = rPatient.getString(2);
						break;
					}
				}
				
				Task toArray = new Task(enumInstance, stDate, endDate, "Appointment with"+patientName, color);
				
				SecreTask.add(toArray);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
