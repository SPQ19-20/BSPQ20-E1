package es.deusto.client.windows;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.deusto.client.controller.Controller;


public class CreateUserWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextField textField_username;
	private JTextField textField_password;
	private JTextField textField_email;
	private JTextField textField_city;
	private JButton bCreate;

	private Controller controller;
	
	public CreateUserWindow(Controller controller) {
		this.controller = controller;

		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		bCreate = new JButton("Create user");
		bCreate.setBounds(234, 337, 111, 25);
		getContentPane().add(bCreate);
		bCreate.addActionListener(this);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(54, 61, 119, 16);
		getContentPane().add(lblUsername);
		
		textField_username = new JTextField();
		textField_username.setBounds(133, 58, 175, 22);
		getContentPane().add(textField_username);
		textField_username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(54, 110, 119, 16);
		getContentPane().add(lblPassword);
		
		textField_password = new JTextField();
		textField_password.setBounds(133, 107, 175, 22);
		getContentPane().add(textField_password);
		textField_password.setColumns(10);
		
		JLabel lblMail = new JLabel("E-mail:");
		lblMail.setBounds(74, 159, 119, 16);
		getContentPane().add(lblMail);
		
		textField_email = new JTextField();
		textField_email.setBounds(133, 156, 175, 22);
		getContentPane().add(textField_email);
		textField_email.setColumns(10);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(84, 208, 119, 16);
		getContentPane().add(lblCity);
		
		textField_city = new JTextField();
		textField_city.setBounds(133, 205, 175, 22);
		getContentPane().add(textField_city);
		textField_city.setColumns(10);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		Object boton = e.getSource();
		if (boton == bCreate) {
			String email = this.textField_email.getText();
			String password = this.textField_password.getText();
			String name = this.textField_username.getText();
			String city = this.textField_city.getText();
			if (this.controller.attemptNormalSignup(email, password, name, city)) {
				new UserEventsWindow(this.controller);
				this.dispose();
			} else {
				String message = "We could not create this account, maybe this email is already in use";
				JOptionPane.showMessageDialog(this, message, "Could not create account", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			int posY = this.getY();
			int posX = this.getX();
			int altura = this.getHeight();
			int anchura = this.getWidth();
			LogInWindow p = new LogInWindow(this.controller);
			p.setVisible(true);
			p.setSize(anchura, altura);
			p.setLocation(posX, posY);
			p.setResizable(false);
			this.setVisible(false);
			this.dispose();
		}
	}
		

}
