package login;

import gui.EmployeeSelectionPanel;
import gui.ManageEmployeesPanel;
import gui.ManageSkillsPanel;
import gui.ViewEmployeesPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dbquery.connectionData;
import net.miginfocom.swing.MigLayout;
import main_window.MainWindow;



public class LoginWindow {
	public JFrame frmIpsaLogin;
	private JTextField passField;
	private JTextField userField;
    private ArrayList<String> usernames = new ArrayList<String>();
    private ArrayList<String> passwords = new ArrayList<String>();
    private ArrayList<Integer> loginTypes = new ArrayList<Integer>();

	public LoginWindow(){
		initialize();
		try {
			getLoginInfo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		frmIpsaLogin = new JFrame();
		frmIpsaLogin.setTitle("IPSA - Login");
		frmIpsaLogin.setBounds(500, 200, 300, 300);
		JPanel loginPanel = new JPanel(new MigLayout("insets 20 20 20 20"));
		frmIpsaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIpsaLogin.getContentPane().setLayout(new MigLayout(""));
		
		passField = new JPasswordField(30);
		//passField.setBounds(129, 85, 86, 20);
		//frmIpsaLogin.getContentPane().add(passField);
		//passField.setColumns(20);
		
		userField = new JTextField(30);
		//userField.setBounds(129, 54, 86, 20);
		//frmIpsaLogin.getContentPane().add(userField);
		//userField.setColumns(20);
				
		JLabel lblUsername = new JLabel("Username");
		//lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		//lblUsername.setBounds(50, 57, 69, 14);
		//frmIpsaLogin.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		//lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		//lblPassword.setBounds(73, 88, 46, 14);
		//frmIpsaLogin.getContentPane().add(lblPassword);
		
		JLabel lblIntelligentPersonnelSelection = new JLabel("Intelligent Personnel");
		JLabel label2 = new JLabel("Selection Agent");
		lblIntelligentPersonnelSelection.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		label2.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		//lblIntelligentPersonnelSelection.setBounds(10, 11, 226, 14);
		//frmIpsaLogin.getContentPane().add(lblIntelligentPersonnelSelection);
		
		final JTextArea txtrErrorThatUsername = new JTextArea();
		txtrErrorThatUsername.setEnabled(false);
		txtrErrorThatUsername.setForeground(new Color(255, 0, 0));
		txtrErrorThatUsername.setBackground(SystemColor.menu);
		txtrErrorThatUsername.setLineWrap(true);
		txtrErrorThatUsername.setEditable(false);
		txtrErrorThatUsername.setWrapStyleWord(true);
		txtrErrorThatUsername.setText("Error: That username and password combination doesn't exist.");
		//txtrErrorThatUsername.setBounds(10, 172, 270, 36);
		txtrErrorThatUsername.setVisible(false);
		//frmIpsaLogin.getContentPane().add(txtrErrorThatUsername);
		
		loginPanel.add(lblIntelligentPersonnelSelection, "wrap, align center");
		loginPanel.add(label2,"wrap 20, align center");
		//loginPanel.add(lblUsername, "");
		loginPanel.add(userField, "wrap");
		//loginPanel.add(lblPassword);
		loginPanel.add(passField, "wrap 20");
		
		JButton btnLogin = new JButton("Login");
		//btnLogin.setBounds(129, 133, 89, 23);
		//frmIpsaLogin.getContentPane().add(btnLogin);
		btnLogin.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int loginType = getLoginType(userField.getText(), passField.getText());
					//loginType = loginAttempt(userField.getText(), passField.getText());
					
					if(loginType==0){
						txtrErrorThatUsername.setVisible(true);
					}
					else{
						System.out.println("Login accepted");
						frmIpsaLogin.dispose();
						MainWindow MW = new MainWindow(loginType);
						MW.setVisible(true);
						//this.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						//this.setVisible(false);
					}				
			}
		});
		
		loginPanel.add(btnLogin);
		frmIpsaLogin.getContentPane().add(loginPanel);

		
	}
	
	//public static int loginAttempt(String name, String pass) throws SQLException{
	public void getLoginInfo() throws SQLException{

		Statement stmt = null;
		Connection con;
		int loginType = 0;

			
		try {
			String query = "SELECT * FROM logins;";
			
			con = DriverManager.getConnection(connectionData.address, connectionData.username, connectionData.password);
	        // Create a Statement
	        Statement stmnt = con.createStatement();

	        // Execute a query
	        ResultSet rs = stmnt.executeQuery(query);
			try{
				while(rs.next()){
					usernames.add(rs.getString("username"));
					passwords.add(rs.getString("password"));
					loginTypes.add(rs.getInt("type"));
				}
			} catch (SQLException e){
				
			} finally {
				if(stmt != null){
					stmt.close();
				}
			}
	        
	        
	        /*try{
				while (rs.next()){
					System.out.println(rs.getString("username")+", "+rs.getString("password")+", "+rs.getInt("type"));
					int userMatch = name.compareTo(rs.getString("username"));
					System.out.println("Comp is " +userMatch);
					int passMatch = pass.compareTo(rs.getString("password"));
					System.out.println("Comp is " +passMatch);
					
					if(userMatch==0 && passMatch==0){
						loginType = rs.getInt("type");
						return loginType;
					}
				}
			} catch (SQLException e){
					
			} finally{
				if(stmt!= null) {
					stmt.close();
				}
			}	*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getLoginType(String username, String password){
		int i, loginType = 0;
		for(i = 0; i < usernames.size(); i++){
			System.out.println(usernames.get(i));
			int userMatch = username.compareTo(usernames.get(i));
			int passMatch = password.compareTo(passwords.get(i));
			
			if(userMatch==0 && passMatch==0){
				loginType = loginTypes.get(i);
				break;
			}
		}
		return loginType;
	}
}