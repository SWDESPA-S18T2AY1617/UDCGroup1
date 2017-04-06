import java.util.*;

public class CalendarModel {//extends Observer{
	public CalendarModel() {
		allTasks = store.read();
	}
	public Iterator getTasks(GregorianCalendar day, boolean[] viewType, boolean sort) {
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
		for(int i=0;i<allTasks.size() && noOverlap;i++) {
			if (allTasks.get(i).checkOverlap(nTask))
				noOverlap = false;
		}
		if (noOverlap) {
			allTasks.add(nTask);
			return "Successfully added!";
		}
		else
			return "Sorry! Todo or Event already there!";
		//setState();
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

	private ArrayList<Task> allTasks = new ArrayList<Task>();
	private CalendarController controller;
	private Storage store = new Storage();
}