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
		String dayYear = String.valueOf(day.get(GregorianCalendar.YEAR)),
			   dayMonth = day.get(GregorianCalendar.MONTH) + 1 >= 10 ? String.valueOf(day.get(GregorianCalendar.MONTH) + 1) 
					      : "0" + String.valueOf(day.get(GregorianCalendar.MONTH) + 1),
			   dayDate = day.get(GregorianCalendar.DATE) >= 10 ? String.valueOf(day.get(GregorianCalendar.DATE))
					     : "0" + String.valueOf(day.get(GregorianCalendar.DATE));

		if (frameName.equals("Secretary")) {
			tempQuery = generateSecQuery(false, dayYear + dayMonth + dayDate, dayYear + dayMonth + dayDate, 
										 viewType);
			if (tempQuery.equals("None"))
				return allSlots.iterator();
		}
		else if (frameName.contains("Doctor")) {
			int docNum = Integer.parseInt(frameName.substring(7));
			tempQuery = generateDocQuery(false, dayYear + dayMonth + dayDate, dayYear + dayMonth + dayDate, 
										 viewType, viewType[0], viewType[1]);
			if (tempQuery.equals("None"))
				return allSlots.iterator();
		}
		else if (frameName.contains("Client")) {
			if (!checkIfNoDocs(viewType))
				return allSlots.iterator();

			tempQuery = generateClientQuery(false, dayYear + dayMonth + dayDate, dayYear + dayMonth + dayDate,
											viewType);
		}

		generalQuery += tempQuery;
		return getSlotsFromDB(generalQuery, sort).iterator();
	}
	
	public Iterator getTasks(GregorianCalendar fromDay, GregorianCalendar toDay, boolean[] viewType, boolean sort, String frameName) {
		String generalQuery = "SELECT * from reservations ",
			   tempQuery = "";
		ArrayList<Task> allSlots = new ArrayList<Task>();
		String fdayYear = String.valueOf(fromDay.get(GregorianCalendar.YEAR)),
			   fdayMonth = fromDay.get(GregorianCalendar.MONTH) + 1 >= 10 ? String.valueOf(fromDay.get(GregorianCalendar.MONTH + 1)) 
					      : "0" + String.valueOf(fromDay.get(GregorianCalendar.MONTH) + 1),
			   fdayDate = fromDay.get(GregorianCalendar.DATE) >= 10 ? String.valueOf(fromDay.get(GregorianCalendar.DATE))
					     : "0" + String.valueOf(fromDay.get(GregorianCalendar.DATE));
		String tdayYear = String.valueOf(toDay.get(GregorianCalendar.YEAR)),
			   tdayMonth = toDay.get(GregorianCalendar.MONTH) + 1 >= 10 ? String.valueOf(toDay.get(GregorianCalendar.MONTH) + 1) 
					      : "0" + String.valueOf(toDay.get(GregorianCalendar.MONTH) + 1),
			   tdayDate = toDay.get(GregorianCalendar.DATE) >= 10 ? String.valueOf(toDay.get(GregorianCalendar.DATE))
					     : "0" + String.valueOf(toDay.get(GregorianCalendar.DATE));

		System.out.println("year" + tdayYear + "month" + tdayMonth + "day" + tdayDate);
		if (frameName.equals("Secretary")) {
			tempQuery = generateSecQuery(true, fdayYear + fdayMonth + fdayDate, tdayYear + tdayMonth + tdayDate, 
										 viewType);
			if (tempQuery.equals("None"))
				return allSlots.iterator();
		}
		else if (frameName.contains("Doctor")) {
			int docNum = Integer.parseInt(frameName.substring(7));
			tempQuery = generateDocQuery(true, fdayYear + fdayMonth + fdayDate, tdayYear + tdayMonth + tdayDate, 
										 viewType, viewType[0], viewType[1]);
			if (tempQuery.equals("None"))
				return allSlots.iterator();
		}
		else if (frameName.contains("Client")) {
			if (!checkIfNoDocs(viewType))
				return allSlots.iterator();

			tempQuery = generateClientQuery(true, fdayYear + fdayMonth + fdayDate, tdayYear + tdayMonth + tdayDate,
											viewType);
		}

		generalQuery += tempQuery;
		return getSlotsFromDB(generalQuery, sort).iterator();
	}

	public ArrayList<Task> getSlotsFromDB(String queries,boolean sort) {
		ArrayList<Task> allSlots = new ArrayList<Task>();
		ResultSet rsSlots = null;
		Timestamp tempFromTS, tempToTS;
		GregorianCalendar tempFromDT, tempToDT;
		System.out.println(queries);
		try {
			rsSlots = query.executeQuery(queries);
			while(rsSlots.next()) {
				tempFromTS = rsSlots.getTimestamp("FromTime");
				tempToTS = rsSlots.getTimestamp("ToTime");
				tempFromDT = new GregorianCalendar(tempFromTS.getYear() + 1900, tempFromTS.getMonth(), tempFromTS.getDate(),
												   tempFromTS.getHours(), tempFromTS.getMinutes());
				tempToDT = new GregorianCalendar(tempToTS.getYear() + 1900, tempToTS.getMonth(), tempToTS.getDate(),
												   tempToTS.getHours(), tempToTS.getMinutes());
				allSlots.add(new Task(tempFromDT, tempToDT, "Doctor " + String.valueOf(rsSlots.getInt("DoctorID"))));
			}
		} catch (Exception ex) {System.out.println("Exception caught! getting slots");}

		if(sort)
			sortTasks(allSlots);

		return allSlots;
	} 

	public String genDateParam(boolean weekly, String fromDate, String toDate) {
		if (weekly)
			return " WHERE date(FromTime) BETWEEN " + fromDate + " AND " + toDate + " AND (";
		return "WHERE date(FromTime) = " + fromDate+ " AND (";
	}

	public String genViewParam(boolean free, boolean res) {
		if (!free && !res)
			return "None";
		else if (!free && res)
			return " AND PatientID is not null";
		else if (free && !res)
			return " AND PatientID is null";
		return "";
	}

	public String genDocParam(boolean[] vType) {
		String addStr = "";
		boolean multiDocs = false;
		for(int i=2;i<vType.length;i++) {
			if (vType[i]) {
				if (multiDocs)
					addStr += " OR DoctorID = " + String.valueOf(i-1) + " ";
				else {
					addStr += "DoctorID = " + String.valueOf(i-1) + " ";
					multiDocs = true;
				}
			}
		}
		return addStr;
	}

	public boolean checkIfNoDocs(boolean[] vType) {
		boolean atLeastOne = false;
		for (int i=2;i<vType.length && !atLeastOne;i++) {
			if (vType[i])
				atLeastOne = true;
		}
		return atLeastOne;
	}

	public String generateSecQuery(boolean weekly, String fromDate, String toDate, boolean[] vType) {
		String genString = genDateParam(weekly, fromDate, toDate);
		String viewString = genViewParam(vType[0], vType[1]);
		String docString = "";
		if (viewString.equals("None"))
			return viewString;
		else {
			genString += " " + genDocParam(vType);
			genString += ")";
			return genString += viewString;
		}
	}

	public String generateDocQuery(boolean weekly, String fromDate, String toDate, boolean[] vType, boolean free, boolean reserved) {
		String genString = genDateParam(weekly, fromDate, toDate);
		String viewString = genViewParam(free, reserved);
		if (viewString.equals("None"))
			return viewString;
		else{
			genString += " " + genDocParam(vType);
			genString += ")";
			return genString += viewString;
		}
	}

	public String generateClientQuery(boolean weekly, String fromDate, String toDate, boolean[] viewType) {
		return genDateParam(weekly, fromDate, toDate) + " " + genDocParam(viewType) + ") AND PatientID is null"; 
	}

	public String addTask(Task nTask) {
		boolean noOverlap = true;
		try {
			ResultSet allSlots = query.executeQuery("select ID, DoctorID, FromTime, ToTime from reservations");
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