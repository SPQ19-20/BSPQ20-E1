package es.deusto.client.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import es.deusto.client.controller.Controller;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class LogInWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	public JTextField id;
	public JTextField pass;
	public JButton bCreate;
	public JButton blogin;
	private JButton bForgottenPass;
	public JCheckBox cBoxAdmin;
	public JCheckBox cBox;
	private JTextField textField;
	private JPasswordField passwordField;

	private Controller controller;

	public LogInWindow(Controller controller) {
		this.controller = controller;

		getContentPane().setLayout(null);
		setTitle("LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		bCreate = new JButton("New User");
		bCreate.setBounds(12, 336, 111, 25);
		getContentPane().add(bCreate);

		blogin = new JButton("LogIn");
		blogin.setBounds(235, 336, 111, 25);
		getContentPane().add(blogin);

		JLabel lblUsuario = new JLabel("User:");
		lblUsuario.setBounds(44, 95, 79, 16);
		getContentPane().add(lblUsuario);

		textField = new JTextField("");
		textField.setBounds(97, 92, 160, 22);
		getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField("");
		passwordField.setBounds(97, 133, 160, 22);
		getContentPane().add(passwordField);

		JLabel lblContrasea = new JLabel("Password:");
		lblContrasea.setBounds(22, 136, 101, 16);
		getContentPane().add(lblContrasea);

		bForgottenPass = new JButton("Password recovery");
		bForgottenPass.setBounds(100, 386, 157, 25);
		getContentPane().add(bForgottenPass);

		setListeners();

		this.setVisible(true);
		this.setSize(400, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
	}

	private void setListeners() {
		bCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int posY = getY();
				int posX = getX();
				int altura = getHeight();
				int anchura = getWidth();
				CreateUserWindow p = new CreateUserWindow(controller);
				p.setVisible(true);
				p.setSize(anchura, altura);
				p.setLocation(posX, posY);
				p.setResizable(false);
				setVisible(false);
				dispose();
			}
		});

		blogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email, password;
				email = textField.getText();
				password = String.valueOf(passwordField.getPassword());
				if (controller.attemptNormalLogin(email, password)) {
					// login success
					new UserEventsWindow(controller);
					dispose();
				}
			}
		});

		bForgottenPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ForgottenPassword(controller);
				dispose();
			}
		});
	}

}
