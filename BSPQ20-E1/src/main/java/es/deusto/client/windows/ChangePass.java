package es.deusto.client.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.*;

/**
 * This class is the one used to create a window to change the
 * user password.
 */

public class ChangePass extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String mail;
	private JTextField txtpass;
	private JTextField txtagain;
	private Controller controller;
	private LanguageManager langManager;

	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Font fontBold = new Font("Arial", Font.BOLD, 20);

	/**
	 * Create the frame.
	 */
	public ChangePass(Controller controller) {
		super();
		this.controller = controller;
		this.langManager = controller.getLanguageManager();
		this.mail = mail;

		initComponents();

		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setResizable(false);

	}

	private void initComponents() {
		contentPane = new JPanel();
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		JPanel topControlPanel, contentPanel;
		topControlPanel = initTopControlPanel();
		contentPanel = initContentPanel();

	}

	private JPanel initTopControlPanel() {
		JPanel topControlPanel = new JPanel(new BorderLayout());

		JMenu languageMenu = new JMenu("Language");
		JMenuItem english = new JMenuItem("EN");
		JMenuItem spanish = new JMenuItem("ES");
		JMenuItem greek = new JMenuItem("EL");
		JMenuItem italian = new JMenuItem("IT");

		try {
			ImageIcon iconEN, iconES, iconIT, iconGR;
			iconEN = new ImageIcon(
					ImageIO.read(new File(getClass().getClassLoader().getResource("images/gb.png").getFile())));
			iconES = new ImageIcon(
					ImageIO.read(new File(getClass().getClassLoader().getResource("images/es.png").getFile())));
			iconIT = new ImageIcon(
					ImageIO.read(new File(getClass().getClassLoader().getResource("images/it.png").getFile())));
			iconGR = new ImageIcon(
					ImageIO.read(new File(getClass().getClassLoader().getResource("images/gr.png").getFile())));
			english.setIcon(iconEN);
			spanish.setIcon(iconES);
			greek.setIcon(iconGR);
			italian.setIcon(iconIT);
		} catch (Exception e) {
		}

		english.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!langManager.getLanguage().equals("en")) {
					controller.getLanguageManager().setLanguage("en");
					dispose();
					new ChangePass(controller);
				}
			}
			});
	
			spanish.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!langManager.getLanguage().equals("es")) {
						controller.getLanguageManager().setLanguage("es");
						dispose();
						new ChangePass(controller);
					}
				}
			});
	
			italian.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!langManager.getLanguage().equals("it")) {
						controller.getLanguageManager().setLanguage("it");
						dispose();
						new ChangePass(controller);
					}
				}
			});
	
			greek.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!langManager.getLanguage().equals("el")) {
						controller.getLanguageManager().setLanguage("el");
						dispose();
						new ChangePass(controller);
					}
				}
			});
	
			languageMenu.add(english);
			languageMenu.add(spanish);
			languageMenu.add(greek);
			languageMenu.add(italian);

			return topControlPanel;
		}

		private JPanel initContentPanel() {

			JPanel contentPanel = new JPanel();
			contentPanel.setLayout(null);


			JLabel lblEnterPassword = new JLabel("Enter Password:");
			lblEnterPassword.setFont(new Font("Arial", Font.PLAIN, 20));
			lblEnterPassword.setBounds(40, 85, 160, 15);
			contentPanel.add(lblEnterPassword);
			
			txtpass = new JTextField();
			txtpass.setBounds(280, 85, 100, 15);
			contentPanel.add(txtpass);
			txtpass.setColumns(10);
			
			JLabel lblEnterPasswordAgain = new JLabel("Enter Password Again:");
			lblEnterPasswordAgain.setFont(new Font("Arial", Font.PLAIN, 20));
			lblEnterPasswordAgain.setBounds(40, 145, 160, 15);
			contentPanel.add(lblEnterPasswordAgain);
			
			txtagain = new JTextField();
			txtagain.setBounds(280, 145, 100, 15);
			contentPanel.add(txtagain);
			txtagain.setColumns(10);
			//txtagain.setVisible(true);
			
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
			btnSave.setBounds(155, 220, 85, 20);
			contentPanel.add(btnSave);
			
			return contentPanel;
	}

}
