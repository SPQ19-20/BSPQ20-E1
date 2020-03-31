package es.deusto.client.windows;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ForgottenPassword extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtxmail;
	private String code;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgottenPassword frame = new ForgottenPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ForgottenPassword() {
		code = "";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Go back to login window
				dispose();
			}
		});
		btnClose.setBounds(27, 232, 85, 21);
		contentPane.add(btnClose);
		
		JLabel lblHaveYouForgotten = new JLabel("Have You Forgotten your Password?");
		lblHaveYouForgotten.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHaveYouForgotten.setBounds(87, 29, 275, 21);
		contentPane.add(lblHaveYouForgotten);
		
		JLabel lblEnterYourMail = new JLabel("Enter your mail here:");
		lblEnterYourMail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnterYourMail.setBounds(27, 80, 156, 15);
		contentPane.add(lblEnterYourMail);
		
		txtxmail = new JTextField();
		txtxmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtxmail.setBounds(265, 79, 96, 19);
		contentPane.add(txtxmail);
		txtxmail.setColumns(10);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*if(email exists in db){
				 * for(int i =0;i<4;i++){
				 * 	code = ""+ ((int)(Math.random()*9))
				 * }
				 * send code to mail
				 * String mail = txtmail.getText();
				 * new CodeVerification(code, mail);
				 * }else{
				 * JOptionPane.showMessageDialog(null, "Email Does Not Exist")
				 * }
				*/
			}
		});
		
			
		btnConfirm.setBounds(299, 232, 85, 21);
		contentPane.add(btnConfirm);
	}
}
