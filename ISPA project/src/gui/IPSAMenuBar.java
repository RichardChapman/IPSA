package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class IPSAMenuBar extends JMenuBar {
	// Menus
	private JMenu ipsaMenu = new JMenu("IPSA"); //logout, preferences, about ipsa
	private JMenu help = new JMenu();
	
	// Menu items
	private JMenuItem logout = new JMenuItem();

	public IPSAMenuBar(){
		logout.setText("Logout");
		logout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Logout?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(choice == JOptionPane.YES_NO_OPTION){
					System.exit(0);
				}
			}
			
		});
		
		ipsaMenu.add(logout);
		
	}
	
}
