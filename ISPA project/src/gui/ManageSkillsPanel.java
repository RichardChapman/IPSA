package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import db.searchQuery;
import net.miginfocom.swing.MigLayout;

//comment
//comment 
//comment
public class ManageSkillsPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4069391136788920444L;
	
	
	String skill;
	public ManageSkillsPanel(){
		this.setBackground(Color.WHITE);
		display();
	}
	
	private void display(){
		this.setLayout(new MigLayout(""));
		JLabel label = new JLabel("Manage Skills");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		this.add(label, "wrap");
		
		Statement stmt = null;
		Connection con;

		ArrayList<String> skills = new ArrayList<String>();
			
		String columnNames[] = {"Skill Name"};		
		
		try {
			String query = "SELECT * FROM hard_skills_translator;";
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
					skills.add(rs.getString("name"));
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
		};
		
		String[][] dataValues = new String[skills.size()+1][columnNames.length];
		
		int i;
		
		for(i = 0; i < skills.size(); i++){
			dataValues[i][0] = skills.get(i);
		}
		
		TableModel model = new DefaultTableModel(dataValues, columnNames){
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
		
		final JTable skillTable = new JTable(model);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		skillTable.setRowSorter(sorter);
		
		JScrollPane tableScrollPane = new JScrollPane(skillTable);
		this.add(tableScrollPane);
		
		JButton addSkillButton = new JButton("Add New Skill");
		this.add(addSkillButton, "dock south");
		addSkillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skill = JOptionPane.showInputDialog("Whats the name of the skill you would like to add?");
				if(skill == null || (skill != null && ("".equals(skill))))   
				{
					JOptionPane.showMessageDialog(label, "You have not added any skills.");
				}else
				{
					searchQuery s = new searchQuery();
					if(s.addOrDelete("INSERT INTO hard_skills_translator (name) VALUES ('" + skill + "');") == 1){
						JOptionPane.showMessageDialog(label, "You have added the skill " + skill +" to the database.");
					} else {
						JOptionPane.showMessageDialog(label, "Error");
					}
					
					tableScrollPane.setViewportView(skillTable);

				}

			}
		});
		
		JButton removeSkillButton = new JButton("Remove Skill");
		removeSkillButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				skill = JOptionPane.showInputDialog("Whats the name of the skill you would like to delete?");
				if(skill == null || (skill != null && ("".equals(skill))))   
				{

				}else
				{
					JOptionPane.showConfirmDialog(label, "Are you sure you would like to delete the " +skill+" skill?","Deletion Warning", JOptionPane.WARNING_MESSAGE);
					searchQuery s = new searchQuery();
					if(s.addOrDelete("DELETE FROM hard_skills_translator WHERE name='" + skill + "';") == 1){
						JOptionPane.showMessageDialog(label, "You have removed the skill " + skill +" from the database.");
					}
				}
			}
		});
		this.add(removeSkillButton, "dock south");

		this.setVisible(true);
	}
}
