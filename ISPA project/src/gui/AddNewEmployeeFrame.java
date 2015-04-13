package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.searchQuery;
import net.miginfocom.swing.MigLayout;

public class AddNewEmployeeFrame{
	private String id, firstName, lastName, dept, pos, dob, dor;
	private String fullName;
	private JFrame frame;
	//private String selfRating = "", managerRating = "", yearsExp = "";
	private ArrayList<String> skillsList = new ArrayList<String>();
	private ArrayList<String> selfRatings = new ArrayList<String>();
	private ArrayList<String> managerRatings = new ArrayList<String>();
	private ArrayList<String> yearsExps = new ArrayList<String>();
	private String previousCard;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6301161010602605836L;

	public AddNewEmployeeFrame(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.setSize(300, 800);
		//frame.setBounds(200, 150, 400, 800);
		frame.setBounds(200, 100, 600, 600);
		frame.setMinimumSize(new Dimension(600, 600));
		initGUI();
	}
	
	public void initGUI(){
		frame.setLayout(new BorderLayout());
		/* Title in North */
		/*JLabel activityTitle = new JLabel("Add New Employee");
		activityTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		JPanel activityTitlePanel = new JPanel();
		activityTitlePanel.add(activityTitle);
		this.add(activityTitlePanel, BorderLayout.NORTH);
		*/
		
		CardLayout cardLayout = new CardLayout();
		JPanel cardPanel = new JPanel(cardLayout);
		
		/* Basic Info Panel */
		JPanel basicInfoPanel = new JPanel(new MigLayout(""));
		basicInfoPanel.setBackground(Color.WHITE);
		JLabel title = new JLabel("Employee Basic Info");
		title.setFont(new Font("Lucida Grande", Font.BOLD, 20));	
		basicInfoPanel.add(title, "wrap");
		JPanel basicLeft = new JPanel(new MigLayout(""));
		basicLeft.setBackground(Color.WHITE);
		JPanel basicRight = new JPanel(new MigLayout(""));
		basicRight.setBackground(Color.WHITE);
		
		JLabel idLabel = new JLabel("ID #");
		JTextField idTextField = new JTextField(10);
		
		JLabel firstLabel = new JLabel("First Name");
		JTextField firstTextField = new JTextField(10);

		JLabel lastLabel = new JLabel("Last Name");
		JTextField lastTextField = new JTextField(10);

		JLabel deptLabel = new JLabel("Dept.");
		JTextField deptTextField = new JTextField(10);

		JLabel posLabel = new JLabel("Pos.");
		JTextField posTextField = new JTextField(10);

		JLabel dobLabel = new JLabel("DOB");
		JTextField dobTextField = new JTextField(10);

		JLabel dorLabel = new JLabel("DOR");
		JTextField dorTextField = new JTextField(10);
		
		basicLeft.add(idLabel);
		basicLeft.add(idTextField, "wrap");
		basicLeft.add(firstLabel);
		basicLeft.add(firstTextField, "wrap");
		basicLeft.add(lastLabel);
		basicLeft.add(lastTextField, "wrap");
		basicLeft.add(deptLabel);
		basicLeft.add(deptTextField, "wrap");
		
		basicRight.add(posLabel);
		basicRight.add(posTextField, "wrap");
		basicRight.add(dobLabel);
		basicRight.add(dobTextField, "wrap");
		basicRight.add(dorLabel);
		basicRight.add(dorTextField, "wrap");
		
		basicInfoPanel.add(basicLeft);
		basicInfoPanel.add(basicRight, "wrap");
		
		JTextArea instructions = new JTextArea("");
		
		JPanel buttonPanel = new JPanel(new MigLayout(""));
		buttonPanel.setBackground(Color.WHITE);
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(idTextField.getText() == null || idTextField.getText() == ""){
					System.out.println("can't be null");
				}
				id = idTextField.getText();
				firstName = firstTextField.getText();
				lastName = lastTextField.getText();
				setFullName(firstName, lastName);
				dept = deptTextField.getText();
				pos = posTextField.getText();
				dob = dobTextField.getText();
				dor = dorTextField.getText();
				
				instructions.setText("Please specify skill set for " + getFullName());
				
				cardLayout.next(cardPanel);
			}
		});
		//buttonPanel.add(next);
		//basicInfoPanel.add(buttonPanel);
		
		/* Skill Set Panel */
		JPanel skillSetPanel = new JPanel(new MigLayout("", "fill", ""));
		skillSetPanel.setBackground(Color.WHITE);
		JLabel skillSetTitle = new JLabel("Skill Set Info");
		skillSetTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));	
		skillSetPanel.add(skillSetTitle, "align center, wrap");
	
		
		ArrayList<String> skills = new ArrayList<String>();
		
		Statement stmt = null;
		Connection con;
		int loginType = 0;

			
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
		
		String[] array = skills.toArray(new String[skills.size()]);
		JComboBox<String> dropDown = new JComboBox<String>(array);
		
		JPanel skillPanel = new JPanel(new MigLayout("insets 20 20 20 20","shrink",""));
		skillPanel.setBackground(Color.WHITE);
		skillPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		skillPanel.add(instructions, "wrap");
		skillPanel.add(dropDown, "wrap");
		JLabel selfRatingLabel = new JLabel("Self-rating");
		JLabel yearsExpLabel = new JLabel("Years Experience");
		JTextField selfRatingField = new JTextField(20);
		JTextField yearsExpField = new JTextField(20);
		
		JPanel fieldPanel = new JPanel(new MigLayout("insets 15 15 15 15", "[][][grow]", "[]"));
		fieldPanel.setBackground(Color.WHITE);
		
		fieldPanel.add(selfRatingLabel);
		fieldPanel.add(selfRatingField, "wrap");
		fieldPanel.add(yearsExpLabel);
		fieldPanel.add(yearsExpField);
		/*JPanel leftPanel = new JPanel(new MigLayout("wmax 150"));
		leftPanel.setBackground(Color.WHITE);
		JPanel rightPanel = new JPanel(new MigLayout("wmax 150"));
		rightPanel.setBackground(Color.WHITE);
		leftPanel.add(selfRatingLabel, "wrap");
		rightPanel.add(selfRatingField, "wrap, span");
		leftPanel.add(managerRatingLabel, "wrap");
		rightPanel.add(managerRatingField, "wrap, span");
		leftPanel.add(yearsExpLabel);
		rightPanel.add(yearsExpField);

		skillPanel.add(leftPanel);
		skillPanel.add(rightPanel, "wrap");*/
		
		skillPanel.add(fieldPanel);
		
		JButton add = new JButton("Add Skill");
		
		JScrollPane pane = new JScrollPane(getPreviewPanel());
		
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				skillsList.add(array[dropDown.getSelectedIndex()]);
				System.out.println(array[dropDown.getSelectedIndex()]);
				selfRatings.add(selfRatingField.getText());
				selfRatingField.setText("");
				yearsExps.add(yearsExpField.getText());
				yearsExpField.setText("");
				pane.setViewportView(getPreviewPanel());
			}
			
		});
		
		skillPanel.add(add, "dock south");
		
		skillSetPanel.add(skillPanel, "wrap");
		skillSetPanel.add(pane, "wrap");
		
		/* Add panels to card layout*/
		cardPanel.add(basicInfoPanel, "Basic Info");
		cardPanel.add(skillSetPanel, "Skill Set");
		
		frame.add(cardPanel, BorderLayout.CENTER);

		
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				frame.dispose();
				
				searchQuery sq = new searchQuery();
				if(sq.addOrDelete("INSERT INTO employee (id,FirstName,LastName,"
						+ "dept,position,dob) VALUES (" + id
						+ ",'" + firstName + "','" + lastName + "','" + dept + "','" +
						pos + "','" + dob + "');") == 1){
					System.out.println("success");
				}
				
				JOptionPane.showMessageDialog(new JLabel("Success"), "This employee has been successfully added.");

			}
			
		});
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				frame.dispose();
			}
			
		});
		
		JButton previous = new JButton("Previous");
		previous.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardLayout.previous(cardPanel);
				//cardLayout.show(cardPanel, "Basic Info");

			}

		});
		
		JPanel bottombuttonPanel = new JPanel(new MigLayout("", "", ""));
		bottombuttonPanel.setBackground(Color.WHITE);

		bottombuttonPanel.add(exit);
		bottombuttonPanel.add(previous);
		bottombuttonPanel.add(next);
		bottombuttonPanel.add(confirm);
		
		frame.add(bottombuttonPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
		
		frame.pack();
		
	}		
	
	public JPanel getPreviewPanel(){
		JPanel previewPanel = new JPanel(new MigLayout("hmin 200"));
		previewPanel.setBackground(Color.WHITE);
		previewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		JTextArea instructions2 = new JTextArea("Preview of skillset:" );
		JTextArea skillspreview = new JTextArea("");
		//skillspreview.setMinimumSize(new Dimension());
		//JTextArea skillspreview = new JTextArea(selfRating + " " + managerRating + " " + yearsExp);
		if(skillsList.size() == 0){
			System.out.println("skills = 0");
			skillspreview.setVisible(false);
		} else {
			for(int i = 0; i < selfRatings.size(); i++){
				skillspreview.append(skillsList.get(i) + "\tSelf Rating: " + selfRatings.get(i)  + "\tYrs. Exp: " + yearsExps.get(i) + "\n");
			}
			skillspreview.setVisible(true);
		}
		
		previewPanel.add(instructions2, "wrap");
		previewPanel.add(skillspreview);
		return previewPanel;
	}
	
	public void setFullName(String first, String last){
		fullName = first + " " + last;
	}

	public String getFullName(){
		return fullName;
	}
}
