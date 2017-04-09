import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class DayPanels extends ViewPanels {
	public DayPanels(CalendarController cc, String name) {
		super.controller = cc;
		super.frameTitle = name;
	}
	protected void updatePanel() {
		Iterator events = controller.getTasks(false, frameTitle);
		if(!events.hasNext()){
			modelViewTable.setValueAt("No appointments for today",0,1);
		} else {
			for (Iterator it = events; it.hasNext();) {
				Task t = (Task)it.next();
				int j = 0;
				String eventName = "<html><font color='" + t.getStrColor() + "'";

				eventName += ">" + t.getName() + " - " + t.getReservationID() +" </font></html>";
				if(t.getStartMinute() == 30)
					j++;

				int k = ((t.getEndHour() - t.getStartHour()) * 2) + (t.getEndMinute() / 30);

				for(int l = j; l < k; l++)
					modelViewTable.setValueAt(eventName,l + (t.getStartHour()*2),1);
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

		int j = 0;
		modelViewTable.setRowCount(48);
		for(int i = 0; i < 48; i++){
			String time = Integer.toString(j);
			if(j < 10)
				time = "0" + time;
			if(i % 2 == 0){
				modelViewTable.setValueAt(time,i,0);
				j++;
			}
		}	
	}


	protected void doAction() {
		int row = eventTable.getSelectedRow(), col = eventTable.getSelectedColumn();  
		String content = "" + eventTable.getValueAt(row,col);
		String reserID = content.split("\\s")[4];
		if(frameTitle.contains("Doctor"))
			controller.deleteAppointment(reserID);
		 else controller.bookAppointment(frameTitle, reserID);
	}
	private JButton btnChange;
}