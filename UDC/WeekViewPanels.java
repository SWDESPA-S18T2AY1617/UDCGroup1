import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public abstract class WeekViewPanels extends PanelFactory implements Update{
	protected JPanel makePanel() {
		initComponents();
		addComponents();
		additionalComponents();
		setPanel();
		setBounds();
		setListeners();
		updatePanel();
		return newPanel;
	}

	private void initComponents() {
		btnAction = new JButton();
		newPanel = new JPanel(null);
		modelViewTable = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        eventTable = new JTable(modelViewTable);
		scrollEvents = new JScrollPane(eventTable);
		//toDoLeft = new JLabel("Tasks left to do: "+controller.getToDo());
		setTable();
	}

	private void setTable() {
		eventTable.setRowHeight(40);
		//Monday
		modelViewTable.addColumn("Time");
		modelViewTable.addColumn("Monday");
		//Tuesday
		modelViewTable.addColumn("Time");
		modelViewTable.addColumn("Tuesday");
		//Wednesday
		modelViewTable.addColumn("Time");
		modelViewTable.addColumn("Wednesday");
		//Thursday
		modelViewTable.addColumn("Time");
		modelViewTable.addColumn("Thursday");
		//Friday
		modelViewTable.addColumn("Time");
		modelViewTable.addColumn("Friday");
		modelViewTable.setColumnCount(10);
		eventTable.getTableHeader().setResizingAllowed(false);
		eventTable.getTableHeader().setReorderingAllowed(false);
		eventTable.setColumnSelectionAllowed(true);
		eventTable.setRowSelectionAllowed(true);
		eventTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		eventTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		eventTable.getColumnModel().getColumn(1).setPreferredWidth(350);
		eventTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		eventTable.getColumnModel().getColumn(3).setPreferredWidth(350);
		eventTable.getColumnModel().getColumn(4).setPreferredWidth(120);
		eventTable.getColumnModel().getColumn(5).setPreferredWidth(350);
		eventTable.getColumnModel().getColumn(6).setPreferredWidth(120);
		eventTable.getColumnModel().getColumn(7).setPreferredWidth(350);
		eventTable.getColumnModel().getColumn(8).setPreferredWidth(120);
		eventTable.getColumnModel().getColumn(9).setPreferredWidth(350);
	}

	private void addComponents() {
		newPanel.add(scrollEvents);
		newPanel.add(btnAction);
	//	newPanel.add(toDoLeft);
	}

	private void setPanel() {
		newPanel.setBackground(Color.WHITE);
	}

	private void setBounds() {
		newPanel.setBounds(300,100,595,795);
		scrollEvents.setBounds(50,75, 500, 475);
		btnAction.setBounds(345, 20, 205,40);
	//	toDoLeft.setBounds(50, 20, 150, 40);
	}

	private void setListeners() {
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				book();
			}
		});
	}

	private void book() {
		int row = eventTable.getSelectedRow(), col = eventTable.getSelectedColumn();  
		String content = "" + eventTable.getValueAt(row,col);
		String reserID = content.split("\\s")[4];
		if(frameTitle.contains("Doctor"))
			controller.deleteAppointment(reserID);
		 else controller.bookAppointment(frameTitle, reserID);
	}

	protected abstract void additionalComponents();

	protected String frameTitle;
	protected JPanel newPanel;
	protected JScrollPane scrollEvents;
	protected JButton btnAction, btnChange;
	protected JLabel toDoLeft;
	protected JTable eventTable;
	protected DefaultTableModel modelViewTable;
	protected CalendarController controller;

}