import java.awt.*;
import javax.swing.*;
public abstract class PanelFactory {
	public static JPanel determine(String btnName, CalendarController cc) {
		PanelFactory panel = null;
		switch(btnName) {
			case "CREATE":  panel = new AddPanel(cc);
			break;
			case "DAY": panel = new DayPanels(cc);
			break;
			case "AGENDA": panel = new AgendaPanels(cc);
			break;
			case "DAY(W)": panel = new WeekDayPanels(cc);
			break;
			case "AGENDA(W)": panel = new WeekAgendaPanels(cc);
		}
		return panel.makePanel();
	}
	protected abstract JPanel makePanel();
}