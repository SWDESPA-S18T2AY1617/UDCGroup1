import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class WeekDayPanels extends WeekViewPanels {
	public WeekDayPanels(CalendarController cc) {
		super.controller = cc;
	}
	protected void updatePanel() {
		Iterator events = controller.getWeekTasks(false);
		if(!events.hasNext()) {
			//to be revised
		} else {
			for (Iterator it = events; it.hasNext();) {
				Task t = (Task)it.next();
				int i = t.getDayOfWeek() * 2 + 1;
				int j = 0;
				String eventName = "<html><font color='" + t.getStrColor() + "'";
				if (t.getDone() && t.getType() == Type.TO_DO)
					eventName += " style='text-decoration:line-through;'";
				eventName += ">" + t.getName() + "</font></html>";
				if(t.getStartMinute() == 30)
					j++;

				int k = ((t.getEndHour() - t.getStartHour()) * 2) + (t.getEndMinute() / 30);

				for(int l = j; l < k; l++)
					modelViewTable.setValueAt(eventName,l + (t.getStartHour()*2),i);
			}
		}
	}

	protected void additionalComponents() {
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