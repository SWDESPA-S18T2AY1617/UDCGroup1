import java.util.*;
import java.io.*;

public class Task implements Serializable, Comparable<Task>{
	public Task(GregorianCalendar startDT, GregorianCalendar endDT, String docName, String day) {
		this.name = docName;
		startDateTime = startDT;
		endDateTime = endDT;
		this.day = day;
		docID = Integer.parseInt(docName.substring(7));
	}

	public Task(GregorianCalendar startDT, GregorianCalendar endDT) {
		startDateTime = startDT;
		endDateTime = endDT;
		this.name = "Doctor";
		this.day = "Day";
		docID = -1;	
	}

	public Task(String id, String color, GregorianCalendar startDT, GregorianCalendar endDT, String docName) {
		startDateTime = startDT;
		endDateTime = endDT;
		this.name = docName;
		this.day = "Day";
		this.color = color;
		reserID = id;
		docID = Integer.parseInt(docName.substring(7));	
	}

	public String getDayNum() {
		return day;
	}

	public String getStrColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public int getDocID() {
		return docID;
	}

	public String getReservationID() {
		return reserID;
	}

	public GregorianCalendar getStartDatetime() {
		return startDateTime;
	}

	public GregorianCalendar getEndDatetime() {
		return endDateTime;
	}

	public String getStrDate() {
		return "" + getMonth() + "/"  + getDay() + "/" + getYear();
	}

	public int getMonth() {
		return startDateTime.get(GregorianCalendar.MONTH);
	}

	public int getDay() {
		return startDateTime.get(GregorianCalendar.DATE);
	}
	
	public int getDayOfWeek() {
		return startDateTime.get(GregorianCalendar.DAY_OF_WEEK);
	}

	public int getYear() {
		return startDateTime.get(GregorianCalendar.YEAR);
	}

	public int getStartHour() {
		return startDateTime.get(GregorianCalendar.HOUR_OF_DAY);
	} 

	public int getStartMinute() {
		return startDateTime.get(GregorianCalendar.MINUTE);
	}

	public String getStrStartTime() {
		if (getStartMinute() == 0)
			return "" + getStartHour() + ":00";
		return "" + getStartHour() + ":" + getStartMinute();
	}

	public int getEndHour() {
		return endDateTime.get(GregorianCalendar.HOUR_OF_DAY);
	}

	public int getEndMinute() {
		return endDateTime.get(GregorianCalendar.MINUTE);
	}

	public String getStrEndTime() {
		if (getEndMinute() == 0)
			return "" + getEndHour() + ":00";
		return "" + getEndHour() + ":" + getEndMinute();
	}

	public boolean checkOverlap(Task cmpDT) {
		int baseMinStart = (getStartHour() * 60) + getStartMinute();
		int baseMinEnd = (getEndHour() * 60) + getEndMinute();
		int cmpMinStart = (cmpDT.getStartHour() * 60) + cmpDT.getStartMinute();
		int cmpMinEnd = (cmpDT.getEndHour() * 60) + cmpDT.getEndMinute();

		if ((cmpDT.getMonth() == getMonth()) &&
			(cmpDT.getDay() == getDay()) &&
			(cmpDT.getYear() == getYear()))
		{
			if (cmpMinStart == baseMinStart && cmpMinEnd == baseMinEnd)
				return true;
			else if (cmpMinStart >= baseMinStart && cmpMinStart < baseMinEnd)
				return true;
			else if (cmpMinEnd > baseMinStart && cmpMinEnd <= baseMinEnd)
				return true;
		}
		return false;
	}	

	@Override
	public int compareTo(Task t) {
		if((this.getStartHour() * 60) + this.getStartMinute() >
			(t.getStartHour() * 60) + t.getStartMinute())
			return 1;
		return -1;
	}

		public String toString() {
		return "Name = " + name + "\nStart Time = " + getMonth() + " " + getDay() + 
		       ", " + getYear() + " " + getStrStartTime() + "\nEnd Time = " + 
		       getStrEndTime() + "\nDay = " + getDayNum();
	}

	private String name, day, reserID;
	private String color;
	private int docID;
	private GregorianCalendar startDateTime;
	private GregorianCalendar endDateTime;
}