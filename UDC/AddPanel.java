import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
public class AddPanel extends PanelFactory {
	public AddPanel(CalendarController cc, String name) {
		controller = cc;
		frameTitle = name;
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
		addSlotLbl = new JLabel("Add here");
		weekChkBox = new ArrayList<JCheckBox>();
		type = null;

		for(int i=0;i<24;i++) {
			hourStartList.addItem(String.valueOf(i));
			hourEndList.addItem(String.valueOf(i));
		}

		minStartList.addItem("00");
		minEndList.addItem("00");
		minStartList.addItem("30");
		minEndList.addItem("30");

		name = new JTextField("Name of Event/To-do");
		btnSave = new JButton("Save");
		btnDiscard = new JButton("Discard");
		to = new JLabel("to");
		group = new ButtonGroup();
		createPanel = new JPanel(null);

		for(int i=0;i<5;i++)
			weekChkBox.add(new JCheckBox(daysString[i]));
	}

	private void addComponents() {
		createPanel.add(name);
		createPanel.add(btnSave);
		createPanel.add(btnDiscard);
		createPanel.add(hourEndList);
		createPanel.add(hourStartList);
		createPanel.add(minEndList);
		createPanel.add(minStartList);
		createPanel.add(to);
		createPanel.add(addSlotLbl);
		for(int i=0;i<5;i++)
			createPanel.add(weekChkBox.get(i));
	}

	private void addListeners() {
		btnSave.addActionListener(new btnSave_Action());
		btnDiscard.addActionListener(new btnDiscard_Action());
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
		hourStartList.setBounds(40, 120, 45, 40);
		minStartList.setBounds(95, 120, 45, 40);
		to.setBounds(150, 120, 50, 40);
		addSlotLbl.setBounds(40, 50, 300, 50);
		hourEndList.setBounds(180, 120, 45, 40);
		minEndList.setBounds(235, 120, 45, 40);
		btnSave.setBounds(40, 240, 70, 40);
		btnDiscard.setBounds(120, 240, 100, 40);
		createPanel.setBounds(300,100,595,795);
		weekChkBox.get(0).setBounds(40, 180, 80, 40);
		weekChkBox.get(1).setBounds(130, 180, 80, 40);
		weekChkBox.get(2).setBounds(220, 180, 90, 40);
		weekChkBox.get(3).setBounds(320, 180, 80, 40);
		weekChkBox.get(4).setBounds(410, 180, 80, 40);
	}

	class btnDiscard_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			clearContents();
		}
	}


	class btnSave_Action implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			GregorianCalendar todayCheck = new GregorianCalendar();
			int startHour = Integer.parseInt(hourStartList.getSelectedItem().toString()), 
				startMin = Integer.parseInt(minStartList.getSelectedItem().toString()),
				endHour = Integer.parseInt(hourEndList.getSelectedItem().toString()),
				endMinute = Integer.parseInt(minEndList.getSelectedItem().toString());

			for(int i=0;i<5;i++) {
				Task tempApp = new Task(new GregorianCalendar(1970, 1, 1, startHour, startMin),
										new GregorianCalendar(1970, 1, 1, endHour, endMinute),
										AddPanel.this.frameTitle, daysString[i]);
				controller.addNewTask(tempApp, weekChkBox.get(i).isSelected(), daysString[i], AddPanel.this.frameTitle);
			}
		}
	}

	private JPanel wklyPanel;
	private ArrayList<JCheckBox> weekChkBox;
	private String[] daysString = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	private String frameTitle;

	private JPanel createPanel;
	private JTextField name;
	private JLabel to, addSlotLbl;
	private Type type;
	private ButtonGroup group;
	private JButton btnSave, btnDiscard;
	private JComboBox hourStartList, minStartList, hourEndList, minEndList;
	private CalendarController controller;
}