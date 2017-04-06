import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Client {
	private int id;
	private String name;
	ArrayList<GregorianCalendar> reservations, freeDates;
														
	public Client(int i, String n){
		setId(i);
		setName(n);
		reservations = new ArrayList<GregorianCalendar>();
		freeDates = new ArrayList<GregorianCalendar>();
	}
	
	public void addReservation(GregorianCalendar date){
		reservations.add(date);
	}
	
	public void cancelReservation(GregorianCalendar date) {
		if (reservations.contains(date)) {
			reservations.remove(date);
		}
		freeDates.add(date);
	}
	
	public void updateFreeDates() {
		
	}

	public int getId() { return id; }
	public String getName() { return name; }
	public ArrayList<GregorianCalendar> getReservations() { return reservations; }
	public ArrayList<GregorianCalendar> getFreeDates() { return freeDates; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setReservations(ArrayList<GregorianCalendar> reservations) { this.reservations = reservations; }
	public void setFreeDates(ArrayList<GregorianCalendar> freeDates) { this.freeDates = freeDates; }	
}
