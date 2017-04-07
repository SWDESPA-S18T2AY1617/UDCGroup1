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

				eventName += ">" + t.getName() + "</font></html>";
				if(t.getStartMinute() == 30)
					j++;

				int k = ((t.getEndHour() - t.getStartHour()) * 2) + (t.getEndMinute() / 30);

				for(int l = j; l < k; l++)
					modelViewTable.setValueAt(eventName,l + (t.getStartHour()*2),1);
			}
		}

	}
	protected void additionalComponents() {
		btnExtra = new JButton("Book");
		newPanel.add(btnExtra);
		btnExtra.setBounds(345, 20, 205,40);

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
}