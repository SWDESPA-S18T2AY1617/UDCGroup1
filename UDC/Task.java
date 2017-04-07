import java.util.*;
import java.io.*;

public class Task implements Serializable, Comparable<Task>{
	public Task(GregorianCalendar startDT, GregorianCalendar endDT, String day, String docName) {
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

	public Task(GregorianCalendar startDT, GregorianCalendar endDT, String docName) {
		startDateTime = startDT;
		endDateTime = endDT;
		this.name = docName;
		this.day = "Day";
		docID = Integer.parseInt(docName.substring(7));	
	}

	public Type getType() {
		return type;
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

	public GregorianCalendar getStartDatetime() {
		return startDateTime;
	}

	public GregorianCalendar getEndDatetime() {
		return endDateTime;
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


	public boolean findEvent(GregorianCalendar date, boolean[] viewType) {
		boolean flag = false;
		if((date.get(GregorianCalendar.MONTH) == getMonth() &&
			date.get(GregorianCalendar.DATE) == getDay() &&
			date.get(GregorianCalendar.YEAR) == getYear()))
			
			for(boolean x: viewType){
				if(!x)
					flag = true;
			}
			if(flag == false){
				return true;
			}
			else if(viewType[0] == true){
				if(status == Status.FREE)
					return true;
			}
			else if(viewType[1] == true){
				if(status == Status.TAKEN)
					return true;
			}
			else if(viewType[2] == true){
				if(type == Type.DOCTOR1) //filter doctor 1's appointments
				return true;
			}
			else if(viewType[3] == true){
				if(type == Type.DOCTOR2) //filterv doctor 2's appointments
				return true;
			}
			else if(viewType[4] == true){
				if(type == Type.DOCTOR3) //filter doctor 3's appointments
				return true;
			}
		return false;
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

	private Type type;
	private Status status;
	private String name, day;
	private String color;
	private int docID;
	private GregorianCalendar startDateTime;
	private GregorianCalendar endDateTime;
	private boolean done;
}