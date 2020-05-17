package es.deusto.client.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import es.deusto.client.controller.Controller;

/**
 * This class is the one used to create a window that allows
 * a user to log in when he forgets the password.
 */

public class ForgottenPassword extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtxmail;
	private String code;
	
	private Controller controller;
	private LanguageManager langManager;

	/**
	 * Create the frame.
	 */
	public ForgottenPassword(Controller controller) {
		this.setVisible(true);
		this.controller = controller;
		this.langManager = controller.getLanguageManager();

		code = "";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton(langManager.getString("closeButton"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Go back to login window
				new LogInWindow(controller);
				dispose();
			}
		});
		btnClose.setBounds(27, 232, 100, 21);
		contentPane.add(btnClose);
		
		JLabel lblHaveYouForgotten = new JLabel(langManager.getString("forgottenPassText"));
		lblHaveYouForgotten.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHaveYouForgotten.setBounds(87, 29, 275, 21);
		contentPane.add(lblHaveYouForgotten);
		
		JLabel lblEnterYourMail = new JLabel(langManager.getString("enterEmailText"));
		lblEnterYourMail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnterYourMail.setBounds(27, 80, 156, 15);
		contentPane.add(lblEnterYourMail);
		
		txtxmail = new JTextField();
		txtxmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtxmail.setBounds(240, 79, 140, 19);
		contentPane.add(txtxmail);
		txtxmail.setColumns(10);
		
		JButton btnConfirm = new JButton(langManager.getString("confirmButton"));
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.attemptPasswordRecovery(txtxmail.getText());
				JOptionPane.showMessageDialog(null, langManager.getString("passRecoveryDone"));
			}
		});
		
			
		btnConfirm.setBounds(279, 232, 100, 21);
		contentPane.add(btnConfirm);

		this.setSize(450, 400);
		this.setResizable(false);
	}
}
