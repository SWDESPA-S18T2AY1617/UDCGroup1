import java.util.*;

public class CalendarModel {
	public CalendarModel() {
		allTasks = store.read();
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
		for(int i=0;i<allTasks.size() && noOverlap;i++) {
			if (allTasks.get(i).checkOverlap(nTask))
				noOverlap = false;
		}
		if (noOverlap) {
			allTasks.add(nTask);
			System.out.println(nTask.toString());
			return "Successfully added!";
		}
		else
			return "Sorry! Todo or Event already there!";
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

	private int id;
	private String name;
	private ArrayList<GregorianCalendar> freeDates, occupiedDates;
	private ArrayList<Task> allTasks = new ArrayList<Task>();
	private CalendarController controller;
	private Storage store = new Storage();
}