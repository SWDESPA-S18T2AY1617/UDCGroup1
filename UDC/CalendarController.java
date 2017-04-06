import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.reflect.Field;

public class CalendarController {//extends Observer {
	private CalendarModel model;
	private ArrayList<CalendarView> view;
	private int monthToday, yearToday, yearBound;

	public CalendarController(CalendarModel shape) {
		GregorianCalendar cal = new GregorianCalendar();
		model = shape;
		view = new ArrayList<CalendarView>();
		monthToday = cal.get(GregorianCalendar.MONTH);
		yearToday = cal.get(GregorianCalendar.YEAR);
		yearBound = yearToday;
	}

	public void attachView(CalendarView calView) {
		view.add(calView);
		System.out.println("View added" + view.size());
	}

	public Iterator getTasks(boolean sort, String frameName){
		int index = getNumView(frameName);
		GregorianCalendar cal = new GregorianCalendar(yearToday, 
				monthToday,Integer.parseInt(view.get(index).getDaylbl()));
		return model.getTasks(cal, view.get(index).getViewType(),sort, frameName);
	}

	public Iterator getWeekTasks(boolean sort, String frameName){
		int index = getNumView(frameName);
		GregorianCalendar temp = new GregorianCalendar(yearToday,
				monthToday,Integer.parseInt(view.get(index).getDaylbl()));
		GregorianCalendar cal = new GregorianCalendar(yearToday,
				monthToday,temp.getFirstDayOfWeek());
		GregorianCalendar end = model.getWeekEnd(cal);
		return model.getTasks(cal, end, view.get(index).getViewType(), sort);
	}
	
/*	public void deleteTD() {
		model.deleteTD();
	}

	public int getToDo() {
		return model.getToDoLeft();
	}

/*	public boolean[] getView() {
		return view.getViewType();
	}*/

	public int getYear() {
		return yearToday;
	}

	public int getMonth() {
		return monthToday;
	}

	public int getYearBound() {
		return yearBound;
	}

	public void changePanel(ActionEvent e) {
		Component c = (Component)e.getSource();
		JFrame frame = (JFrame) SwingUtilities.getRoot(c);
		String frameTitle = frame.getTitle();
		JPanel nPanel = PanelFactory.determine(((JButton)c).getText(),view.get(getNumView(frameTitle)).getController(), frameTitle);
		view.get(getNumView(frameTitle)).addPaneltoPane(nPanel);
	}

	class btnPrev_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			Component c = (Component)e.getSource();
			JFrame frame = (JFrame) SwingUtilities.getRoot(c);
			String frameTitle = frame.getTitle();
			int index = CalendarController.this.getNumView(frameTitle);
			if (monthToday == 0) {
				monthToday = 11;
				yearToday -= 1;
			} else {
				monthToday -= 1;
			}
			//model.setState();
			view.get(index).refreshCalendar(monthToday, yearToday);
		}
	}

	class btnNext_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			Component c = (Component)e.getSource();
			JFrame frame = (JFrame) SwingUtilities.getRoot(c);
			String frameTitle = frame.getTitle();
			int index = CalendarController.this.getNumView(frameTitle);
			if (monthToday == 11) {
				monthToday = 0;
				yearToday += 1;
			} else {
				monthToday += 1;
			}
			//model.setState();
			view.get(index).refreshCalendar(monthToday, yearToday);
		}
	}
	class cmbYear_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			Component c = (Component)e.getSource();
			JFrame frame = (JFrame) SwingUtilities.getRoot(c);
			String frameTitle = frame.getTitle();
			int index = CalendarController.this.getNumView(frameTitle);
			if (view.get(index).getCmbYr() != null) {
				String currYear = view.get(index).getCmbYr().toString();
				yearToday = Integer.parseInt(currYear);
				//model.setState();
				view.get(index).refreshCalendar(monthToday, yearToday);
			}
		}
	}

	class btnToday_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			Component c = (Component)e.getSource();
			JFrame frame = (JFrame) SwingUtilities.getRoot(c);
			String frameTitle = frame.getTitle();
			int index = CalendarController.this.getNumView(frameTitle);
			String dteToday = view.get(index).getDateToday();
			view.get(index).setDate(dteToday);
			view.get(index).setDayLbl();
			view.get(index).refreshCalendar(monthToday, yearToday);
		}
	}

	class btnView_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			changePanel(e);
		}
	}

	class viewChanged implements ItemListener {
		 	public void itemStateChanged(ItemEvent e){
		 	Component source = (Component)e.getItem();
		 	String cbName = ((JCheckBox)source).getText();

		 	JFrame frame = (JFrame) SwingUtilities.getRoot(source);
			String frameTitle = frame.getTitle();
			int indexView = CalendarController.this.getNumView(frameTitle);

		 	int index = 0;
		 	if(cbName.equals("Free"))
		 		index = 0;
		 	else if(cbName.equals("Reserved"))
		 		index = 1;
		 	else if(cbName.equals("Doctor 1"))
		 		index = 2;
		 	else if(cbName.equals("Doctor 2"))
		 		index = 3;
		 	else if(cbName.equals("Doctor 3"))
		 		index = 4;
		 	if (e.getStateChange() == ItemEvent.DESELECTED) {
		 		view.get(indexView).setViewType(index, false);
		 	} else view.get(indexView).setViewType(index, true);
		 }
	}

	public void addNewTask(Task tempoTask, boolean wkCheck, String dayName, String frameTitle) 
	{
		int index = getNumView(frameTitle);
		String day = view.get(index).getDaylbl(),
			   month = view.get(index).getMonthlbl(), 
			   year = view.get(index).getCmbYr().toString();
		String[] daysString = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		int equivMthNum = 0;
		int startTotalMinutes = tempoTask.getStartHour() * 60 + tempoTask.getStartMinute();
		int endTotalMinutes = tempoTask.getEndHour() * 60 + tempoTask.getEndMinute();

		// Get integer equivalent of day based on Calendar
		GregorianCalendar selectedDateCheck = new GregorianCalendar(Integer.parseInt(year), equivMthNum, Integer.parseInt(day));
		int dayNumIdeal = myIndexOf(daysString, dayName) + 1; // Based from submitted
		int dayNumReal = selectedDateCheck.get(GregorianCalendar.DAY_OF_WEEK) - 2; // Based from selected date

		// Get string equivalent of month
		for(Months m: Months.values()) {
			if(m.toString().equals(month))
				equivMthNum = m.toInt();
		}

		GregorianCalendar testStartDate = new GregorianCalendar(Integer.parseInt(year),equivMthNum,
								Integer.parseInt(day), tempoTask.getStartHour(), tempoTask.getStartMinute());
		testStartDate.add(GregorianCalendar.DATE, dayNumIdeal - dayNumReal);
		GregorianCalendar testEndDate = new GregorianCalendar(Integer.parseInt(year),equivMthNum,
								Integer.parseInt(day), tempoTask.getEndHour(), tempoTask.getEndMinute());
		testEndDate.add(GregorianCalendar.DATE, dayNumIdeal - dayNumReal);
		Task newTask = new Task(testStartDate, testEndDate, tempoTask.getName(), dayName);

		if (endTotalMinutes > startTotalMinutes && 
			dayNumIdeal >= dayNumReal && wkCheck)
			view.get(index).setStatus(model.addTask(newTask));
		else {
			view.get(index).setStatus("Sorry invalid time or day passed; ");
		}
	}

		private int getNumView(String frameName) {
		int num = 0;
		switch(frameName) {
			case "Doctor 1": num =  0; break;
			case "Doctor 2": num =  1; break;
			case "Doctor 3": num =  2; break;
			case "Secretary": num = 3; break;
			case "Client 1": num =  4; break;
			case "Client 2": num =  5; break;
		}
		return num;
	}

	public int myIndexOf(String[] arr, String cmp) {
		for(int i=0;i<arr.length;i++) {
			if (cmp == arr[i])
				return i;
		}
		return -1;
	}

	public void save(){
		model.saveEvents();
	}

}
