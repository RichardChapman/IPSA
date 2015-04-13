package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import db.searchQuery;

public class ManageEmployeesPanel extends JPanel{
	private static final long serialVersionUID = 2183497957771781217L;
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private ArrayList<String> lastNames = new ArrayList<String>();
	private ArrayList<String> firstNames = new ArrayList<String>();
	private ArrayList<String> depts = new ArrayList<String>();
	private ArrayList<String> positions = new ArrayList<String>();
	private ArrayList<String> dobs = new ArrayList<String>();
	private JTable employeeTable;
	private TableModel model;
	
	public ManageEmployeesPanel(){
		this.setBackground(Color.WHITE);
		setEmployeeData();
		display();
	}
	
	private void display(){
		this.setLayout(new BorderLayout());
		
		JLabel activityTitle = new JLabel("Manage Employees");
		activityTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		JPanel activityTitlePanel = new JPanel(new BorderLayout());
		activityTitlePanel.add(activityTitle, BorderLayout.NORTH);
		activityTitlePanel.setBackground(Color.WHITE);
		this.add(activityTitlePanel, BorderLayout.NORTH);

		
		/* Employee Table */
		String columnNames[] = {"Employee ID", "Last Name", "First Name", "Dept", "POS", "DOB"};
		String[][] dataValues = new String[ids.size()+1][columnNames.length];
		
		int i;
		
		for(i = 0; i < ids.size(); i++){
			dataValues[i][0] = ids.get(i).toString();
			dataValues[i][1] = lastNames.get(i);
			dataValues[i][2] = firstNames.get(i);
			dataValues[i][3] = depts.get(i);
			dataValues[i][4] = positions.get(i);
			dataValues[i][5] = dobs.get(i);
		}
		
		//final JTable employeeTable = new JTable(dataValues, columnNames);
		model = new DefaultTableModel(dataValues, columnNames){
			/**
			 * 
			 */
			private static final long serialVersionUID = 2568534900426461512L;

			public Class getColumnClass(int column){
				Class returnValue;
				if((column >= 0) && (column < getColumnCount())){
					returnValue = getValueAt(0, column).getClass();
				} else{
					returnValue = Object.class;
				}
				return returnValue;
			}
		};
		
		employeeTable = new JTable(model);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		employeeTable.setRowSorter(sorter);
		
		JScrollPane tableScrollPane = new JScrollPane(employeeTable);
		this.add(tableScrollPane, BorderLayout.CENTER);
		
		/* Filter/Search in Table */
		JPanel filterPanel = new JPanel(new FlowLayout());
		filterPanel.setBackground(Color.WHITE);
		JTextField searchField = new JTextField(10);
		
		JButton filterButton = new JButton("Filter");
		filterButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = searchField.getText();
				if (text.length() == 0){
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}
			
		});
		
		filterPanel.add(searchField);
		filterPanel.add(filterButton);
		activityTitlePanel.add(filterPanel, BorderLayout.EAST);
		
		/* South Panel */
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					int index = employeeTable.getSelectedRow();
					int selection = JOptionPane.showConfirmDialog(null, "Permanently delete " + firstNames.get(index) + ", " + lastNames.get(index) + " from the database?");
					int id = ids.get(index);
					if(selection == JOptionPane.YES_OPTION){
						searchQuery s = new searchQuery();
						if(s.addOrDelete("DELETE FROM employee WHERE id=" + id + ";") == 1){
							JOptionPane.showMessageDialog(null, "Employee has been removed from the database.");
							((AbstractTableModel)model).fireTableDataChanged();
						}
					}
			}
		});
		southPanel.add(deleteButton);
		
		JButton addNewButton = new JButton("Add New");
		addNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewEmployeeFrame a = new AddNewEmployeeFrame();
			}
			
		});
		southPanel.add(addNewButton);
		
		this.add(southPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	private void setEmployeeData(){
		Statement stmt = null;
		Connection con;			
		try {
			String query = "SELECT * FROM employee;";
			String username = "cis499";
			final String password = "password";
		    final String driverClass = "com.mysql.jdbc.Driver";
		    final String url = "jdbc:mysql://scraggley.cis.umassd.edu/" + username;
			
			con = DriverManager.getConnection(url, username, password);
	        // Create a Statement
	        Statement stmnt = con.createStatement();

	        // Execute a query
	        ResultSet rs = stmnt.executeQuery(query);
			try{
				while(rs.next()){
					System.out.println("Adding" + rs.getInt("id"));
					ids.add(rs.getInt("id"));
					System.out.println("Adding" + rs.getString("LastName"));
					lastNames.add(rs.getString("LastName"));
					System.out.println("Adding" + rs.getString("FirstName"));
					firstNames.add(rs.getString("FirstName"));
					
					System.out.println("Adding" + rs.getString("dept"));
					depts.add(rs.getString("dept"));
					System.out.println("Adding" + rs.getString("position"));
					positions.add(rs.getString("position"));
					System.out.println("Adding" + rs.getString("dob"));
					dobs.add(rs.getString("dob"));
				}
			} catch (SQLException e){
				
			} finally {
				if(stmt != null){
					stmt.close();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
