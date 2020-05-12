package es.deusto.client.windows;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.TopicInfo;
import es.deusto.server.data.Topic;

public class CreateOrganizerWindow extends JFrame implements ActionListener{

    //elements for registering username, email, password and organization.
	private static final long serialVersionUID = 1L;
	private JTextField textField_username, textField_password, textField_email, textField_organization;
	private JButton bCreate, backButton;
	private Controller controller;
	private LanguageManager langManager;
	
	public CreateOrganizerWindow(Controller controller) {
		this.controller = controller;
		this.langManager = controller.getLanguageManager();

		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JLabel lblUsername = new JLabel(langManager.getString("userLabel"));
		lblUsername.setBounds(54, 61, 119, 16);
		getContentPane().add(lblUsername);

		textField_username = new JTextField();
		textField_username.setBounds(133, 58, 175, 22);
		getContentPane().add(textField_username);
		textField_username.setColumns(10);

		JLabel lblPassword = new JLabel(langManager.getString("passwordLabel"));
		lblPassword.setBounds(54, 110, 119, 16);
		getContentPane().add(lblPassword);

		textField_password = new JTextField();
		textField_password.setBounds(133, 107, 175, 22);
		getContentPane().add(textField_password);
		textField_password.setColumns(10);

		JLabel lblMail = new JLabel(langManager.getString("emailLabel"));
		lblMail.setBounds(54, 159, 119, 16);
		getContentPane().add(lblMail);

		textField_email = new JTextField();
		textField_email.setBounds(133, 156, 175, 22);
		getContentPane().add(textField_email);
		textField_email.setColumns(10);

        //aqui cambiamos los valores city -> organization
		JLabel lblOrganization = new JLabel(langManager.getString("OrganizationLabel"));
		lblOrganization.setBounds(54, 208, 119, 16);
		getContentPane().add(lblOrganization);

		textField_organization = new JTextField();
		textField_organization.setBounds(133, 205, 175, 22);
		getContentPane().add(textField_organization);
        textField_organization.setColumns(10);

        bCreate = new JButton(langManager.getString("createUserButton"));
		bCreate.setBounds(214, 254, 150, 25);
		getContentPane().add(bCreate);
		bCreate.addActionListener(this);

		backButton = new JButton(langManager.getString("backToLogButton"));
		backButton.setBounds(30, 254, 150, 25);
		getContentPane().add(backButton);
		backButton.addActionListener(this);

		JLabel titleLabel = new JLabel(langManager.getString("titleSignOrganizer"));
		titleLabel.setBounds(110 ,12, 200, 16);
		getContentPane().add(titleLabel);

		this.setTitle(langManager.getString("createOrganizer"));
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		Object boton = e.getSource();
		if (boton == bCreate) {
			String email = this.textField_email.getText();
			String password = this.textField_password.getText();
			String name = this.textField_username.getText();
			String organization = this.textField_organization.getText();
			
			if (this.controller.attemptOrganizerSignup(email, password, name, organization)) {
				new EventOrganizerWindow(this.controller);
				this.dispose();
			} else {
				String message = langManager.getString("error1Text");
				JOptionPane.showMessageDialog(this, message, langManager.getString("error2Text"), JOptionPane.ERROR_MESSAGE);
			}
		} else if (boton == backButton) {
			this.dispose();
			new LogInWindow(this.controller);
		}
	}

}