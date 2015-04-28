package gui;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;


class skill{
	public String skillName;
	public int skillID;
}


//Holds selected skills and their importance
class selectedSkill{
	public String skillName;
	public int skillID;
	public int skillLevel;
	public int skillImportance;
}


//Holds all employee information
class employee{
	public String firstName;
	public String lastName;
	public int id;
	public ArrayList<Integer> skillLevels = new ArrayList<Integer>();
	public ArrayList<Integer> years = new ArrayList<Integer>();
	public String dept;
	public String position;
	public double rating = 0;
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
	
	
	JButton searchEmployee, resetSearch, searchAgain;
	JScrollPane resultsPane;
	JTable resultsTable;
	JTextPane reasonForSelection, experienceForSelection;
	ListSelectionModel listSelectionModel;
	JComboBox<String> algorithmSelection;
	
	final static double IMPORTANCE_LOW_MIN = 0, IMPORTANCE_LOW_MAX = 4;
	final static double IMPORTANCE_MED_MIN = 2, IMPORTANCE_MED_MAX = 8;
	final static double IMPORTANCE_HIGH_MIN = 6, IMPORTANCE_HIGH_MAX = 10;
	
	final static double EXPERIENCE_LOW_MIN = 0, EXPERIENCE_LOW_MAX = 10;
	final static double EXPERIENCE_MED_MIN = 5, EXPERIENCE_MED_MAX = 20;
	final static double EXPERIENCE_HIGH_MIN = 15, EXPERIENCE_HIGH_MAX = 25;
	
	
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
	    String[] skillImportance = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	    String[] columnNames = {"First Name", "Last Name", "Dept", "Position"};
	    String[] algorithms = {"Fuzzy Search", "Complex Fuzzy Search", "Dynamic search", "Basic search"};
 
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
	    
	    JComboBox<String>[] levelList;
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
    	

	    JScrollPane scrollPane = new JScrollPane(panel);
	    
	    scrollPane.setPreferredSize(new Dimension(200,200));
	    add(scrollPane, BorderLayout.CENTER);
	    scrollPane.setBounds(50,100,550,300);
	    
	    algorithmSelection = new JComboBox<String>(algorithms);
	    algorithmSelection.setBounds(600, 260, 150, 40);
	    ((JLabel)algorithmSelection.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	    this.add(algorithmSelection);
	    
	    
	    searchEmployee = new JButton("Search for Employee");
	    searchEmployee.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		searchEmployee.setBounds(600, 310, 150, 40);
		searchEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String skillLevel;
				int skillLvl, selectedAlgorithm;
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
						selectedSkill skill = new selectedSkill();
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
				
				selectedAlgorithm = algorithmSelection.getSelectedIndex();
				System.out.println("The selected Algorithm is: "+ selectedAlgorithm);
				employee[] employees = rateEmployees(employeeList, selectedResults, selectedAlgorithm);
				
			
								
				scrollPane.setVisible(false);
				searchEmployee.setVisible(false);
				searchEmployee.setEnabled(false);
				resetSearch.setVisible(false);
				resetSearch.setEnabled(false);
				algorithmSelection.setVisible(false);
				algorithmSelection.setEnabled(false);
				searchAgain.setVisible(true);
				searchAgain.setEnabled(true);
				
				
				resultsTable = new JTable(getResultsTable(employees), columnNames);
				resultsTable.setCellSelectionEnabled(true);
				resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				    public void valueChanged(ListSelectionEvent e) {
				    	reasonForSelection.setText("");
				    	experienceForSelection.setText("");
				    	StyledDocument skillsDoc = reasonForSelection.getStyledDocument();
				    	StyledDocument experienceDoc = experienceForSelection.getStyledDocument();

					    Style style = reasonForSelection.addStyle("I'm a Style", null);
				    	
				        Integer selectedEmployee = resultsTable.getSelectedRow();
				        
				        for(int k = 0; k < selectedResults.length; k++)
				        {
				        	StyleConstants.setForeground(style, Color.black);
					        try { skillsDoc.insertString(skillsDoc.getLength(), selectedResults[k].skillName + ": ", style); }
					        catch (BadLocationException ee){}
					        
					        if(employees[selectedEmployee].skillLevels.get(k) > selectedResults[k].skillLevel)
					        {
					        	StyleConstants.setForeground(style, Color.green);
						        try { skillsDoc.insertString(skillsDoc.getLength(), skillLevels[employees[selectedEmployee].skillLevels.get(k)] + "\n", style); }
						        catch (BadLocationException ee){}
					        }
					        else if(employees[selectedEmployee].skillLevels.get(k) == selectedResults[k].skillLevel)
					        {
					        	StyleConstants.setForeground(style, Color.blue);
						        try { skillsDoc.insertString(skillsDoc.getLength(), skillLevels[employees[selectedEmployee].skillLevels.get(k)] + "\n", style); }
						        catch (BadLocationException ee){}
					        }
					        else if(employees[selectedEmployee].skillLevels.get(k) < selectedResults[k].skillLevel)
					        {
					        	StyleConstants.setForeground(style, Color.red);
						        try { skillsDoc.insertString(skillsDoc.getLength(), skillLevels[employees[selectedEmployee].skillLevels.get(k)] + "\n", style); }
						        catch (BadLocationException ee){}
					        }
					        
					        
					        
					        StyleConstants.setForeground(style, Color.black);
					        try { experienceDoc.insertString(experienceDoc.getLength(), "Experience : " + employees[selectedEmployee].years.get(k).toString() + "\n", style); }
					        catch (BadLocationException ee){}
				        }
				    }
				});
				resultsPane = new JScrollPane(resultsTable);
				resultsPane.setPreferredSize(new Dimension(200,200));
			    add(resultsPane, BorderLayout.CENTER);
			    resultsPane.setBounds(50,100,550,300);
			    
			    reasonForSelection = new JTextPane();
		        reasonForSelection.setEditable(false);
		        add(reasonForSelection);
		        reasonForSelection.setBounds(50, 420, 225, 200);
		        
		        experienceForSelection = new JTextPane();
		        experienceForSelection.setEditable(false);
		        add(experienceForSelection);
		        experienceForSelection.setBounds(275, 420, 225, 200);
				
				
				
			}
		});
		this.add(searchEmployee);
		
		resetSearch = new JButton("Reset Criteria");
	    resetSearch.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		resetSearch.setBounds(600, 360, 150, 40);
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
		
		
		searchAgain = new JButton("Search Again");
		searchAgain.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		searchAgain.setBounds(600, 360, 150, 40);
		searchAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchAgain.setVisible(false);
				searchAgain.setEnabled(false);
				reasonForSelection.setVisible(false);
				reasonForSelection.setEnabled(false);
				experienceForSelection.setVisible(false);
				experienceForSelection.setEnabled(false);
				resultsPane.setVisible(false);
				resultsPane.setEnabled(false);
				scrollPane.setVisible(true);
				searchEmployee.setVisible(true);
				searchEmployee.setEnabled(true);
				resetSearch.setVisible(true);
				resetSearch.setEnabled(true);
				algorithmSelection.setVisible(true);
				algorithmSelection.setEnabled(true);
				
			}
		});
		this.add(searchAgain);
		searchAgain.setVisible(false);
		searchAgain.setEnabled(false);
		
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
	public static employee[] rateEmployees(ArrayList<employee> getEmployees, selectedSkill[] selectedSkills, int algorithm)
	{
		employee[] employees = getEmployees.toArray(new employee[getEmployees.size()]);
		
		//Add switch statement for other algorithms
		switch (algorithm){
		case 0:
			employees = fuzzySearch(getEmployees, selectedSkills);
			break;
		case 1:
			employees = complexFuzzySearch(getEmployees, selectedSkills);
		case 2:
			employees = richardsSearch(getEmployees, selectedSkills);
			break;
		case 3:
			employees = basicSearch(getEmployees, selectedSkills);
			break;
		default:
			System.out.println("Algorithm which does not exist was selected");
		}

		sortByRating(employees, 0, employees.length-1);
		
		return employees;
	}
	
	//Sorts the employees from best to worst
	public static void sortByRating(employee[] arr, int low, int high) {
		 
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		//pick the pivot
		int middle = low + (high - low) / 2;
		double pivot = arr[middle].rating;
 
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
	
	public static void printTables()
	{
		Connection con = null;

			
		try {
			String query = "SELECT * FROM employee";
			String username = "cis499";
			final String password = "password";
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
	}
	
	public static employee[] fuzzySearch (ArrayList<employee> getEmployees, selectedSkill[] selectedSkills) {
		
		employee[] employees = getEmployees.toArray(new employee[getEmployees.size()]);
		double currentMembership;
		int difference;
		int experience = 3;
		int importance;
		int skillFactor;
		int calculatedSkill;
		double membershipTemp;
		for(int i = 0; i < employees.length; i++)
		{
			for(int j = 0; j < selectedSkills.length; j++)
			{
				
				//Use manifolds to determine importance membership
				currentMembership = degreeOfMembership(IMPORTANCE_LOW_MIN, selectedSkills[j].skillImportance, IMPORTANCE_LOW_MAX);//Low
				membershipTemp = degreeOfMembership(IMPORTANCE_MED_MIN, selectedSkills[j].skillImportance, IMPORTANCE_MED_MAX);//Medium
				if(membershipTemp >= currentMembership)
				{
					currentMembership = membershipTemp;
					importance = 2;
				}
				else
				{
					importance = 1;
				}
				membershipTemp = degreeOfMembership(IMPORTANCE_HIGH_MIN, selectedSkills[j].skillImportance, IMPORTANCE_HIGH_MAX);//High
				if(membershipTemp >= currentMembership)
				{
					importance = 3;
				}
				
				
				if(employees[i].years.get(j) < EXPERIENCE_HIGH_MAX)
				{
					//Use manifolds to determine years of experience membership
					currentMembership = degreeOfMembership(EXPERIENCE_LOW_MIN, employees[i].years.get(j), EXPERIENCE_LOW_MAX);//Low
					membershipTemp = degreeOfMembership(EXPERIENCE_MED_MIN, employees[i].years.get(j), EXPERIENCE_MED_MAX);//Medium
					if(membershipTemp >= currentMembership)
					{
						currentMembership = membershipTemp;
						experience = 2;
					}
					else
					{
						experience = 1;
					}
					membershipTemp = degreeOfMembership(EXPERIENCE_HIGH_MIN, employees[i].years.get(j), EXPERIENCE_HIGH_MAX);//High
					if(membershipTemp >= currentMembership)
					{
						currentMembership = membershipTemp;
						experience = 3;
					}
				}
				
				skillFactor = importanceAndExperienceMatrix(importance, experience);
				calculatedSkill = skillFactorSkillLevelMatrix(skillFactor, employees[i].skillLevels.get(j));
				
				difference = calculatedSkill - selectedSkills[j].skillLevel;
				if (difference < 0)
				{
						difference = -1 * difference;
				}
				employees[i].rating += difference;
			}
		}
		
		
		return employees;
	}
	
	public static employee[] complexFuzzySearch (ArrayList<employee> getEmployees, selectedSkill[] selectedSkills) {
		
		employee[] employees = getEmployees.toArray(new employee[getEmployees.size()]);
		double[] importanceMembership = {0, 0, 0};
		double[] experienceMembership = {0, 0, 1};
		double[] skillFactorMembership = {0, 0, 0};
		double[] calculatedSkills = {0, 0, 0, 0};
		double percentage = 0;
		double difference = 0;
		int skillFactor;
		int calculatedSkill;
		for(int i = 0; i < employees.length; i++)
		{
			for(int j = 0; j < selectedSkills.length; j++)
			{
				
				//Use manifolds to determine importance membership
				importanceMembership[0] = degreeOfMembership(IMPORTANCE_LOW_MIN, selectedSkills[j].skillImportance, IMPORTANCE_LOW_MAX);//Low
				importanceMembership[1] = degreeOfMembership(IMPORTANCE_MED_MIN, selectedSkills[j].skillImportance, IMPORTANCE_MED_MAX);//Medium
				importanceMembership[2] = degreeOfMembership(IMPORTANCE_HIGH_MIN, selectedSkills[j].skillImportance, IMPORTANCE_HIGH_MAX);//High
				
				//Use manifolds to determine years of experience membership
				if (employees[i].years.get(j) < EXPERIENCE_HIGH_MAX)
				{
					experienceMembership[0] = degreeOfMembership(EXPERIENCE_LOW_MIN, employees[i].years.get(j), EXPERIENCE_LOW_MAX);//Low
					experienceMembership[1] = degreeOfMembership(EXPERIENCE_MED_MIN, employees[i].years.get(j), EXPERIENCE_MED_MAX);//Medium
					experienceMembership[2] = degreeOfMembership(EXPERIENCE_HIGH_MIN, employees[i].years.get(j), EXPERIENCE_HIGH_MAX);//High
				}
				
				for(int k = 0; k < 3; k++)
				{
					for(int l = 0; l < 3; l++)
					{
						skillFactor = importanceAndExperienceMatrix(k+1,l+1);
						switch (skillFactor) {
						case 1: 
							skillFactorMembership[0] += importanceMembership[k] * experienceMembership[l];
							break;
						case 2: 
							skillFactorMembership[1] += importanceMembership[k] * experienceMembership[l];
							break;
						case 3:
							skillFactorMembership[2] += importanceMembership[k] * experienceMembership[l];
							break;
						default:
							System.out.println("Something went wrong in complex fuzzy. I and E matrix returned something wrong");
							break;
						}
					}
				}
				
				for(int k = 0; k < 3; k++)
				{
					calculatedSkill = skillFactorSkillLevelMatrix(k + 1, employees[i].skillLevels.get(j));
					switch (calculatedSkill) {
					case 0:
						calculatedSkills[0] = 1;
						break;
					case 1: 
						calculatedSkills[1] += (double)calculatedSkill * skillFactorMembership[k];
						break;
					case 2: 
						calculatedSkills[2] += (double)calculatedSkill * skillFactorMembership[k];
						break;
					case 3:
						calculatedSkills[3] += (double)calculatedSkill * skillFactorMembership[k];
						break;
					default:
						System.out.println("Something went wrong in complex fuzzy. FandL matrix returned something wrong");
						break;
					}
				}
				
				percentage = calculatedSkills[0] + calculatedSkills[1] + calculatedSkills[2] + calculatedSkills[3];
				
				for(int k = 0; k<4; k++)
				{
					difference += (calculatedSkills[k] / percentage);
				}
				difference -= selectedSkills[j].skillLevel;
				
				if (difference < 0)
				{
						difference = -1 * difference;
				}
				employees[i].rating += difference;
			}
		}
		
		
		return employees;
	}
	
	public static employee[] richardsSearch (ArrayList<employee> getEmployees, selectedSkill[] selectedSkills) {
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
						difference = difference / (1 + (selectedSkills[j].skillImportance/3));
				}
				else
				{
					difference = -1 * difference * (1 + (selectedSkills[j].skillImportance/3));
				}
				employees[i].rating += difference;
			}
		}
		
		return employees;
	}
	
	
	public static employee[] basicSearch (ArrayList<employee> getEmployees, selectedSkill[] selectedSkills) {
		employee[] employees = getEmployees.toArray(new employee[getEmployees.size()]);
		int difference;
		for(int i = 0; i < employees.length; i++)
		{
			for(int j = 0; j < selectedSkills.length; j++)
			{
				difference = employees[i].skillLevels.get(j) - (selectedSkills[j].skillLevel);
				if (difference < 0)
				{
						difference = -1 * difference;
				}				
				employees[i].rating += difference;
			}
		}
		
		return employees;
	}
	
	public static double degreeOfMembership(double minRange, double xi, double maxRange)
	{
		double degreeOfMembership = 0;
		if(xi >= minRange && xi <= maxRange)
		{
			double centerPoint = (maxRange + minRange)/2;
			
			double slope;
			
			
			if(xi < centerPoint)
			{
				slope = 1.0/(centerPoint - minRange);
				
				degreeOfMembership = (xi - minRange) * slope;
			}
			else
			{
				slope = 1.0/(centerPoint - maxRange);
				
				degreeOfMembership = (xi - maxRange) * slope;
			}
		}
		
		return degreeOfMembership;
	}
	
	public static int importanceAndExperienceMatrix(int importance, int experience)
	{
		/*			Importance
				+===+===+===+===+
				|   | L | M | H |
				+===+===+===+===+
			E	| H | H | H | H |
			X	+---+---+---+---+
			P	| M | H | M | M |
				+---+---+---+---+
				| L | M | L | L |
				+---+---+---+---+
				
		*/
		int value = 0;
		
        switch (importance) {
        case 1:  
        	switch(experience) {
        	case 1:	value = 2;//Low-Low
        			break;
        	case 2: value = 3;//Low-Medium
        			break;
        	case 3: value = 3;//Low-High
        			break;
        	default:
        			System.out.println("Error passing variables to importanceAndExperienceMatrix, importance =" + importance + " exp = " + experience);
        			break;
        	}  
                 break;
        case 2:  
        	switch(experience) {
        	case 1:	value = 1;//Medium-Low
        			break;
        	case 2: value = 2;//Medium-Medium
        			break;
        	case 3: value = 3;//Medium-High
        			break;
        	default:
        			System.out.println("Error passing variables to importanceAndExperienceMatrix, importance =" + importance + " exp = " + experience);
        			break;
        	}
                 break;
        case 3:  
        	switch(experience) {
        	case 1:	value = 1;//High-Low
        			break;
        	case 2: value = 2;//High-Medium
        			break;
        	case 3: value = 3;//High-High
        			break;
        	default:
        			System.out.println("Error passing variables to importanceAndExperienceMatrix, importance =" + importance + " exp = " + experience);
        			break;
        	}
                 break;
        default: System.out.println("Error passing variables to importanceAndExperienceMatrix, importance =" + importance + " exp = " + experience);
                 break;
        }
		
		
		return value;
	}
	
	public static int skillFactorSkillLevelMatrix(int skillFactor, int skillLevel)
	{
		/*			Skill Level
				+===+===+===+===+
				|   | L | M | H |
			F	+===+===+===+===+
			A	| H | M | H | H |
			C	+---+---+---+---+
			T	| M | M | M | H |
			O	+---+---+---+---+
			R	| L | L | L | M |
				+---+---+---+---+
				
		*/
		int value = 0;
		
        switch (skillLevel) {
        case 0:
        	value = 0;
        	break;
        case 1:  
        	switch(skillFactor) {
        	case 1:	value = 1;//Low-Low
        			break;
        	case 2: value = 2;//Low-Medium
        			break;
        	case 3: value = 2;//Low-High
        			break;
        	default:
        			System.out.println("Error passing variables to skillFactorSkillLevelMatrix, factor="+skillFactor+" level="+skillLevel);
        			break;
        	}  
                 break;
        case 2:  
        	switch(skillFactor) {
        	case 1:	value = 1;//Medium-Low
        			break;
        	case 2: value = 2;//Medium-Medium
        			break;
        	case 3: value = 3;//Medium-High
        			break;
        	default:
        			System.out.println("Error passing variables to skillFactorSkillLevelMatrix, factor="+skillFactor+" level="+skillLevel);
        			break;
        	}
                 break;
        case 3:  
        	switch(skillFactor) {
        	case 1:	value = 2;//High-Low
        			break;
        	case 2: value = 3;//High-Medium
        			break;
        	case 3: value = 3;//High-High
        			break;
        	default:
        			System.out.println("Error passing variables to skillFactorSkillLevelMatrix, factor="+skillFactor+" level="+skillLevel);
        			break;
        	}
                 break;
        default: System.out.println("Error passing variables to skillFactorSkillLevelMatrix, factor="+skillFactor+" level="+skillLevel);
                 break;
        }
		
		
		return value;
	}
}


