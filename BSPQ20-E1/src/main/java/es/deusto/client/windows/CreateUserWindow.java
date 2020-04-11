package es.deusto.client.windows;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import es.deusto.client.controller.Controller;

public class CreateUserWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextField textField_username, textField_password, textField_email, textField_city;
	private JButton bCreate, backButton;
	private JCheckBox musicBox, theaterBox, cinemaBox, sportsBox, artBox, cultureBox, foodBox, festivalsBox, moreBox;

	private Controller controller;
	
	public CreateUserWindow(Controller controller) {
		this.controller = controller;

		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JLabel lblUsername = new JLabel(LogInWindow.localization.getStringById("userLabel"));
		lblUsername.setBounds(54, 61, 119, 16);
		getContentPane().add(lblUsername);

		textField_username = new JTextField();
		textField_username.setBounds(133, 58, 175, 22);
		getContentPane().add(textField_username);
		textField_username.setColumns(10);

		JLabel lblPassword = new JLabel(LogInWindow.localization.getStringById("passwordLabel"));
		lblPassword.setBounds(54, 110, 119, 16);
		getContentPane().add(lblPassword);

		textField_password = new JTextField();
		textField_password.setBounds(133, 107, 175, 22);
		getContentPane().add(textField_password);
		textField_password.setColumns(10);

		JLabel lblMail = new JLabel(LogInWindow.localization.getStringById("emailLabel"));
		lblMail.setBounds(54, 159, 119, 16);
		getContentPane().add(lblMail);

		textField_email = new JTextField();
		textField_email.setBounds(133, 156, 175, 22);
		getContentPane().add(textField_email);
		textField_email.setColumns(10);

		JLabel lblCity = new JLabel(LogInWindow.localization.getStringById("cityLabel"));
		lblCity.setBounds(54, 208, 119, 16);
		getContentPane().add(lblCity);

		textField_city = new JTextField();
		textField_city.setBounds(133, 205, 175, 22);
		getContentPane().add(textField_city);
		textField_city.setColumns(10);

		JLabel interests = new JLabel(LogInWindow.localization.getStringById("interestsLabel"));
		interests.setBounds(54, 254, 80, 20);
		getContentPane().add(interests);

		musicBox = new JCheckBox(LogInWindow.localization.getStringById("musicCheck"));
		musicBox.setBounds(54, 274, 150, 20);
		getContentPane().add(musicBox);

		cinemaBox = new JCheckBox(LogInWindow.localization.getStringById("cinemaCheck"));
		cinemaBox.setBounds(54, 294, 150, 20);
		getContentPane().add(cinemaBox);

		theaterBox = new JCheckBox(LogInWindow.localization.getStringById("theaterCheck"));
		theaterBox.setBounds(54, 314, 150, 20);
		getContentPane().add(theaterBox);

		sportsBox = new JCheckBox(LogInWindow.localization.getStringById("sportsCheck"));
		sportsBox.setBounds(54, 334, 150, 20);
		getContentPane().add(sportsBox);

		cultureBox = new JCheckBox(LogInWindow.localization.getStringById("cultureCheck"));
		cultureBox.setBounds(54, 354, 150, 20);
		getContentPane().add(cultureBox);

		artBox = new JCheckBox(LogInWindow.localization.getStringById("artsCheck"));
		artBox.setBounds(54, 374, 150, 20);
		getContentPane().add(artBox);

		foodBox = new JCheckBox(LogInWindow.localization.getStringById("foodCheck"));
		foodBox.setBounds(54, 394, 150, 20);
		getContentPane().add(foodBox);

		festivalsBox = new JCheckBox(LogInWindow.localization.getStringById("festivalsCheck"));
		festivalsBox.setBounds(54, 414, 150, 20);
		getContentPane().add(festivalsBox);

		moreBox = new JCheckBox(LogInWindow.localization.getStringById("moreCheck"));
		moreBox.setBounds(54, 434, 150, 20);
		getContentPane().add(moreBox);

		bCreate = new JButton(LogInWindow.localization.getStringById("createUserButton"));
		bCreate.setBounds(214, 494, 150, 25);
		getContentPane().add(bCreate);
		bCreate.addActionListener(this);

		backButton = new JButton(LogInWindow.localization.getStringById("backToLogButton"));
		backButton.setBounds(30, 494, 150, 25);
		getContentPane().add(backButton);
		backButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		Object boton = e.getSource();
		if (boton == bCreate) {
			String email = this.textField_email.getText();
			String password = this.textField_password.getText();
			String name = this.textField_username.getText();
			String city = this.textField_city.getText();
			String interests = "";
			if (musicBox.isSelected()) interests += "Music ";
			if (theaterBox.isSelected()) interests += "Theater ";
			if (cinemaBox.isSelected()) interests += "Cinema ";
			if (sportsBox.isSelected()) interests += "Sports ";
			if (artBox.isSelected()) interests += "Arts ";
			if (cultureBox.isSelected()) interests += "Culture ";
			if (foodBox.isSelected()) interests += "Food ";
			if (festivalsBox.isSelected()) interests += "Festival ";
			if (moreBox.isSelected()) interests += "More ";
			if (this.controller.attemptNormalSignup(email, password, name, city, interests)) {
				new UserEventsWindow(this.controller);
				this.dispose();
			} else {
				String message = LogInWindow.localization.getStringById("error1Text");
				JOptionPane.showMessageDialog(this, message, LogInWindow.localization.getStringById("error2Text"), JOptionPane.ERROR_MESSAGE);
			}
		} else if (boton == backButton) {
			this.dispose();
			new LogInWindow(this.controller, LogInWindow.localization.getLanguage());
		}
	}

}