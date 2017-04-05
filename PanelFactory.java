import java.awt.*;
import javax.swing.*;
public abstract class PanelFactory {
	public static JPanel determine(String btnName, CalendarController cc) {
		PanelFactory panel = null;
		switch(btnName) {
			case "ADD":  panel = new AddPanel(cc);
			break;
			//case "DAY": panel = new DayPanels(cc);
			//break;
			//case "AGENDA": panel = new AgendaPanels(cc);
		}
		return panel.makePanel();
	}
	protected abstract JPanel makePanel();
}