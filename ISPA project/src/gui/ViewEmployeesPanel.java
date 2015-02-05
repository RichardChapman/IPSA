package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewEmployeesPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9177396264274181320L;

	public ViewEmployeesPanel(){
		this.setBackground(Color.WHITE);
		display();
	}
	
	private void display(){
		this.setLayout(null);
		
		JLabel label = new JLabel("View Employees");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		label.setBounds(288, 6, 207, 34);
		this.add(label);
		
		this.setVisible(true);
	}
}
