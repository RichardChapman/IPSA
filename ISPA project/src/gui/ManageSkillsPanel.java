package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//comment
//comment 
public class ManageSkillsPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4069391136788920444L;
	
	
	String skill;
	public ManageSkillsPanel(){
		this.setBackground(Color.WHITE);
		display();
	}
	
	private void display(){
		this.setLayout(null);
		JLabel label = new JLabel("Manage Skills");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		label.setBounds(325, 6, 207, 34);

		JButton addSkillButton = new JButton("Add New Skill");
		addSkillButton.setBounds(150, 303, 117, 29);
		addSkillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skill = JOptionPane.showInputDialog("Whats the name of the skill you would like to add?");
				if(skill == null || (skill != null && ("".equals(skill))))   
				{
					JOptionPane.showMessageDialog(label, "You have not added any skills.");
				}else
				{
					JOptionPane.showMessageDialog(label, "You have added " +skill+" to the data base!");
				}

			}
		});
		this.add(addSkillButton);

		JButton removeSkillButton = new JButton("Remove Skill");
		removeSkillButton.setBounds(475, 303, 117, 29);
		removeSkillButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				skill = JOptionPane.showInputDialog("Whats the name of the skill you would like to delete?");
				if(skill == null || (skill != null && ("".equals(skill))))   
				{

				}else
				{
					JOptionPane.showConfirmDialog(label, "Are you sure you would like to delete the " +skill+" skill?","Deletion Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		this.add(removeSkillButton);

		this.add(label);
		this.setVisible(true);
	}
}
