import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ClientView extends CalendarView{
	public ClientView(String name){
		super();
		this.name = name;
		viewType = new boolean[] {true, false, true, true, true};
		this.frmMain.setTitle(name);

	}
	
	protected void initVChoiceComponents() {
		cbD1 = new JCheckBox("Doctor 1");
		cbD2 = new JCheckBox("Doctor 2");
		cbD3 = new JCheckBox("Doctor 3");
		vChoicePanel = new JPanel(null);
		cbD1.setSelected(true);
		cbD2.setSelected(true);
		cbD3.setSelected(true);
		//temp = new JPanel(null);
	}

	protected void addVChoiceComponents() {
		pane.add(vChoicePanel);
		//pane.add(temp);
		vChoicePanel.add(cbD1);
		vChoicePanel.add(cbD2);
		vChoicePanel.add(cbD3);
	}

	protected void setBoundsVChoiceComponents() {
		//temp.setBounds(300,100,595,795);
		vChoicePanel.setBounds(0,400,300,300);
		cbD1.setBounds(100, 60, 100, 40);
		cbD2.setBounds(100, 110, 100, 40);
		cbD3.setBounds(100, 160, 100, 40);
	}

	protected void additionalCalendarComp() {
		btnReservation = new JButton("VIEW RESERVATIONS");
		calendarPanel.add(btnReservation);
		btnReservation.setBounds(50, 325, 200, 40);
	}

	protected void addAdditionalListeners() {
		//view reservation, (taken, free)
		btnReservation.addActionListener(controller.new btnView_Action());
	}



    /**** Calendar Swing Components ****/
	private JButton btnReservation;

	/**** View Choice Components ****/
	private JCheckBox cbD1, cbD2, cbD3;
	
}