import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DoctorView extends CalendarView{
	public DoctorView() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
		initCalendarComponents();
		addCalendarComponents();
		setBoundsCalendarComponents();
		setCalendarTable();
		setCalendarPanel();

		initViewComponents();
		addViewComponents();
		setBoundsViewComponents();
		setViewPanel();

		initVChoiceComponents();
		addVChoiceComponents();
		setBoundsVChoiceComponents();
		setVChoicePanel();
	}

	protected void initVChoiceComponents() {
		cbFree = new JCheckBox("Free");
		cbReserved = new JCheckBox("Reserved");
		vChoicePanel = new JPanel(null);
		cbFree.setSelected(false);
		cbReserved.setSelected(false);
	}

	protected void addVChoiceComponents() {
		pane.add(vChoicePanel);
		vChoicePanel.add(cbFree);
		vChoicePanel.add(cbReserved);
	}

	protected void setBoundsVChoiceComponents() {
		vChoicePanel.setBounds(0,400,300,300);
		cbFree.setBounds(100, 70, 100, 40);
		cbReserved.setBounds(100, 140, 100, 40);
	}


	protected void additionalCalendarComp() {
		addApps = new JButton("ADD APPOINTMENTS");
		calendarPanel.add(addApps);
		addApps.setBounds(50, 340, 200, 40);
	}

	protected void addAdditionalListeners() {
		cbFree.addItemListener(controller.new viewChanged());
		cbReserved.addItemListener(controller.new viewChanged());
	}

	protected void initializeViewType() {
		viewType = new boolean[] {true, true, true, true, true};
		if(name.equals("Doctor 1")){
			viewType[3] = false;
			viewType[4] = false;	
		} else if(name.equals("Doctor 2")){
			viewType[2] = false;
			viewType[4] = false;	
		} else {
			viewType[2] = false;
			viewType[3] = false;	
		}
	}


    /**** Calendar Swing Components ****/
	private JButton addApps;

	/**** View Choice Components ****/
	private JCheckBox cbFree, cbReserved;
	private boolean[] viewType;
}