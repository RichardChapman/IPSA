package main_window;

import gui.EmployeeSelectionPanel;
import gui.ManageEmployeesPanel;
import gui.ManageSkillsPanel;
import gui.ViewEmployeesPanel;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import java.awt.CardLayout;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;

import java.awt.Component;

public class MainWindow extends JFrame{
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 2025522416616040535L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;

		public MainWindow(){
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 800, 600);
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(null);
			setContentPane(contentPane);
			
		    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBackground(Color.WHITE);
			tabbedPane.setBounds(6, 49, 788, 523);
			
			// Create the four "activities"
			EmployeeSelectionPanel employeeSelectionPanel = new EmployeeSelectionPanel();
			ViewEmployeesPanel viewEmployeesPanel = new ViewEmployeesPanel();
			ManageEmployeesPanel manageEmployeesPanel = new ManageEmployeesPanel();			
			ManageSkillsPanel manageSkillsPanel = new ManageSkillsPanel();
			
			// Add activities to tabbedPane
			tabbedPane.addTab("Employee Selection", null, employeeSelectionPanel, null);
			tabbedPane.addTab("View Employees", null, viewEmployeesPanel, null);
			tabbedPane.addTab("Manage Employees", null, manageEmployeesPanel, null);
			tabbedPane.addTab("Manage Skills", null, manageSkillsPanel, null);
			
			/* Below is the actual configuration once we get the profileType
			 * from the login information. We will display the different activities
			 * accordingly
			 *
			switch(profileType){
			case ProjectLead:
				tabbedPane.addTab("Employee Selection", null, employeeSelectionPanel, null);
				break;
			case Administrator:
				tabbedPane.addTab("Manage Skills", null, manageSkillsPanel, null);
				tabbedPane.addTab("Manage Employees", null, manageEmployeesPanel, null);
				break;		
			case Manager:
				tabbedPane.addTab("Employee Selection", null, employeeSelectionPanel, null);
				tabbedPane.addTab("View Employees", null, viewEmployeesPanel, null);
				break;
			}
			*/
			
			// Add tabbedPane to contentPane
			contentPane.add(tabbedPane);
		}
}
