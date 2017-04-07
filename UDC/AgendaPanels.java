import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class AgendaPanels extends ViewPanels{
	public AgendaPanels(CalendarController cc, String name) {
		super.controller = cc;
		super.frameTitle = name;
	}
	protected void updatePanel() {
		Iterator allTasks = controller.getTasks(false, frameTitle);
		display(allTasks);
	}
	private void sorted() {
		/*Iterator allTasks = controller.getTasks(true);
		display(allTasks);*/
	}

	private void display(Iterator allTasks) {
		int j = 0;
		if (!allTasks.hasNext()) {
			modelViewTable.setValueAt("No appointments for today",0,1);
		} else {
			for (Iterator it = allTasks; it.hasNext();) {
				Task t = (Task)it.next();
				String tskName = "<html><font color='" + t.getStrColor() + "'";

				tskName += ">" + t.getName() + "</font></html>";
				String tskTime = t.getStrStartTime() + " - " + t.getStrEndTime();

				modelViewTable.setValueAt(tskTime, j, 0);
				modelViewTable.setValueAt(tskName, j, 1);
				j++;				
			}
		}
	}
	protected void additionalComponents() {
		btnExtra = new JButton("Book");
		newPanel.add(btnExtra);
		btnExtra.setBounds(345, 20, 205,40);
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