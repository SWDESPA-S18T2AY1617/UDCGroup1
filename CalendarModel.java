import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class CalendarModel {//extends Observer{
	public CalendarModel(Statement qry) {
		allTasks = new ArrayList<Task>();
		query = qry;
	}

	public Iterator getTasks(GregorianCalendar day, boolean[] viewType, boolean sort, String frameName) {
		String generalQuery = "SELECT * from reservations ",
			   tempQuery = "";
		ArrayList<Task> allSlots = new ArrayList<Task>();
		ResultSet rsSlots = null;
		boolean multiDocs = false;
		Timestamp tempFromTS, tempToTS;
		GregorianCalendar tempFromDT, tempToDT;
		String dayYear = String.valueOf(day.get(GregorianCalendar.YEAR)),
			   dayMonth = day.get(GregorianCalendar.MONTH) + 1 >= 10 ? String.valueOf(day.get(GregorianCalendar.MONTH) + 1) 
					      : "0" + String.valueOf(day.get(GregorianCalendar.MONTH) + 1),
			   dayDate = day.get(GregorianCalendar.DATE) >= 10 ? String.valueOf(day.get(GregorianCalendar.DATE))
					     : "0" + String.valueOf(day.get(GregorianCalendar.DATE));

		if (frameName.equals("Secretary")) {
			tempQuery += "WHERE date(FromTime) = " + dayYear + dayMonth + dayDate + " AND (";
			for(int i=2;i<viewType.length;i++) {
				if (viewType[i]) {
					if (multiDocs)
						tempQuery += " OR DoctorID = " + String.valueOf(i-1) + " ";
					else {
						tempQuery += "DoctorID = " + String.valueOf(i-1) + " ";
						multiDocs = true;
					}
				}
			}
			tempQuery += ")";
			if (!viewType[0] && !viewType[1])
				return allSlots.iterator();
			else if (!viewType[0] && viewType[1])
				tempQuery += " AND PatientID is not null";
			else if (viewType[0] && !viewType[1])
				tempQuery += " AND PatientID is null";
		}
		else if (frameName.contains("Doctor")) {
			int docNum = Integer.parseInt(frameName.substring(7));
			tempQuery += "WHERE DoctorID = " + docNum + " AND date(FromTime) = " + dayYear + dayMonth + dayDate;
			if (!viewType[0] && !viewType[1])
				return allSlots.iterator();
			else if (!viewType[0] && viewType[1])
				tempQuery += " AND PatientID is not null";
			else if (viewType[0] && !viewType[1])
				tempQuery += " AND PatientID is null";
		}
		else if (frameName.contains("Client")) {
			tempQuery += "WHERE date(FromTime) = " + dayYear + dayMonth + dayDate + " AND (";
			for(int i=2;i<viewType.length;i++) {
				if (viewType[i]) {
					if (multiDocs)
						tempQuery += " OR DoctorID = " + String.valueOf(i-1) + " ";
					else {
						tempQuery += "DoctorID = " + String.valueOf(i-1) + " ";
						multiDocs = true;
					}
				}
			}
			tempQuery += ") AND PatientID is null";
		}

		generalQuery += tempQuery;
		System.out.println(generalQuery);
		try {
			rsSlots = query.executeQuery(generalQuery);
			while(rsSlots.next()) {
				tempFromTS = rsSlots.getTimestamp("FromTime");
				tempToTS = rsSlots.getTimestamp("ToTime");
				tempFromDT = new GregorianCalendar(tempFromTS.getYear() + 1900, tempFromTS.getMonth(), tempFromTS.getDate(),
												   tempFromTS.getHours(), tempFromTS.getMinutes());
				tempToDT = new GregorianCalendar(tempToTS.getYear() + 1900, tempToTS.getMonth(), tempToTS.getDate(),
												   tempToTS.getHours(), tempToTS.getMinutes());
				allSlots.add(new Task(tempFromDT, tempToDT, "Doctor " + String.valueOf(rsSlots.getInt("DoctorID"))));
			}
		} catch (Exception ex) {System.out.println("Exception caught!");}
		if(allSlots == null)
			System.out.println("HI");
		else System.out.println("Im not empty");
		return allSlots.iterator();
	}
	
	public Iterator getTasks(GregorianCalendar fromDay, GregorianCalendar toDay, boolean[] viewType, boolean sort, String frameName) {
		String generalQuery = "SELECT * from reservations ",
			   tempQuery = "";
		ArrayList<Task> allSlots = new ArrayList<Task>();
		ResultSet rsSlots = null;
		boolean multiDocs = false;
		Timestamp tempFromTS, tempToTS;
		GregorianCalendar tempFromDT, tempToDT;
		String fdayYear = String.valueOf(fromDay.get(GregorianCalendar.YEAR)),
			   fdayMonth = fromDay.get(GregorianCalendar.MONTH) >= 10 ? String.valueOf(fromDay.get(GregorianCalendar.MONTH)) 
					      : "0" + String.valueOf(GregorianCalendar.MONTH),
			   fdayDate = fromDay.get(GregorianCalendar.DATE) >= 10 ? String.valueOf(fromDay.get(GregorianCalendar.DATE))
					     : "0" + String.valueOf(fromDay.get(GregorianCalendar.DATE));
		String tdayYear = String.valueOf(toDay.get(GregorianCalendar.YEAR)),
			   tdayMonth = toDay.get(GregorianCalendar.MONTH) >= 10 ? String.valueOf(toDay.get(GregorianCalendar.MONTH)) 
					      : "0" + String.valueOf(GregorianCalendar.MONTH),
			   tdayDate = toDay.get(GregorianCalendar.DATE) >= 10 ? String.valueOf(toDay.get(GregorianCalendar.DATE))
					     : "0" + String.valueOf(toDay.get(GregorianCalendar.DATE));

		if (frameName.equals("Secretary")) {
			tempQuery += "WHERE date(FromTime) BETWEEN " + fdayYear + fdayMonth + fdayDate + 
			          " AND " + tdayYear + tdayMonth + tdayDate + " AND (";
			for(int i=2;i<viewType.length;i++) {
				if (viewType[i]) {
					if (multiDocs)
						tempQuery += " OR DoctorID = " + String.valueOf(i-1) + " ";
					else {
						tempQuery += "DoctorID = " + String.valueOf(i-1) + " ";
						multiDocs = true;
					}
				}
			}
			tempQuery += ")";
			if (!viewType[0] && !viewType[1])
				return allSlots.iterator();
			else if (!viewType[0] && viewType[1])
				tempQuery += " AND PatientID is not null";
			else if (viewType[0] && !viewType[1])
				tempQuery += " AND PatientID is null";
		}
		else if (frameName.contains("Doctor")) {
			int docNum = Integer.parseInt(frameName.substring(7));
			tempQuery += "WHERE date(FromTime) BETWEEN " + fdayYear + fdayMonth + fdayDate + 
			          " AND " + tdayYear + tdayMonth + tdayDate + " AND DoctorID = " + docNum;
			if (!viewType[0] && !viewType[1])
				return allSlots.iterator();
			else if (!viewType[0] && viewType[1])
				tempQuery += " AND PatientID is not null";
			else if (viewType[0] && !viewType[1])
				tempQuery += " AND PatientID is null";
		}
		else if (frameName.contains("Client")) {
			tempQuery += "WHERE date(FromTime) BETWEEN " + fdayYear + fdayMonth + fdayDate + 
			          " AND " + tdayYear + tdayMonth + tdayDate + " AND (";
			for(int i=2;i<viewType.length;i++) {
				if (viewType[i]) {
					if (multiDocs)
						tempQuery += " OR DoctorID = " + String.valueOf(i-1) + " ";
					else {
						tempQuery += "DoctorID = " + String.valueOf(i-1) + " ";
						multiDocs = true;
					}
				}
			}
			tempQuery += ") AND PatientID is null";
		}

		generalQuery += tempQuery;
		try {
			rsSlots = query.executeQuery(generalQuery);
			while(rsSlots.next()) {
				tempFromTS = rsSlots.getTimestamp("FromTime");
				tempToTS = rsSlots.getTimestamp("ToTime");
				tempFromDT = new GregorianCalendar(tempFromTS.getYear() + 1900, tempFromTS.getMonth(), tempFromTS.getDate(),
												   tempFromTS.getHours(), tempFromTS.getMinutes());
				tempToDT = new GregorianCalendar(tempToTS.getYear() + 1900, tempToTS.getMonth(), tempToTS.getDate(),
												   tempToTS.getHours(), tempToTS.getMinutes());
				allSlots.add(new Task(tempFromDT, tempToDT, "Doctor " + String.valueOf(rsSlots.getInt("DoctorID"))));
			}
		} catch (Exception ex) {System.out.println("Exception caught!");}

		return allSlots.iterator();
	}

	public String addTask(Task nTask) {
		boolean noOverlap = true;
		try {
			ResultSet allSlots = query.executeQuery("select ID, DoctorID, FromTime, ToTime from reservations where DoctorID = 1");
			while (allSlots.next() && noOverlap) {
				Timestamp fromDT = allSlots.getTimestamp("FromTime");
				Timestamp toDT = allSlots.getTimestamp("ToTime");
				Task cmpTask = new Task(new GregorianCalendar(fromDT.getYear() + 1900, fromDT.getMonth(),
										fromDT.getDate(), fromDT.getHours(), fromDT.getMinutes()),
										new GregorianCalendar(toDT.getYear() + 1900, toDT.getMonth(), 
										toDT.getDate(), toDT.getHours(), toDT.getMinutes()));
				if (cmpTask.checkOverlap(nTask))
					noOverlap = false;
			}
			if (noOverlap) {
				addToDB(nTask);
				return "Successfully added!";
			}
			else 
				return "Sorry! Slot already taken!";
		} catch (Exception ex) {
			System.out.println("Exception found!");
		}

		return "Successfully added";
	}

	private void addToDB(Task newTask) {
		Object fromTime = new java.sql.Timestamp(newTask.getStartDatetime().getTime().getTime());
		Object toTime = new java.sql.Timestamp(newTask.getEndDatetime().getTime().getTime());
		String strQuery = "insert into reservations (DoctorID, FromTime, ToTime) values ('" +
		                  newTask.getDocID() + "', '" + fromTime + "', '" + toTime + "')";
		try {
			query.executeUpdate(strQuery);
			ResultSet rs = query.executeQuery("select * from reservations");
		} catch (Exception ex) {
			System.out.println("Exception Caught! " + ex);
		}
	}
	
	public GregorianCalendar getWeekEnd(GregorianCalendar cal) {
		GregorianCalendar end = cal;
		end.add(end.DATE, 5);
		return end;
	}

	public void saveEvents(){
		store.write(allTasks);
	}
	
//*****************************new methods*****************************
	public Iterator getUserReservations(int id) {
		String strQuery = "SELECT reservations.Date, availability.FromTime, availability.ToTime, doctor.Name" +
				" FROM reservations, doctor, availability" +
				" WHERE reservations.PatientID = '" + id + 
				"' AND doctor.ID = reservations.doctorID" +
				" AND reservations.SlotId = availability.AvailID";
		ResultSet rs = null;
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		try {
			rs = query.executeQuery(strQuery);
		} catch (SQLException e1) {
			System.out.println("Cannot retrieve user reservations");
			e1.printStackTrace();
		}
			 
		try {
			while(rs.next()) {	
				Timestamp fromDT = rs.getTimestamp("availability.FromTime");
				Timestamp toDT = rs.getTimestamp("availability.ToTime");
				String docName = rs.getString("doctor.Name");
				Task t = new Task(new GregorianCalendar(fromDT.getYear() + 1900, fromDT.getMonth(),
										fromDT.getDate(), fromDT.getHours(), fromDT.getMinutes()),
										new GregorianCalendar(toDT.getYear() + 1900, toDT.getMonth(), 
										toDT.getDate(), toDT.getHours(), toDT.getMinutes()), docName);
				tasks.add(t);
			}
		} catch (SQLException e) {
			System.out.println("Unable to get values from resultset user reservations");
		}
				
		return tasks.iterator();
	}
		
	public void cancelReservation(int id, Task newTask) {
		Object fromTime = new java.sql.Timestamp(newTask.getStartDatetime().getTime().getTime());
		String strQuery = "INSERT INTO reservations (PatientID) values (null) " + 
							" WHERE PatientID = '" + id + 
							"' AND DoctorID = '" + newTask.getDocID() + 
							"' AND Date = '" + newTask.getDateFormatted() + "'";
		try {
			query.executeUpdate(strQuery);
		} catch (Exception ex) {
			System.out.println("Unable to cancel appointment");
		}
	}
	
	public void bookReservation(int id, Task newTask) {
		String strQuery = "INSERT INTO reservations (PatientID) values ('" + id +
							"' WHERE PatientID IS NULL" +
							" AND DoctorID = '" + newTask.getDocID() +
							"' Date = '" + newTask.getDateFormatted() + "'";
		
		try {
			query.executeUpdate(strQuery);
		} catch (Exception ex) {
			System.out.println("Unable to book appointment");
		}
	}
	private String updateTask(Task checkTask, Task newTask) {
		if (this.deleteTask(checkTask).equalsIgnoreCase("Successfully deleted!")){
			this.addTask(newTask);
			return "Successfully updated!";
		}
		else
			return "Sorry! Slot doesn't exist!";
	}
	
	private String deleteTask(Task newTask){
		boolean noOverlap = true;
		boolean nullPatient = false;
		try {
			ResultSet allSlots = query.executeQuery("select ID, DoctorID, PatientID, FromTime, ToTime from reservations where DoctorID = 1");
			while (allSlots.next()) {
				nullPatient = !allSlots.getObject("PatientID").equals(null);
				Timestamp fromDT = allSlots.getTimestamp("FromTime");
				Timestamp toDT = allSlots.getTimestamp("ToTime");
				Task cmpTask = new Task(new GregorianCalendar(fromDT.getYear() + 1900, fromDT.getMonth(), 
										fromDT.getDate(), fromDT.getHours(), fromDT.getMinutes()),
										new GregorianCalendar(toDT.getYear() + 1900, toDT.getMonth(), 
										toDT.getDate(), toDT.getHours(), toDT.getMinutes()));
				if (cmpTask.checkOverlap(newTask))
					noOverlap = false;
			}
			if (!noOverlap && !nullPatient) {
				removeFromDB(newTask);
				return "Successfully deleted!";
			}
			else 
				return "Sorry! Slot doesn't exist!";
		} catch (Exception ex) {
			System.out.println("Exception found!");
		}
		return "Successfully deleted";
	}
	
	private void removeFromDB(Task newTask) {
		Object fromTime = new java.sql.Timestamp(newTask.getStartDatetime().getTime().getTime());
		Object toTime = new java.sql.Timestamp(newTask.getEndDatetime().getTime().getTime());
		
		String strQuery = "DELETE FROM Reservations WHERE DoctorID = " + newTask.getDocID() + " AND FromTime = " + fromTime + " AND ToTime = " + toTime + "And PatientID IS NULL";
		try {
			query.executeUpdate(strQuery);
			ResultSet rs = query.executeQuery("select * from reservations");
		} catch (Exception ex) {
			System.out.println("Exception Caught! " + ex);
		}
	}
//*********************************end*********************************

/*	public void deleteTD() {
		for(int i=0;i<allTasks.size();i++) {
			if(allTasks.get(i).getDone())  {
				allTasks.remove(i);
				i--;
			}
		}
	}*/

/*	public int getToDoLeft() {
		int left = 0;
		for(int i=0;i<allTasks.size();i++) {
			if(allTasks.get(i).getType() == Type.TO_DO &&
				!allTasks.get(i).getDone())
					left++;
		}
		return left;
	}
*/
	private ArrayList<Task> sortTasks(ArrayList<Task> toSort) {
		Collections.sort(toSort);
		return toSort;
	}

	private void setDocID(int id) {
		this.id = id;
	}

	private void setDocName(String name) {
		this.name = name;
	}

	private int getDocID() {
		return id;
	}

	private String getDocName() {
		return name;
	}


	private Statement query;
	private int id;
	private String name;
	private ArrayList<Task> allTasks = new ArrayList<Task>();
	private Storage store = new Storage();
	private CalendarController controller;
}