package es.deusto.client.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

import es.deusto.client.controller.Controller;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class LogInWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	public JTextField id, pass;
	public JButton bCreate, blogin;
	private JButton bForgottenPass;
	public JCheckBox cBoxAdmin, cBox;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel labelES, labelGB;

	private Controller controller;

	public LogInWindow(Controller controller) {
		this.controller = controller;

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
		labelES.setBounds(335, 10, icon.getIconWidth(), icon.getIconHeight());
		getContentPane().add(labelES);
		ImageIcon icon1 = null;
		try {
			icon1 = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gb.png").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelGB = new JLabel(icon1);
		labelGB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelGB.setBounds(340+icon.getIconWidth(), 10, icon1.getIconWidth(), icon1.getIconHeight());
		getContentPane().add(labelGB);

		bCreate = new JButton("New User");
		bCreate.setBounds(12, 336, 111, 25);
		getContentPane().add(bCreate);

		blogin = new JButton("LogIn");
		blogin.setBounds(235, 336, 111, 25);
		getContentPane().add(blogin);

		JLabel lblUsuario = new JLabel("Username:");
		lblUsuario.setBounds(22, 95, 79, 16);
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

		labelES.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO change language to spanish
			}
		});

		labelGB.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO change language to english
			}
		});

	}

}
