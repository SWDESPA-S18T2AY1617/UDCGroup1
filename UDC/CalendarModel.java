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
		ArrayList<Task> taskMonthYear = new ArrayList<Task>();
		boolean flag = false;
		for(boolean x: viewType){
			if(!x)
				flag = true;
		}
		if(flag == false){
			return taskMonthYear.iterator();
		}
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
	
	public Iterator getTasks(GregorianCalendar fromDay, GregorianCalendar toDay, boolean[] viewType, boolean sort) {
		ArrayList<Task> taskMonthYear = new ArrayList<Task>();
		boolean flag = false;
		for(boolean x: viewType){
			if(!x)
				flag = true;
		}
		if(flag == false){
			return taskMonthYear.iterator();
		}
		for (int i=0;i<5;i++) {
			fromDay.add(fromDay.DATE, i);
			for (int j=0;j<allTasks.size();i++) {
				if(allTasks.get(i).findEvent(fromDay, viewType)) {
					taskMonthYear.add(allTasks.get(i));
				}
			}
		} if (sort) {
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