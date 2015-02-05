package login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
}
