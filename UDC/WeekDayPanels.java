import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class WeekDayPanels extends WeekViewPanels{
	public WeekDayPanels(CalendarController cc, String name) {
		super.controller = cc;
		super.frameTitle = name;
	}
	public void updatePanel() {
		Iterator events = controller.getWeekTasks(false, frameTitle);
		if(!events.hasNext()) {
			
			modelViewTable.setValueAt("No appointments for this week",0,1);
		} else {
			for (Iterator it = events; it.hasNext();) {
				Task t = (Task)it.next();
				int i = (t.getDayOfWeek() - 2) * 2 + 1;
				int j = 0;
				String eventName = "<html><font color='" + t.getStrColor() + "'";
				eventName += ">" + t.getName() + " - " + t.getReservationID() + " </font></html>";
				if(t.getStartMinute() == 30)
					j++;

				int k = ((t.getEndHour() - t.getStartHour()) * 2) + (t.getEndMinute() / 30);

				for(int l = j; l < k; l++)
					modelViewTable.setValueAt(eventName,l + (t.getStartHour()*2),i);
			}
		}
	}

	private void addBtnChange() {
		btnChange = new JButton("Change");
		newPanel.add(btnChange);
		btnChange.setBounds(50,20,100, 40);
		btnChange.addActionListener(controller.new btnView_Action());
	}

	protected void additionalComponents() {
		if(frameTitle.contains("Doctor")) {
			btnAction.setText("Delete");
			addBtnChange();
		}
		else btnAction.setText("Book");
		
		modelViewTable.setRowCount(48);
		for(int k=0;k<=8;k+=2) {
			int j = 0;
			for(int i = 0; i < 48; i++){
				String time = Integer.toString(j);
				if(j < 10)
					time = "0" + time;
				if(i % 2 == 0){
					modelViewTable.setValueAt(time,i,k);
					j++;
				}
			}	
		}
		
	}
}