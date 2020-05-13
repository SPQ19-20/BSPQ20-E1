package es.deusto.client.windows;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.TopicInfo;
import es.deusto.server.data.Topic;

/**
 * This class is the one used to create a window that allows
 * to create a new user.
 */

public class CreateUserWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextField textField_username, textField_email, textField_city, textField_country;
	private JPasswordField textField_password;
	private JButton bCreate, backButton;
	private JCheckBox musicBox, theaterBox, cinemaBox, sportsBox, artBox, cultureBox, foodBox, festivalsBox, moreBox;

	private Controller controller;
	private LanguageManager langManager;
	
	public CreateUserWindow(Controller controller) {
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

		textField_password = new JPasswordField();
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

		JLabel lblCity = new JLabel(langManager.getString("cityLabel"));
		lblCity.setBounds(54, 208, 119, 16);
		getContentPane().add(lblCity);

		textField_city = new JTextField();
		textField_city.setBounds(133, 205, 175, 22);
		getContentPane().add(textField_city);
		textField_city.setColumns(10);

		JLabel lblCountry = new JLabel(langManager.getString("countryLabel"));
		lblCountry.setBounds(54, 247, 119, 16);
		getContentPane().add(lblCountry);

		textField_country = new JTextField();
		textField_country.setBounds(133, 244, 175, 22);
		getContentPane().add(textField_country);
		textField_country.setColumns(10);

		int off = 49;

		JLabel interests = new JLabel(langManager.getString("interestsLabel"));
		interests.setBounds(54, 254+off, 80, 20);
		getContentPane().add(interests);

		musicBox = new JCheckBox(langManager.getString("musicCheck"));
		musicBox.setBounds(54, 274+off, 150, 20);
		getContentPane().add(musicBox);

		cinemaBox = new JCheckBox(langManager.getString("cinemaCheck"));
		cinemaBox.setBounds(54, 294+off, 150, 20);
		getContentPane().add(cinemaBox);

		theaterBox = new JCheckBox(langManager.getString("theaterCheck"));
		theaterBox.setBounds(54, 314+off, 150, 20);
		getContentPane().add(theaterBox);

		sportsBox = new JCheckBox(langManager.getString("sportsCheck"));
		sportsBox.setBounds(54, 334+off, 150, 20);
		getContentPane().add(sportsBox);

		cultureBox = new JCheckBox(langManager.getString("cultureCheck"));
		cultureBox.setBounds(54, 354+off, 150, 20);
		getContentPane().add(cultureBox);

		artBox = new JCheckBox(langManager.getString("artsCheck"));
		artBox.setBounds(54, 374+off, 150, 20);
		getContentPane().add(artBox);

		foodBox = new JCheckBox(langManager.getString("foodCheck"));
		foodBox.setBounds(54, 394+off, 150, 20);
		getContentPane().add(foodBox);

		festivalsBox = new JCheckBox(langManager.getString("festivalsCheck"));
		festivalsBox.setBounds(54, 414+off, 150, 20);
		getContentPane().add(festivalsBox);

		moreBox = new JCheckBox(langManager.getString("moreCheck"));
		moreBox.setBounds(54, 434+off, 150, 20);
		getContentPane().add(moreBox);

		bCreate = new JButton(langManager.getString("createUserButton"));
		bCreate.setBounds(214, 494+off, 150, 25);
		getContentPane().add(bCreate);
		bCreate.addActionListener(this);

		backButton = new JButton(langManager.getString("backToLogButton"));
		backButton.setBounds(30, 494+off, 150, 25);
		getContentPane().add(backButton);
		backButton.addActionListener(this);

		JLabel titleLabel = new JLabel(langManager.getString("titleSignUser"));
		titleLabel.setBounds(110 ,12, 200, 16);
		getContentPane().add(titleLabel);

		this.setTitle(langManager.getString("createUser"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		Object boton = e.getSource();
		if (boton == bCreate) {
			String email = this.textField_email.getText();
			String password = String.valueOf(textField_password.getPassword());
			String name = this.textField_username.getText();
			String city = this.textField_city.getText();
			String country = this.textField_country.getText();
			ArrayList<TopicInfo> interests = new ArrayList<>();
			if (musicBox.isSelected()) interests.add(new TopicInfo("Music"));
			if (theaterBox.isSelected()) interests.add(new TopicInfo("Theater"));
			if (cinemaBox.isSelected()) interests.add(new TopicInfo("Cinema"));
			if (sportsBox.isSelected()) interests.add(new TopicInfo("Sports"));
			if (artBox.isSelected()) interests.add(new TopicInfo("Art"));
			if (cultureBox.isSelected()) interests.add(new TopicInfo("Culture"));
			if (foodBox.isSelected()) interests.add(new TopicInfo("Food"));
			if (festivalsBox.isSelected()) interests.add(new TopicInfo("Festival"));
			if (moreBox.isSelected()) interests.add(new TopicInfo("More"));
			if (this.controller.attemptNormalSignup(email, password, name, city, country, interests)) {
				new UserEventsWindow(this.controller);
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