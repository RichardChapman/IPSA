package main_window;

import gui.EmployeeSelectionPanel;
import gui.IPSAMenuBar;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import java.awt.CardLayout;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;

import java.awt.Component;

public class MainWindow extends JFrame{
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 2025522416616040535L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel topPanel;
	private String profile = "";

	public MainWindow(int profileType){
		
		System.out.println("Creating main window.");
		
		/* Changed this from DISPOSE to EXIT
		 * because DISPOSE only exits the window but leaves the 
		 * application running
		 * 
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		this.setContentPane(contentPane);
		
	    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		//tabbedPane.setBounds(6, 49, 788, 523);
			
		// Create the four "activities"
		EmployeeSelectionPanel employeeSelectionPanel = new EmployeeSelectionPanel();
		ViewEmployeesPanel viewEmployeesPanel = new ViewEmployeesPanel();
		ManageEmployeesPanel manageEmployeesPanel = new ManageEmployeesPanel();			
		ManageSkillsPanel manageSkillsPanel = new ManageSkillsPanel();
		
		switch(profileType){
			case 1:
				profile = "admin";
				tabbedPane.addTab("Manage Skills", null, manageSkillsPanel, null);
				tabbedPane.addTab("Manage Employees", null, manageEmployeesPanel, null);
				break;		
			case 2:
				profile = "manager";
				tabbedPane.addTab("Employee Selection", null, employeeSelectionPanel, null);
				tabbedPane.addTab("View Employees", null, viewEmployeesPanel, null);
				break;
			case 3:
				profile = "project lead";
				tabbedPane.addTab("Employee Selection", null, employeeSelectionPanel, null);
				break;
		}
		
		JLabel profileText = new JLabel(profile + " ");
		profileText.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		
		/*topPanel = new JPanel(new MigLayout("insets 15 15 15 15", "[][grow][10]", "[]"));
		topPanel.setBackground(Color.WHITE);
		topPanel.setMinimumSize(new Dimension(contentPane.getWidth(), 50));
		topPanel.setMaximumSize(new Dimension(contentPane.getWidth(), 50));
		topPanel.setPreferredSize(new Dimension(contentPane.getWidth(), 50));

		topPanel.add(profileText, "east");*/
		
		// Add tabbedPane to contentPane
		//contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		//IPSAMenuBar m = new IPSAMenuBar();
		//this.setJMenuBar(m);
		//this.setVisible(true);
	}
}
