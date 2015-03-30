package gui;

import dbquery.searchQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

class skill{
	public String skillName;
	public int skillID;
}

class selectedSkill{
	public String skillName;
	public int skillID;
	public int skillLevel;
	public int skillImportance;
}

class employee{
	public String firstName;
	public String lastName;
	public int id;
	public ArrayList<Integer> skillLevels = new ArrayList<Integer>();
	public ArrayList<Integer> years = new ArrayList<Integer>();
	public String dept;
	public String position;
	public int rating = 0;
}

public class EmployeeSelectionPanel extends JPanel{

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1109018949622346081L;


	public EmployeeSelectionPanel(){
		this.setBackground(Color.WHITE);
		display();
	}
	
	private void display(){
		this.setLayout(null);
		
		JLabel label = new JLabel("Employee Selection");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		label.setBounds(288, 6, 207, 34);
		this.add(label);
		
		this.setVisible(true);
		
	
	    JPanel panel = new JPanel();
	    JPanel headerPanel = new JPanel();
		GridLayout employeeLayout = new GridLayout(0,3);
	    int vertGap = 5;
	    int horzGap = 5;
        //Set up the horizontal gap value
        employeeLayout.setHgap(horzGap);
        //Set up the vertical gap value
        employeeLayout.setVgap(vertGap);
        
        panel.setLayout(employeeLayout);
        
	    String[] skillLevels = { "None", "Low", "Medium", "High" };
	    String[] skillImportance = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	    String[] columnNames = {"First Name", "Last Name", "Dept", "Position"};
 
	    //JComboBox levelList = new JComboBox(skillLevels);
	   // JComboBox importanceList = new JComboBox(skillImportance);

	    
	    panel.add(new Label("Skill Name"));
	    panel.add(new Label("Skill Level"));
	    panel.add(new Label("Importance"));
	    
	    headerPanel.setPreferredSize(new Dimension(500,50));
	    headerPanel.setBounds(50,50,550,100);
	    
	    
	    int totalSkills;   
	    totalSkills = getTotalSkills();
	    
	    printTables();
	    
	    JComboBox[] levelList;
	    levelList = new JComboBox[totalSkills];
	    for(int i = 0; i < totalSkills; i++)
	    {
	    	levelList[i] = new JComboBox(skillLevels);
	    }
	    JComboBox[] importanceList;
	    importanceList = new JComboBox[totalSkills];
	    for(int i = 0; i < totalSkills; i++)
	    {
	    	importanceList[i] = new JComboBox(skillImportance);
	    }
	    

	    	
    	skill[] getSkills = new skill[totalSkills];
    	for(int i = 0; i < totalSkills; i++)
    	{
    		getSkills[i] = new skill();
    	}
    	
    	getSkills = getSkills(totalSkills);
    	final skill[] skills = getSkills;
    	
    	
    	for(int i = 0; i<totalSkills; i++){
	    	panel.add(new Label(skills[i].skillName));
	    	panel.add(levelList[i]);
	    	panel.add(importanceList[i]);

    	}
    	
    	ArrayList<selectedSkill> selectedSkills = new ArrayList<selectedSkill>();

	    JScrollPane scrollPane = new JScrollPane(panel);
	    
	    scrollPane.setPreferredSize(new Dimension(200,200));
	    add(scrollPane, BorderLayout.CENTER);
	    scrollPane.setBounds(50,100,550,300);
	    
	    
	    JButton searchEmployee = new JButton("Search for Employee");
	    searchEmployee.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		searchEmployee.setBounds(600, 360, 150, 40);
		searchEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String skillName;
				int skillID;
				String skillLevel;
				int skillLvl;
				int skillImportance;
				selectedSkill skill = new selectedSkill();
				ArrayList<selectedSkill> selectedSkills = new ArrayList<selectedSkill>();
				for(int i = 0; i < skills.length; i++)
				{
					skillLevel = (String)levelList[i].getSelectedItem();
					if (!skillLevel.equals("None"))
					{
						if(skillLevel.equals("Low"))
						{
							skillLvl = 1;
						}
						else if(skillLevel.equals("Medium"))
						{
							skillLvl = 2;
						}
						else
						{
							skillLvl = 3;
						}
						skill.skillName = skills[i].skillName;
						skill.skillID = skills[i].skillID;
						skill.skillLevel = skillLvl;
						skill.skillImportance = Integer.parseInt((String)importanceList[i].getSelectedItem());
						selectedSkills.add(skill);
					}
				}
				
				
				selectedSkill[] selectedResults = selectedSkills.toArray(new selectedSkill[selectedSkills.size()]);
				System.out.println("Getting skills successful");
				
				
				ArrayList<employee> employeeList = new ArrayList<employee>();
				employeeList = getEmployees(selectedResults);
				System.out.println(employeeList.size());
				employee[] employees = rateEmployees(employeeList, selectedResults);
				
			
								
				scrollPane.setVisible(false);
				searchEmployee.setVisible(false);
				searchEmployee.setEnabled(false);
				//resetSearch.setVisible(false);
				//resetSearch.setVisible(false);
				
				JTable resultsTable = new JTable(getResultsTable(employees), columnNames);
				JScrollPane resultsPane = new JScrollPane(resultsTable);
				resultsPane.setPreferredSize(new Dimension(200,200));
			    add(resultsPane, BorderLayout.CENTER);
			    resultsPane.setBounds(50,100,550,300);
				
				
				
			}
		});
		this.add(searchEmployee);
		
		JButton resetSearch = new JButton("Reset Criteria");
	    resetSearch.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		resetSearch.setBounds(600, 300, 150, 40);
		resetSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < levelList.length; i++)
				{
					levelList[i].setSelectedIndex(0);
					importanceList[i].setSelectedIndex(0);
				}
			}
		});
		this.add(resetSearch);
	}
	
	
	
	
	//Gets the total amount of skills there are
	public static int getTotalSkills()
	{
		
		int totalSkills = 0;
		Connection con = null;

			
		try {
			String query = "SELECT COUNT(sid) FROM hard_skills_translator";
			String username = "cis499";
			final String password = "password";
		    final String driverClass = "com.mysql.jdbc.Driver";
		    final String url = "jdbc:mysql://scraggley.cis.umassd.edu/" + username;
			
			con = DriverManager.getConnection(url, username, password);
			
	        // Create a Statement
	        Statement stmnt = con.createStatement();

	        // Execute a query
	        ResultSet rs = stmnt.executeQuery(query);
					        
	        
			while (rs.next()){
				totalSkills = rs.getInt(1);
					
			}
			
			} catch (Exception ex) {
				System.out.println("Problem with getting skill totals");
				ex.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (Exception ex) {
				}
			}
		return totalSkills;
	}
	
	
	//Gets all skills and there IDs
	public static skill[] getSkills(int totalSkills)
	{
		
		Connection con = null;
		skill[] getSkills = new skill[totalSkills];
		for(int i = 0; i < totalSkills; i++)
		{
			getSkills[i] = new skill();
		}

			
		try {
			String query = "SELECT * FROM hard_skills_translator";
			String username = "cis499";
			final String password = "password";
		    final String driverClass = "com.mysql.jdbc.Driver";
		    final String url = "jdbc:mysql://scraggley.cis.umassd.edu/" + username;
			
			con = DriverManager.getConnection(url, username, password);
			
	        // Create a Statement
	        Statement stmnt = con.createStatement();

	        // Execute a query
	        ResultSet rs = stmnt.executeQuery(query);
					        
	        int i = 0;
			while (rs.next()){
				getSkills[i].skillName = rs.getString("name");
				getSkills[i].skillID = rs.getInt("sid");
				i++;
			}
			
			} catch (Exception ex) {
				System.out.println("Problem with retreiving skills data");
				ex.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (Exception ex) {
				}
			}
		return getSkills;
	}
	
	
	//Gets employees from the database that have at least 1 of the desired skills
	public static ArrayList<employee> getEmployees(selectedSkill[] selectedSkills)
	{
		
		ArrayList<employee> employees = new ArrayList<employee>();
		
		employee currentEmployee = new employee();
		Connection con = null;
		
		//Creates query for specific skills
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT hard_skills.eid, employee.lastname, employee.firstname, employee.dept, employee.position, ");
		int i;
		for(i = 0; i < selectedSkills.length - 1; i++)
		{
			queryBuilder.append("hard_skills." + selectedSkills[i].skillID + "n, hard_skills." + selectedSkills[i].skillID + "y, ");
		}
		queryBuilder.append("hard_skills." + selectedSkills[i].skillID + "n, hard_skills." + selectedSkills[i].skillID + "y ");
		queryBuilder.append("FROM hard_skills INNER JOIN employee ON employee.id=hard_skills.eid");
		
		

			
		try {
			String query = queryBuilder.toString();
			
			String username = "cis499";
			final String password = "password";
		    final String driverClass = "com.mysql.jdbc.Driver";
		    final String url = "jdbc:mysql://scraggley.cis.umassd.edu/" + username;
			
			con = DriverManager.getConnection(url, username, password);
			
	        // Create a Statement
	        Statement stmnt = con.createStatement();

	        // Execute a query
	        ResultSet rs = stmnt.executeQuery(query);
	        
	        boolean addEmployee = false;
	        int currentValue;
					        
			while (rs.next()){
				currentEmployee.id = rs.getInt("eid");
				System.out.println(currentEmployee.id);
				for(i = 0; i < selectedSkills.length; i++)
				{
					currentValue = rs.getInt(selectedSkills[i].skillID + "n");
					System.out.println(currentValue);
					if (currentValue > 0)
					{
						addEmployee = true;
					}
					currentEmployee.firstName = rs.getString("firstname");
					currentEmployee.lastName = rs.getString("lastname");
					currentEmployee.dept = rs.getString("dept");
					currentEmployee.position = rs.getString("position");
					currentEmployee.skillLevels.add(currentValue);
					currentEmployee.years.add(rs.getInt(selectedSkills[i].skillID + "y"));
					
				}
				if (addEmployee)
				{
					System.out.println("Employee added");
					employees.add(currentEmployee);
					addEmployee = false;
				}
				currentEmployee = new employee();
			}
			
			} catch (Exception ex) {
				System.out.println("Problem with retreiving skills data");
				ex.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (Exception ex) {
				}
			}
		return employees;
	}
	
	
	//Rates the employees
	public static employee[] rateEmployees(ArrayList<employee> getEmployees, selectedSkill[] selectedSkills)
	{
		float difference = 0;
		int expectedYears = 4;
		float skillRating;
		employee[] employees = getEmployees.toArray(new employee[getEmployees.size()]);
		for(int i = 0; i < employees.length; i++)
		{
			for(int j = 0; j < selectedSkills.length; j++)
			{
				skillRating = (employees[i].skillLevels.get(j) * 1000) * (1 + ((employees[i].years.get(j)-(expectedYears * employees[i].skillLevels.get(j)))/100));
				difference = skillRating - (selectedSkills[j].skillLevel * 1000);
				if (difference > 0)
				{
						difference = difference / (1- (selectedSkills[j].skillImportance/3));
				}
				else
				{
					difference = -1 * difference * (selectedSkills[j].skillImportance/3);
				}
				employees[i].rating += difference;
			}
		}
		
		sortByRating(employees, 0, employees.length);
		
		return employees;
	}
	
	public static void sortByRating(employee[] arr, int low, int high) {
		 
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		//pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle].rating;
 
		//make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i].rating < pivot) {
				i++;
			}
 
			while (arr[j].rating > pivot) {
				j--;
			}
 
			if (i <= j) {
				employee temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		//recursively sort two sub parts
		if (low < j)
			sortByRating(arr, low, j);
 
		if (high > i)
			sortByRating(arr, i, high);
	}
	
	public static selectedSkill[] getSelectedSkills(skill[] skills)
	{
		String skillName;
		int skillID;
		String skillLevel;
		int skillImportance;
		selectedSkill skill = new selectedSkill();
		ArrayList<selectedSkill> selectedSkills = new ArrayList<selectedSkill>();
		for(int i = 0; i < skills.length; i++)
		{
			//skillLevel = levelList[i].getSelectedItem();
			skill.skillName = skills[i].skillName;
			skill.skillID = skills[i].skillID;
		}
		
		
		selectedSkill[] selectedResults = selectedSkills.toArray(new selectedSkill[selectedSkills.size()]);
		return selectedResults;
		
	}
	
	public static Object[][] getResultsTable(employee[] employees)
	{
		Object[][] employeeTable = new Object[employees.length][4];
		
		for(int i = 0; i < employees.length; i++)
		{
				employeeTable[i][0] = employees[i].firstName;
				employeeTable[i][1] = employees[i].lastName;
				employeeTable[i][2] = employees[i].dept;
				employeeTable[i][3] = employees[i].position;
		}
		
		
		
		return employeeTable;
	}
	
	/*public static void printTables()
	{
		Connection con = null;

			
		try {
			String query = "SELECT * FROM employee";
			String username = "cis499";
			final String password = "password";
		    final String driverClass = "com.mysql.jdbc.Driver";
		    final String url = "jdbc:mysql://scraggley.cis.umassd.edu/" + username;
			
			con = DriverManager.getConnection(url, username, password);
			
	        // Create a Statement
	        Statement stmnt = con.createStatement();

	        // Execute a query
	        ResultSet rs = stmnt.executeQuery(query);
	        ResultSetMetaData rsmd = rs.getMetaData();
					        
	        printColTypes(rsmd);
	        
	        int numberOfColumns = rsmd.getColumnCount();
	        
	        for (int i = 1; i <= numberOfColumns; i++) {
	            if (i > 1) System.out.print(",  ");
	            String columnName = rsmd.getColumnName(i);
	            System.out.print(columnName);
	          }
	          System.out.println("");
	        
			while (rs.next()){
				for (int i = 1; i <= numberOfColumns; i++) {
			          if (i > 1) System.out.print(",  ");
			          String columnValue = rs.getString(i);
			          System.out.print(columnValue);
			        }
			        System.out.println(""); 					
			}
			
			} catch (Exception ex) {
				System.out.println("Problem with getting skill totals");
				ex.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (Exception ex) {
				}
			}
	}
	

	public static void printColTypes(ResultSetMetaData rsmd)
		                            throws SQLException {
				int columns = rsmd.getColumnCount();
				for (int i = 1; i <= columns; i++) {
				int jdbcType = rsmd.getColumnType(i);
				String name = rsmd.getColumnTypeName(i);
				System.out.print("Column " + i + " is JDBC type " + jdbcType);
				System.out.println(", which the DBMS calls " + name);
		    }
	}*/
	
	
}


