import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Doctor {
	private int id;
	private String name;
	ArrayList<GregorianCalendar> freeDates, occupiedDates; //freeDates - dates that clients can set appointments 
														   //occupiedDates - dates that have clients already
	
	public Doctor(int i, String n){
		setId(i);
		setName(n);
		freeDates = new ArrayList<GregorianCalendar>();
		occupiedDates = new ArrayList<GregorianCalendar>();
	}
	
	public void setAppointment(GregorianCalendar availableDate, boolean isDaily, ArrayList<Integer> days){ 
		if(isDaily) {
			//int ctr;
			GregorianCalendar temp = availableDate;
			freeDates.add(availableDate);
			temp.set(Calendar.DAY_OF_WEEK, temp.getFirstDayOfWeek());
			
			for (int i=0; i<days.size(); i++) {
				temp.add(Calendar.DAY_OF_WEEK, days.get(i));
				freeDates.add(temp);
				temp.set(Calendar.DAY_OF_WEEK, temp.getFirstDayOfWeek());
			}
			
//			ctr = 6 - availableDate.get(Calendar.DAY_OF_WEEK);
//			while(ctr != 0){
//				temp.add(Calendar.DAY_OF_WEEK, 1);
//				freeDates.add(temp);
//				ctr--;
//			}
		}
		else
			freeDates.add(availableDate);
	}
	
	public void updateAppointment(GregorianCalendar fromDate, GregorianCalendar toDate){
		for (int i=0; i<freeDates.size(); i++) {
			if (fromDate.equals(freeDates.get(i))) {
				freeDates.remove(i);
				freeDates.add(toDate);
			}
		}
		
	}
	
	public void freeAppointment(GregorianCalendar checkDate){
		for (int i=0; i<freeDates.size(); i++) {
			if (checkDate.equals(freeDates.get(i))) {
				freeDates.remove(i);
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<GregorianCalendar> getFreeDates() {
		return freeDates;
	}
	
	public ArrayList<GregorianCalendar> getOccupiedDates() {
		return occupiedDates;
	}
}
