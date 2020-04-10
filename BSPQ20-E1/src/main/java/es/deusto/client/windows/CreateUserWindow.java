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

		bCreate = new JButton("Create user");
		bCreate.setBounds(234, 494, 111, 25);
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

		JLabel interests = new JLabel("Interests:");
		interests.setBounds(54, 254, 80, 20);
		getContentPane().add(interests);

		musicBox = new JCheckBox("Music");
		musicBox.setBounds(54, 274, 150, 20);
		getContentPane().add(musicBox);

		cinemaBox = new JCheckBox("Cinema");
		cinemaBox.setBounds(54, 294, 150, 20);
		getContentPane().add(cinemaBox);

		theaterBox = new JCheckBox("Theater");
		theaterBox.setBounds(54, 314, 150, 20);
		getContentPane().add(theaterBox);

		sportsBox = new JCheckBox("Sports");
		sportsBox.setBounds(54, 334, 150, 20);
		getContentPane().add(sportsBox);

		cultureBox = new JCheckBox("Culture");
		cultureBox.setBounds(54, 354, 150, 20);
		getContentPane().add(cultureBox);

		artBox = new JCheckBox("Arts");
		artBox.setBounds(54, 374, 150, 20);
		getContentPane().add(artBox);

		foodBox = new JCheckBox("Food");
		foodBox.setBounds(54, 394, 150, 20);
		getContentPane().add(foodBox);

		festivalsBox = new JCheckBox("Festivals");
		festivalsBox.setBounds(54, 414, 150, 20);
		getContentPane().add(festivalsBox);

		moreBox = new JCheckBox("More..");
		moreBox.setBounds(54, 434, 150, 20);
		getContentPane().add(moreBox);

		backButton = new JButton("Back to login");
		backButton.setBounds(30, 494, 111, 25);
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
				String message = "We could not create this account, maybe this email is already in use.";
				JOptionPane.showMessageDialog(this, message, "Could not create account", JOptionPane.ERROR_MESSAGE);
			}
		} else if (boton == backButton) {
			this.dispose();
			new LogInWindow(this.controller);
		}
	}

}