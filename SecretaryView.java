import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SecretaryView extends CalendarView{
	protected void initVChoiceComponents() {
		cbFree = new JCheckBox("Free");
		cbTaken = new JCheckBox("Reserved");
		cbD1 = new JCheckBox("Doctor 1");
		cbD2 = new JCheckBox("Doctor 2");
		cbD3 = new JCheckBox("Doctor 3");
		vChoicePanel = new JPanel(null);
		cbFree.setSelected(true);
		cbTaken.setSelected(true);
		cbD1.setSelected(true);
		cbD2.setSelected(true);
		cbD3.setSelected(true);
		//temp = new JPanel(null);
	}

	protected void addVChoiceComponents() {
		pane.add(vChoicePanel);
		//pane.add(temp);
		vChoicePanel.add(cbFree);
		vChoicePanel.add(cbTaken);
		vChoicePanel.add(cbD1);
		vChoicePanel.add(cbD2);
		vChoicePanel.add(cbD3);
	}

	protected void setBoundsVChoiceComponents() {
		//temp.setBounds(300,100,595,795);
		vChoicePanel.setBounds(0,400,300,300);
		cbFree.setBounds(100, 35, 100, 40);
		cbTaken.setBounds(100, 85, 100, 40);
		cbD1.setBounds(100, 135, 100, 40);
		cbD2.setBounds(100, 185, 100, 40);
		cbD3.setBounds(100, 235, 100, 40);
	}

	protected void additionalCalendarComp() {
		btnReservation = new JButton("View Reservation");
		calendarPanel.add(btnReservation);
		btnReservation.setBounds(50, 325, 200, 40);
	}

	protected void addAdditionalListeners() {
		//view reservation, doctors
		cbFree.addItemListener(controller.new viewChanged());
		cbTaken.addItemListener(controller.new viewChanged());
	}

	protected void initializeViewType() {
		viewType = new boolean[] {true, true, true, true, true};
	}


    /**** Calendar Swing Components ****/
	private JButton btnReservation;

	/**** View Choice Components ****/
	private JCheckBox cbFree, cbTaken, cbD1, cbD2, cbD3;
	private boolean[] viewType;
}