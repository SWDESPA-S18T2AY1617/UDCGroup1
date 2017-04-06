import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public abstract class ViewPanels extends PanelFactory {
	protected JPanel makePanel() {
		initComponents();
		addComponents();
		additionalComponents();
		setPanel();
		setBounds();
		setListeners();
		//updatePanel();
		return newPanel;
	}

	private void initComponents() {
		newPanel = new JPanel(null);
		modelViewTable = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        eventTable = new JTable(modelViewTable);
		scrollEvents = new JScrollPane(eventTable);
		setTable();
	}

	private void setTable() {
		eventTable.setRowHeight(40);
		modelViewTable.addColumn("Time");
		modelViewTable.addColumn("Event/Task");
		modelViewTable.setColumnCount(2);
		eventTable.getTableHeader().setResizingAllowed(false);
		eventTable.getTableHeader().setReorderingAllowed(false);
		eventTable.setColumnSelectionAllowed(true);
		eventTable.setRowSelectionAllowed(true);
		eventTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		eventTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		eventTable.getColumnModel().getColumn(1).setPreferredWidth(350);
	}

	private void addComponents() {
		newPanel.add(scrollEvents);
		//newPanel.add(toDoLeft);
	}

	private void setPanel() {
		newPanel.setBackground(Color.WHITE);
	}

	private void setBounds() {
		newPanel.setBounds(300,100,595,795);
		scrollEvents.setBounds(50,75, 500, 475);
		//toDoLeft.setBounds(50, 20, 150, 40);
	}

	private void setListeners() {
		/*eventTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				clickToDo();
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteDoneToDo();
			}
		});*/
	}

/*	private void clickToDo() {
		int dayCol = eventTable.getSelectedColumn();
		int dayRow = eventTable.getSelectedRow();
		try {
			String oldDay = (String)modelViewTable.getValueAt(dayRow, dayCol);
			Iterator dayIterator = controller.getTasks(false);
			if (dayIterator.hasNext()) {
				for (Iterator it=dayIterator; it.hasNext();) {
					Task t = (Task)it.next();
					String pangReplace = "<html><font color='" + t.getStrColor() + "'";
					String actualTask = oldDay.contains("text-decoration:line-through;") 
										? oldDay.replaceAll(pangReplace + " style='text-decoration:line-through;'>", "")
										: oldDay.replaceAll(pangReplace + ">", "");
					actualTask = actualTask.replaceAll("</font></html>", "");
					int j = 0;
					if (t.getName().equals(actualTask) && t.getType() == Type.TO_DO) {
						oldDay = oldDay.contains("text-decoration:line-through;")
								 ? oldDay.replaceAll(pangReplace.substring(7) + " style='text-decoration:line-through;'>", pangReplace.substring(7) + ">")
								 : oldDay.replaceAll(pangReplace.substring(7) + ">", pangReplace.substring(7) + " style='text-decoration:line-through;'>");
						oldDay = oldDay + "</font></html>";
						modelViewTable.setValueAt(oldDay, dayRow, dayCol);
						t.setDone(!t.getDone());
					}
				}
			}
			toDoLeft.setText("Tasks left to do: "+controller.getToDo());
		} catch(Exception e){}
	}

	private void deleteDoneToDo() {
		controller.deleteTD();
		toDoLeft.setText("Tasks left to do: "+controller.getToDo());
	}*/

	//protected abstract void updatePanel();
	protected abstract void additionalComponents();
	protected String frameTitle;
	protected JPanel newPanel;
	protected JScrollPane scrollEvents;
	protected JButton btnExtra;
	protected JLabel toDoLeft;
	protected JTable eventTable;
	protected DefaultTableModel modelViewTable;
	protected CalendarController controller;

}