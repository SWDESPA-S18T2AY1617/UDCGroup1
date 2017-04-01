import java.awt.*;
import javax.swing.*;
public abstract class PanelFactory {
	public static JPanel determine(String btnName, CalendarController cc) {
		PanelFactory panel = null;
		switch(btnName) {
			case "CREATE":  panel = new AddPanel(cc);
			break;
			case "CALENDAR DAY": panel = new DayPanels(cc);
			break;
			case "AGENDA DAY": panel = new AgendaPanels(cc);
		}
		return panel.makePanel();
	}
	protected abstract JPanel makePanel();
}