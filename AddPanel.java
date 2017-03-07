import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
public class AddPanel extends PanelFactory {
	public AddPanel(CalendarController cc) {
		controller = cc;
	}
	protected JPanel makePanel() {
		initComponents();
		addComponents();
		setPanel();
		setBoundsCP();
		addListeners();
		return createPanel;
	}

	private void initComponents() {
		hourStartList = new JComboBox();
		hourEndList = new JComboBox();
		minStartList = new JComboBox();
		minEndList = new JComboBox();
		type = null;

		for(int i=0;i<24;i++) {
			hourStartList.addItem(String.valueOf(i));
			hourEndList.addItem(String.valueOf(i));
		}

		minStartList.addItem("00");
		minEndList.addItem("00");
		minStartList.addItem("30");
		minEndList.addItem("30");

		name = new JTextField("Name of Event/Task");
		rbEvent = new JRadioButton("Event");
		rbTodo = new JRadioButton("Task");
		btnSave = new JButton("Save");
		btnDiscard = new JButton("Discard");
		to = new JLabel("to");
		group = new ButtonGroup();
		createPanel = new JPanel(null);
	}

	private void addComponents() {
		createPanel.add(name);
		createPanel.add(rbEvent);
		createPanel.add(rbTodo);
		createPanel.add(btnSave);
		createPanel.add(btnDiscard);
		createPanel.add(hourEndList);
		createPanel.add(hourStartList);
		createPanel.add(minEndList);
		createPanel.add(minStartList);
		createPanel.add(to);
		group.add(rbEvent);
		group.add(rbTodo);
	}

	private void addListeners() {
		btnSave.addActionListener(new btnSave_Action());
		btnDiscard.addActionListener(new btnDiscard_Action());
		rbTodo.addActionListener(new rbTodo_Action());
		rbEvent.addActionListener(new rbEvent_Action());
	}

	public void clearContents() {
		name.setText("Name of Event/Task");
		hourStartList.setSelectedIndex(0);
		hourEndList.setSelectedIndex(0);
		minStartList.setSelectedIndex(0);
		minEndList.setSelectedIndex(0);
	}

	private void setPanel() {
		createPanel.setBackground(Color.LIGHT_GRAY);
	}

	private void setBoundsCP() {
		hourStartList.setBounds(40, 180, 50, 40);
		minStartList.setBounds(100, 180, 50, 40);
		to.setBounds(160, 180, 50, 40);
		hourEndList.setBounds(220, 180, 50, 40);
		minEndList.setBounds(280, 180, 50, 40);
		name.setBounds(40, 50, 300, 50);
		rbEvent.setBounds(40, 120, 100, 40);
		rbTodo.setBounds(180, 120, 100, 40);
		btnSave.setBounds(40, 240, 70, 40);
		btnDiscard.setBounds(120, 240, 100, 40);
		createPanel.setBounds(300,100,595,795);
	}

	class btnDiscard_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			clearContents();
		}
	}

	class rbTodo_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			hourEndList.setEnabled(false);
			minEndList.setEnabled(false);
			type = Type.TO_DO;
		}
	}

	class rbEvent_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			hourEndList.setEnabled(true);
			minEndList.setEnabled(true);
			type = Type.EVENT;
		}
	}

	class btnSave_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if (type != null) {
				String endMinute, endHour;
				if (type == Type.TO_DO) {
					if (minStartList.getSelectedItem().toString() == "00") {
						endMinute = "30";
						endHour = hourEndList.getSelectedItem().toString();
					}
					else {
						endMinute = "00";
						endHour = "" + (Integer.parseInt(hourEndList.getSelectedItem().toString()) + 1);
					}
				}
				else
					endMinute = minEndList.getSelectedItem().toString();
					endHour = hourEndList.getSelectedItem().toString();

				controller.addNewTask(name.getText(), hourStartList.getSelectedItem().toString(),
										minStartList.getSelectedItem().toString(), 
										endHour,
										endMinute,type);
			}
		}
	}

	private JPanel createPanel;
	private JTextField name;
	private JLabel to;
	private Type type;
	private ButtonGroup group;
	private JButton btnSave, btnDiscard;
	private JRadioButton rbEvent, rbTodo;
	private JComboBox hourStartList, minStartList, hourEndList, minEndList;
	private CalendarController controller;
}