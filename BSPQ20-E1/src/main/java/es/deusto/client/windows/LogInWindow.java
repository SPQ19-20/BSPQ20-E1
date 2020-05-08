package es.deusto.client.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.CreateOrganizerWindow;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class LogInWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	public JTextField id, pass;
	public JButton bCreate, blogin;
	private JButton bForgottenPass;
	public JCheckBox cBoxAdmin, cBoxOrganizer; //do we use this?? cBox
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel labelES, labelGB, labelIT, labelGR;
	private Controller controller;
	private LanguageManager langManager;

	public LogInWindow(Controller controller) {
		this.controller = controller;
		this.langManager = this.controller.getLanguageManager();

		getContentPane().setLayout(null);
		setTitle("LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/es.png").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelES = new JLabel(icon);
		labelES.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelES.setBounds(300, 10, icon.getIconWidth(), icon.getIconHeight());
		getContentPane().add(labelES);

		ImageIcon icon1 = null;
		try {
			icon1 = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gb.png").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelGB = new JLabel(icon1);
		labelGB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelGB.setBounds(305+icon.getIconWidth(), 10, icon1.getIconWidth(), icon1.getIconHeight());
		getContentPane().add(labelGB);

		ImageIcon icon2 = null;
		try {
			icon2 = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/it.png").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelIT = new JLabel(icon2);
		labelIT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelIT.setBounds(310+icon.getIconWidth()+icon1.getIconWidth(), 10, icon2.getIconWidth(), icon2.getIconHeight());
		getContentPane().add(labelIT);

		ImageIcon icon3 = null;
		try {
			icon3 = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gr.png").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelGR = new JLabel(icon3);
		labelGR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelGR.setBounds(315+icon.getIconWidth()+icon1.getIconWidth()+icon2.getIconWidth(), 10, icon3.getIconWidth(), icon3.getIconHeight());
		getContentPane().add(labelGR);

		bCreate = new JButton(langManager.getString("newUserButton"));
		bCreate.setBounds(120, 320, 125, 25);
		getContentPane().add(bCreate);

		blogin = new JButton(langManager.getString("loginButton"));
		blogin.setBounds(120, 220, 125, 25);
		getContentPane().add(blogin);

		cBoxOrganizer = new JCheckBox(langManager.getString("checkboxOrganizer")); //by default this checkbox is not enabled
		cBoxOrganizer.setBounds(110, 270, 150, 25);
		getContentPane().add(cBoxOrganizer);
		
		JLabel lblUsuario = new JLabel(langManager.getString("emailLabel"));
		lblUsuario.setBounds(22, 95, 79, 16);
		getContentPane().add(lblUsuario);

		textField = new JTextField("");
		textField.setBounds(97, 92, 160, 22);
		getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField("");
		passwordField.setBounds(97, 133, 160, 22);
		getContentPane().add(passwordField);

		JLabel lblContrasea = new JLabel(langManager.getString("passwordLabel"));
		lblContrasea.setBounds(22, 136, 101, 16);
		getContentPane().add(lblContrasea);

		bForgottenPass = new JButton(langManager.getString("passRecButton"));
		bForgottenPass.setBounds(80, 365, 200, 25);
		getContentPane().add(bForgottenPass);

		setListeners();

		this.setVisible(true);
		this.setSize(400, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
	}

	private void setListeners() {
		bCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int posY = getY();
				int posX = getX();
				if(cBoxOrganizer.isSelected()){
					CreateOrganizerWindow d = new CreateOrganizerWindow(controller);
					d.setVisible(true);
					d.setSize(400, 600);
					d.setLocation(posX, posY);
					d.setResizable(false);
					setVisible(false);
					dispose();
				}else{
					CreateUserWindow p = new CreateUserWindow(controller);
					p.setVisible(true);
					p.setSize(400, 600);
					p.setLocation(posX, posY);
					p.setResizable(false);
					setVisible(false);
					dispose();
				}
			}
		});

		blogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email, password;
				email = textField.getText();
				password = String.valueOf(passwordField.getPassword());

				//new method
				if (cBoxOrganizer.isSelected()) { //if is selected then we need to log in a Organizer
					if (controller.attemptNormalLoginOrganizer(email, password)) {
						// organizer login success
						new OrganizerHome(controller); //now we can display the Organizer Event List.
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, langManager.getString("wrongCredentials"), langManager.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				} else {
					if (controller.attemptNormalLogin(email, password)) {
						// user login success
						new UserEventsWindow(controller);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, langManager.getString("wrongCredentials"), langManager.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
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

		labelES.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!langManager.getLanguage().equals("es")) {
					dispose();
					controller.getLanguageManager().setLanguage("es");
					LogInWindow login = new LogInWindow(controller);
					login.setVisible(true);
					login.setSize(400, 500);
					login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});

		labelGB.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!langManager.getLanguage().equals("en")) {
					dispose();
					controller.getLanguageManager().setLanguage("en");
					LogInWindow login = new LogInWindow(controller);
					login.setVisible(true);
					login.setSize(400, 500);
					login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});

		labelIT.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!langManager.getLanguage().equals("it")) {
					dispose();
					controller.getLanguageManager().setLanguage("it");
					LogInWindow login = new LogInWindow(controller);
					login.setVisible(true);
					login.setSize(400, 500);
					login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});

		labelGR.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!langManager.getLanguage().equals("el")) {
					dispose();
					controller.getLanguageManager().setLanguage("el");
					LogInWindow login = new LogInWindow(controller);
					login.setVisible(true);
					login.setSize(400, 500);
					login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});

	}

}
