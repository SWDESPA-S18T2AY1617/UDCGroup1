import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.reflect.Field;

public class CalendarController {//extends Observer {
	private CalendarModel model;
	private CalendarView view;
	private int monthToday, yearToday, yearBound;

	public CalendarController(CalendarView screen, CalendarModel shape) {
		GregorianCalendar cal = new GregorianCalendar();
		model = shape;
		view = screen;
		monthToday = cal.get(GregorianCalendar.MONTH);
		yearToday = cal.get(GregorianCalendar.YEAR);
		yearBound = yearToday;
	}

	public Iterator getTasks(boolean sort){
		GregorianCalendar cal = new GregorianCalendar(yearToday, 
				monthToday,Integer.parseInt(view.getDaylbl()));
		return model.getTasks(cal, view.getViewType(),sort);
	}

	/*public void deleteTD() {
		model.deleteTD();
	}

	public int getToDo() {
		return model.getToDoLeft();
	}*/

	public int getView() {
		return view.getViewType();
	}

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
		JButton b = (JButton)e.getSource();
		JPanel nPanel = PanelFactory.determine(b.getText(),view.getController());
		view.addPaneltoPane(nPanel);
	}

	class btnPrev_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if (monthToday == 0) {
				monthToday = 11;
				yearToday -= 1;
			} else {
				monthToday -= 1;
			}
			//model.setState();
			view.refreshCalendar(monthToday, yearToday);
		}
	}

	class btnNext_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if (monthToday == 11) {
				monthToday = 0;
				yearToday += 1;
			} else {
				monthToday += 1;
			}
			//model.setState();
			view.refreshCalendar(monthToday, yearToday);
		}
	}
	class cmbYear_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if (view.getCmbYr() != null) {
				String currYear = view.getCmbYr().toString();
				yearToday = Integer.parseInt(currYear);
				//model.setState();
				view.refreshCalendar(monthToday, yearToday);
			}
		}
	}

	class btnToday_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			String dteToday = view.getDateToday();
			view.setDate(dteToday);
			view.setDayLbl();
			view.refreshCalendar(monthToday, yearToday);
		}
	}

	class btnView_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			changePanel(e);
		}
	}

	class viewChanged implements ItemListener {
		 public void itemStateChanged(ItemEvent e){
		 	Object source = e.getItem();
		 	String cbName = ((JCheckBox)source).getText();
		 	int equiv = 0;
		 	if(cbName.equals("Event"))
		 		equiv = 1;
		 	else equiv = 2;
		 	if (e.getStateChange() == ItemEvent.DESELECTED) {
		 		view.setViewType(view.getViewType()-equiv);
		 	} else view.setViewType(view.getViewType()+equiv);
		 }
	}

	public void addNewTask(Task tempoTask, boolean wkCheck, String dayName) 
	{
		String day = view.getDaylbl(),
			   month = view.getMonthlbl(), 
			   year = view.getCmbYr().toString();
		String[] daysString = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		int equivMthNum = 0;
		int startTotalMinutes = tempoTask.getStartHour() * 60 + tempoTask.getStartMinute();
		int endTotalMinutes = tempoTask.getEndHour() * 60 + tempoTask.getEndMinute();

		// Get integer equivalent of day based on Calendar
		GregorianCalendar selectedDateCheck = new GregorianCalendar(Integer.parseInt(year), equivMthNum, Integer.parseInt(day));
		int dayNumIdeal = myIndexOf(daysString, dayName); // Based from submitted
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
			view.setStatus(model.addTask(newTask));
		else
			view.setStatus("Sorry invalid time or day passed.");
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
