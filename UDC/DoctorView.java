import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DoctorView extends CalendarView{
	public DoctorView(String name) {
		super();
		this.name = name;
		this.frmMain.setTitle(name);


		this.viewType = new boolean[] {true, true, true, true, true};
		if(name.equals("Doctor 1")){
			this.viewType[3] = false;
			this.viewType[4] = false;	
		} else if(name.equals("Doctor 2")){
			this.viewType[2] = false;
			this.viewType[4] = false;	
		} else {
			this.viewType[2] = false;
			this.viewType[3] = false;	
		}
	}

	protected void initVChoiceComponents() {
		cbFree = new JCheckBox("Free");
		cbReserved = new JCheckBox("Reserved");
		vChoicePanel = new JPanel(null);
		cbFree.setSelected(true);
		cbReserved.setSelected(true);
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
		addApps.setBounds(50, 325, 200, 40);
	}

	protected void addAdditionalListeners() {
		addApps.addActionListener(controller.new btnView_Action());
		//cbFree.addItemListener(controller.new viewChanged());
		//cbReserved.addItemListener(controller.new viewChanged());
	}


    /**** Calendar Swing Components ****/
	private JButton addApps;

	/**** View Choice Components ****/
	private JCheckBox cbFree, cbReserved;
}