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

public class LogInWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	public JTextField id;
	public JTextField pass;
	public JButton bCreate;
	public JButton blogin;
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
		bCreate.addActionListener(this);

		blogin = new JButton("LogIn");
		blogin.setBounds(235, 336, 111, 25);
		getContentPane().add(blogin);
		blogin.addActionListener(this);

		JLabel lblUsuario = new JLabel("User:");
		lblUsuario.setBounds(44, 95, 79, 16);
		getContentPane().add(lblUsuario);

		textField = new JTextField("Kira@killerqueen.es");
		textField.setBounds(97, 92, 160, 22);
		getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField("4567Hard");
		passwordField.setBounds(97, 133, 160, 22);
		getContentPane().add(passwordField);

		JLabel lblContrasea = new JLabel("Password:");
		lblContrasea.setBounds(22, 136, 101, 16);
		getContentPane().add(lblContrasea);

		// TODO delete, this is just for testing
		blogin.doClick();
		dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton == bCreate) {
			int posY = this.getY();
			int posX = this.getX();
			int altura = this.getHeight();
			int anchura = this.getWidth();
			CreateUserWindow p = new CreateUserWindow(this.controller);
			p.setVisible(true);
			p.setSize(anchura, altura);
			p.setLocation(posX, posY);
			p.setResizable(false);
			this.setVisible(false);
			this.dispose();
		} else if (boton == blogin) {
			String email, password;
			email = this.textField.getText();
			password = String.valueOf(this.passwordField.getPassword());
			if (this.controller.attemptNormalLogin(email, password)) {
				// login success
				new UserEventsWindow(this.controller);
				this.dispose();
			}
		}

	}

}
