import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ClientView {
	public ClientView() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        initFrmMain();
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

	public void initFrmMain() {
		frmMain = new JFrame ("Client");
        frmMain.setSize(900, 740);
		pane = frmMain.getContentPane();
		pane.setLayout(null);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		frmMain.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				controller.save();
			}
		});
	}

	public void initVChoiceComponents() {
		btnViewReserve = new JButton("View Reservations");
		vChoicePanel = new JPanel(null);
	}

	public void addVChoiceComponents() {
		pane.add(vChoicePanel);
		vChoicePanel.add(btnViewReserve);
	}

	public void setVChoicePanel() {
		vChoicePanel.setBackground(Color.BLUE);
		vChoicePanel.setBorder(BorderFactory.createTitledBorder("View"));
	}

	public void setBoundsVChoiceComponents() {
		vChoicePanel.setBounds(0,450,300,250);
		btnViewReserve.setBounds(100, 100, 100, 40);
	}

	public String getDateToday() {
		Months[] months = Months.values();
		GregorianCalendar dateToday = new GregorianCalendar();
		String dteTdy = "" + months[dateToday.get(GregorianCalendar.MONTH)].toString() + " " + 
						dateToday.get(GregorianCalendar.DATE) +  ", " + dateToday.get(GregorianCalendar.YEAR);
		return dteTdy; 
	}

	public void initViewComponents() {
		dateLabel = new JLabel(getDateToday());
		btnToday = new JButton("TODAY");
		btnDay = new JButton("DAY");
		btnAgenda = new JButton("AGENDA");
		btnDayWkly = new JButton("DAY (WEEKLY)");
		btnAgendaWkly = new JButton("AGENDA (WEEKLY)");
		viewPanel = new JPanel(null);
	}

	public void addViewComponents() {
		pane.add(viewPanel);
		viewPanel.add(dateLabel);
		viewPanel.add(btnToday);
		viewPanel.add(btnDay);
		viewPanel.add(btnAgenda);
		viewPanel.add(btnDayWkly);
		viewPanel.add(btnAgendaWkly);
	}

	public void setViewPanel() {
		viewPanel.setBackground(Color.CYAN);
	}

	public void setBoundsViewComponents() {
		viewPanel.setBounds(300,0,595, 100);
		btnToday.setBounds(20, 30, 100, 40);
		btnAgenda.setBounds(435, 10, 125, 40);
		btnDay.setBounds(310, 10, 125, 40);
		btnAgendaWkly.setBounds(435, 50, 125, 40);
		btnDayWkly.setBounds(310, 50, 125, 40);
		dateLabel.setBounds(150, 30, 200, 40);
	}

	public void initCalendarComponents() {
		monthLabel = new JLabel ("January");
		dayLabel = new JLabel("");
		statusLabel = new JLabel("");
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<");
		btnNext = new JButton (">");
		addApps = new JButton("ADD");
		viewApps = new JButton("VIEW");
		modelCalendarTable = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
                
		calendarTable = new JTable(modelCalendarTable);   
		scrollCalendarTable = new JScrollPane(calendarTable);
		calendarPanel = new JPanel(null);
		
	}

	public void addCalendarComponents() {
		pane.add(calendarPanel);
		calendarPanel.add(monthLabel);
		calendarPanel.add(dayLabel);
		calendarPanel.add(statusLabel);
		calendarPanel.add(cmbYear);
		calendarPanel.add(btnPrev);
		calendarPanel.add(btnNext);
		calendarPanel.add(addApps);
		calendarPanel.add(viewApps);
		calendarPanel.add(scrollCalendarTable);
		String[] headers = {"S", "M", "T", "W", "T", "F", "S"}; //All headers
		for (int i=0; i<7; i++)
			modelCalendarTable.addColumn(headers[i]);
		setDayLbl();
	}

	public void setCalendarPanel() {
		calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
		calendarPanel.setBackground(Color.PINK);
	}

	public void setCalendarTable(){
		calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background
		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);
		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		calendarTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		calendarTable.setRowHeight(30);
		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);
	}
	public void setBoundsCalendarComponents() {
		calendarPanel.setBounds(0, 0, 300, 450);
        monthLabel.setBounds(20, 15, 200, 50);
        statusLabel.setBounds(20,380,300, 50);
		cmbYear.setBounds(20, 55, 80, 35);
		btnPrev.setBounds(180, 50, 45, 40);
		btnNext.setBounds(225, 50, 45, 40);
		addApps.setBounds(100, 340, 100, 40);
		viewApps.setBounds(100, 390, 100, 40);
		scrollCalendarTable.setBounds(20, 100, 250, 220);
	}

	public void setDate() {
		col = calendarTable.getSelectedColumn();  
        row = calendarTable.getSelectedRow();  
        dayLabel.setText(""+modelCalendarTable.getValueAt(row,col));
        dateLabel.setText(getMonthlbl() + " " + getDaylbl() + ", " + getCmbYr().toString());
	}

   	public void refreshCalendar(int month, int year) {
		Months[] months =  Months.values();
		int numOfDays, dayOfWeek, i, j;
			
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= controller.getYearBound()-10)
            btnPrev.setEnabled(false);
		if (month == 11 && year >= controller.getYearBound()+100)
            btnNext.setEnabled(false);
                
		monthLabel.setText(months[month].toString());
		cmbYear.setSelectedItem(""+year);
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		numOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		dayOfWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);

		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				modelCalendarTable.setValueAt(null, i, j);

		for (i = 1; i <= numOfDays; i++) {
			int rows = new Integer((i+dayOfWeek-2)/7);
			int column  =  (i+dayOfWeek-2)%7;
			modelCalendarTable.setValueAt(i, rows, column);
		}
		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
	}

	public void setController(CalendarController cc) {
		controller = cc;
		btnPrev.addActionListener(controller.new btnPrev_Action());
		btnNext.addActionListener(controller.new btnNext_Action());
		cmbYear.addActionListener(controller.new cmbYear_Action());
		btnToday.addActionListener(controller.new btnToday_Action());
		addApps.addActionListener(controller.new btnView_Action());
		viewApps.addActionListener(controller.new btnView_Action());
		btnDay.addActionListener(controller.new btnView_Action());
		btnAgenda.addActionListener(controller.new btnView_Action());
		btnDayWkly.addActionListener(controller.new btnView_Action());
		btnAgendaWkly.addActionListener(controller.new btnView_Action());
		btnViewReserve.addItemListener(controller.new checkReserve());
		
		for (int i = controller.getYear()-100; i <= controller.getYear()+100; i++)
			cmbYear.addItem(String.valueOf(i));

		calendarTable.addMouseListener(new MouseAdapter() {  
            public void mouseClicked(MouseEvent evt) {  
                setDate();
            }
        });

		refreshCalendar(controller.getMonth(), controller.getYearBound()); 
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int t) {
		viewType = t;
	}

	public void setDate(String date) {
		dateLabel.setText(date);
	}

	public void setDayLbl() {
		GregorianCalendar dateToday = new GregorianCalendar();
		dayLabel.setText(""+dateToday.get(GregorianCalendar.DATE));
	}

	public void setStatus(String status) {
		statusLabel.setText(status);
	}

	public Object getCmbYr() {
		return cmbYear.getSelectedItem();
	}

	public String getDaylbl() {
		return dayLabel.getText();
	}

	public String getMonthlbl() {
		return monthLabel.getText();
	}

	public CalendarController getController() {
		return controller;
	}

	public void addPaneltoPane(JPanel newpanel) {
		if(temp != null)
			pane.remove(temp);
		temp = newpanel;
		pane.add(temp);
		pane.repaint();
	}
	private CalendarController controller;

	/**** Day Components ****/
	private int col, row;

    /**** Calendar Swing Components ****/
    private JLabel monthLabel, dayLabel, statusLabel;
	private JButton btnPrev, btnNext, addApps, viewApps;
    private JComboBox cmbYear;
	private JFrame frmMain;
	private Container pane;
	private JScrollPane scrollCalendarTable;
	private JPanel calendarPanel;
    
	/**** View Swing Components ****/
	private JPanel viewPanel;
	private JLabel dateLabel;
	private JButton btnToday, btnDay, btnAgenda, btnDayWkly, btnAgendaWkly;

	/**** View Choice Components ****/
	private JPanel vChoicePanel;
	private JButton btnViewReserve;
	private JPanel temp = null;
	private int viewType = 0;
	
    /**** Calendar Table Components ***/
	private JTable calendarTable;
    private DefaultTableModel modelCalendarTable;
}