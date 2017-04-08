import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class WeekAgendaPanels extends WeekViewPanels{
	public WeekAgendaPanels(CalendarController cc, String name) {
		super.controller = cc;
		super.frameTitle = name;
	}
	public void updatePanel() {
		Iterator allTasks = controller.getWeekTasks(false, frameTitle);
		display(allTasks);
	}
	private void sorted() {
		Iterator allTasks = controller.getWeekTasks(true, frameTitle);
		display(allTasks);
	}

	private void display(Iterator allTasks) {
		if (!allTasks.hasNext()) {
			modelViewTable.setValueAt("No appointments for this week",0,1);
		} else {
			int j[] = {0,0,0,0,0};
			int i = 0;
			//int day = 100000;
			for (Iterator it = allTasks; it.hasNext();) {
				Task t = (Task)it.next();
				i = (t.getDayOfWeek() - 2) * 2;
				String tskName = "<html><font color='" + t.getStrColor() + "'";
			/*	if (t.getDone() && t.getType() == Type.TO_DO)
					tskName += " style='text-decoration:line-through;'";*/
				tskName += ">" + t.getName() + "</font></html>";
				String tskTime = t.getStrStartTime() + " - " + t.getStrEndTime();

				modelViewTable.setValueAt(tskTime, j[i/2], i);
				modelViewTable.setValueAt(tskName, j[i/2], i + 1);
				j[i/2]++;
			}
		}
		/*
		int j = 0;
		if (!allTasks.hasNext()) {
			if(controller.getView() == 1)
				modelViewTable.setValueAt("No events for today",0,1);
			else if(controller.getView() == 2)
				modelViewTable.setValueAt("No tasks for today",0,1);
			else modelViewTable.setValueAt("No events/tasks for today",0,1);
			//modelViewTable.setValueAt("No appointments for today",0,1);
		} else {
			for (Iterator it = allTasks; it.hasNext();) {
				Task t = (Task)it.next();
				String tskName = "<html><font color='" + t.getStrColor() + "'";
				if (t.getDone() && t.getType() == Type.TO_DO)
					tskName += " style='text-decoration:line-through;'";
				tskName += ">" + t.getName() + "</font></html>";
				String tskTime = t.getStrStartTime() + " - " + t.getStrEndTime();

				modelViewTable.setValueAt(tskTime, j, 0);
				modelViewTable.setValueAt(tskName, j, 1);
				j++;				
			}
		}
		*/
	}
	protected void additionalComponents() {
		modelViewTable.setRowCount(20);
		eventTable.setShowGrid(false);
		btnSort = new JButton("Sort");
		newPanel.add(btnSort);
		btnSort.setBounds(250,20,70,40);
		btnSort.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				sorted();
			}
		});
	}

	private JButton btnSort;
}