import java.awt.*;
import javax.swing.*;
public abstract class PanelFactory {
	public static JPanel determine(String btnName, CalendarController cc, String frameTitle) {
		PanelFactory panel = null;
		switch(btnName) {
			case "ADD APPOINTMENTS":  panel = new AddPanel(cc, frameTitle);
			break;
			case "CALENDAR": panel = new DayPanels(cc, frameTitle);
			break;
			case "AGENDA": panel = new AgendaPanels(cc, frameTitle);
			break;
			case "CALENDAR(W)": panel = new WeekDayPanels(cc, frameTitle);
			break;
			case "AGENDA(W)": panel = new WeekAgendaPanels(cc, frameTitle);
			break;
			case "VIEW RESERVATIONS":  panel = new ReservationPanel(cc, frameTitle);
			break;
			case "Change":  panel = new ChangePanel(cc, frameTitle);
			break;
		}
		return panel.makePanel();
	}
	protected abstract JPanel makePanel();
}