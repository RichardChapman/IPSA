package login;

//import Login;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main_window.MainWindow;



public class LoginWindow {
	private JFrame frame;
	private JFrame frmIpsaLogin;
	private JTextField passField;
	private JTextField userField;

	public LoginWindow(){
		initialize();
	}
	
	private void initialize() {
		frmIpsaLogin = new JFrame();
		frmIpsaLogin.setTitle("IPSA - Login");
		frmIpsaLogin.setBounds(100, 100, 306, 258);
		frmIpsaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIpsaLogin.getContentPane().setLayout(null);
		
		passField = new JTextField();
		passField.setBounds(129, 85, 86, 20);
		frmIpsaLogin.getContentPane().add(passField);
		passField.setColumns(10);
		
		userField = new JTextField();
		userField.setBounds(129, 54, 86, 20);
		frmIpsaLogin.getContentPane().add(userField);
		userField.setColumns(10);
				
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(50, 57, 69, 14);
		frmIpsaLogin.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(73, 88, 46, 14);
		frmIpsaLogin.getContentPane().add(lblPassword);
		
		JLabel lblIntelligentPersonnelSelection = new JLabel("Intelligent Personnel Selection Agent");
		lblIntelligentPersonnelSelection.setBounds(10, 11, 226, 14);
		frmIpsaLogin.getContentPane().add(lblIntelligentPersonnelSelection);
		
		final JTextArea txtrErrorThatUsername = new JTextArea();
		txtrErrorThatUsername.setEnabled(false);
		txtrErrorThatUsername.setForeground(new Color(255, 0, 0));
		txtrErrorThatUsername.setBackground(SystemColor.menu);
		txtrErrorThatUsername.setLineWrap(true);
		txtrErrorThatUsername.setEditable(false);
		txtrErrorThatUsername.setWrapStyleWord(true);
		txtrErrorThatUsername.setText("Error: That username and password combination doesn't exist.");
		txtrErrorThatUsername.setBounds(10, 172, 270, 36);
		frmIpsaLogin.getContentPane().add(txtrErrorThatUsername);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(129, 133, 89, 23);
		frmIpsaLogin.getContentPane().add(btnLogin);
		btnLogin.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int loginType;
				try {
					loginType = loginAttempt(userField.getText(), passField.getText());
					if(loginType==0){
						txtrErrorThatUsername.setEnabled(true);
					}
					else{
						//Login.login(loginType);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	
	/*
	private void initialize(){
		// This is the login window
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel labelLogin = new JLabel("IPSA Login");
		labelLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		labelLogin.setBounds(357, 111, 132, 53);
		
		frame.getContentPane().add(labelLogin);
	
		JButton buttonGo = new JButton("Go");
		buttonGo.setBounds(357, 203, 117, 29);
	    buttonGo.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.dispose();
	    		MainWindow mw = new MainWindow();
	    		mw.setVisible(true);
	    	}
	    });		
	    
		frame.getContentPane().add(buttonGo);
		frame.setVisible(true);
	}
	*/

	public static int loginAttempt(String name, String pass) throws SQLException{
		Statement stmt = null;
		Connection con;
		int loginType = 0;

			
		try {
			String query = "SELECT * FROM logins;";
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
				while (rs.next()){
					//System.out.println(rs.getString("username")+", "+rs.getString("password")+", "+rs.getInt("type"));
					int userMatch = name.compareTo(rs.getString("username"));
					//System.out.println("Comp is " +userMatch);
					int passMatch = pass.compareTo(rs.getString("password"));
					//System.out.println("Comp is " +passMatch);
					
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
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return 0;
	}
}
