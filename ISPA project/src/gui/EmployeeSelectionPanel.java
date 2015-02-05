package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmployeeSelectionPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1109018949622346081L;


	public EmployeeSelectionPanel(){
		this.setBackground(Color.WHITE);
		display();
	}
	
	private void display(){
		this.setLayout(null);
		
		JLabel label = new JLabel("Employee Selection");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		label.setBounds(288, 6, 207, 34);
		this.add(label);
		
		this.setVisible(true);
	}
}
