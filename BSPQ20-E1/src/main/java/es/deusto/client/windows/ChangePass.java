package es.deusto.client.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangePass extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String mail;
	private JTextField txtpass;
	private JTextField txtagain;

	

	/**
	 * Create the frame.
	 */
	public ChangePass(String mail) {
		this.setVisible(true);
		this.mail = mail;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		lblEnterPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnterPassword.setBounds(39, 84, 164, 15);
		contentPane.add(lblEnterPassword);
		
		txtpass = new JTextField();
		txtpass.setBounds(280, 82, 96, 19);
		contentPane.add(txtpass);
		txtpass.setColumns(10);
		
		JLabel lblEnterPasswordAgain = new JLabel("Enter Password Again:");
		lblEnterPasswordAgain.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnterPasswordAgain.setBounds(39, 144, 164, 15);
		contentPane.add(lblEnterPasswordAgain);
		
		txtagain = new JTextField();
		txtagain.setBounds(280, 142, 96, 19);
		contentPane.add(txtagain);
		txtagain.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtpass.getText().equals(txtagain.getText())) {
					//Save password with corresponding mail(parameter already exists)
				}else {
					JOptionPane.showMessageDialog(null, "Fields do not match");
				}
			}
		});
		btnSave.setBounds(155, 221, 85, 21);
		contentPane.add(btnSave);
	}
}
