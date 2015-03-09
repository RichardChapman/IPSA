package login;

import java.awt.Font;
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

import main_window.MainWindow;



public class LoginWindow {
	private JFrame frame;

	public LoginWindow(){
		initialize();
	}
	
	private void initialize(){
		// This is the login window
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// The following is temporary until login functionality is implemented
		/* 
		 * 
		 */
		
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
