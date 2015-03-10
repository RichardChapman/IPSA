package ipsa;

import java.awt.EventQueue;

import javax.swing.JFrame;

import login.LoginWindow;

public class IPSAApplication {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IPSAApplication window = new IPSAApplication();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IPSAApplication() {
		LoginWindow lw = new LoginWindow();
		lw.frmIpsaLogin.setVisible(true);
	}
}
