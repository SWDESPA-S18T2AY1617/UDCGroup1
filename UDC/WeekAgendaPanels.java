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
				tskName += ">" + t.getName() + " - " + t.getReservationID() + " </font></html>";
				String tskTime = t.getStrStartTime() + " - " + t.getStrEndTime();

				modelViewTable.setValueAt(tskTime, j[i/2], i);
				modelViewTable.setValueAt(tskName, j[i/2], i + 1);
				j[i/2]++;
			}
		}
	}

	private void addBtnChange() {
		btnChange = new JButton("Change");
		newPanel.add(btnChange);
		btnChange.setBounds(50,20,100, 40);
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = eventTable.getSelectedRow(), col = eventTable.getSelectedColumn();  
				String content = "" + eventTable.getValueAt(row,col);
				String reserID = content.split("\\s")[4];
				controller.changePanel(e);
				controller.setReservationID(reserID);				
			}
		});
	}
	protected void additionalComponents() {
		if(frameTitle.contains("Doctor")) {
			btnAction.setText("Delete");
			addBtnChange();
		}
		else btnAction.setText("Book");
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