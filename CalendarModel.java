import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class CalendarModel {
	public CalendarModel(Statement qry) {
		allTasks = store.read();
		query = qry;
	}

	public CalendarModel(int id, String name) {
		allTasks = store.read();
		setDocID(id);
		setDocName(name);
		freeDates = new ArrayList<GregorianCalendar>();
		occupiedDates = new ArrayList<GregorianCalendar>();
	}

	public Iterator getTasks(GregorianCalendar day, int viewType, boolean sort) {
		ArrayList<Task> taskMonthYear = new ArrayList<Task>();
		if(viewType == 0)
			return taskMonthYear.iterator();
		for(int i=0;i<allTasks.size();i++) {
			if(allTasks.get(i).findEvent(day,viewType)) {
					taskMonthYear.add(allTasks.get(i));	
			}
		}
		if(sort){
			taskMonthYear = sortTasks(taskMonthYear);
		}
		return taskMonthYear.iterator();
	}

	public String addTask(Task nTask) {
		boolean noOverlap = true;
		try {
			ResultSet allSlots = query.executeQuery("select ID, DoctorID, FromTime, ToTime from reservations where DoctorID = 1");
			while (allSlots.next() && noOverlap) {
				Timestamp fromDT = allSlots.getTimestamp("FromTime");
				Timestamp toDT = allSlots.getTimestamp("ToTime");
				Task cmpTask = new Task(new GregorianCalendar(fromDT.getYear(), fromDT.getMonth(), 
										fromDT.getDate(), fromDT.getHours(), fromDT.getMinutes()),
										new GregorianCalendar(toDT.getYear(), toDT.getMonth(), 
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

	public void saveEvents(){
		store.write(allTasks);
	}

	/*public void deleteTD() {
		for(int i=0;i<allTasks.size();i++) {
			if(allTasks.get(i).getDone())  {
				allTasks.remove(i);
				i--;
			}
		}
	}

	public int getToDoLeft() {
		int left = 0;
		for(int i=0;i<allTasks.size();i++) {
			if(allTasks.get(i).getType() == Type.TO_DO &&
				!allTasks.get(i).getDone())
					left++;
		}
		return left;
	}*/

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

	public ArrayList<GregorianCalendar> getFreeDates() {
		return freeDates;
	}
	
	public ArrayList<GregorianCalendar> getOccupiedDates() {
		return occupiedDates;
	}


	/*** MySQL Connection ***/
	/*private String driverName = "com.mysql.jdbc.Driver";
	private String localhost = "127.0.0.1";
	private String url = "jdbc:mysql//127.0.0.1:3306/";
	private String database = "mydb";
	private String username = "root";
	private String password = "password";
	private Connection connection;*/

	private Statement query;
	private int id;
	private String name;
	private ArrayList<GregorianCalendar> freeDates, occupiedDates;
	private ArrayList<Task> allTasks = new ArrayList<Task>();
	private CalendarController controller;
	private Storage store = new Storage();
}