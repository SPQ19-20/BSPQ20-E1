package es.deusto.client.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import es.deusto.client.controller.Controller;

public class CodeVerification extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String code;
	private String mail;
	private JTextField txtcode;
	
	private Controller controller;
	private LanguageManager langManager;

	/**
	 * Create the frame.
	 */
	public CodeVerification(String code, Controller controller) {
		this.setVisible(true);
		this.controller = controller;
		this.langManager = controller.getLanguageManager();

		this.code = code;
		this.mail = mail;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPleaseEnterThe = new JLabel(langManager.getString("codeText"));
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseEnterThe.setBounds(61, 44, 351, 17);
		contentPane.add(lblPleaseEnterThe);
		
		JButton btnBack = new JButton(langManager.getString("closeButton"));
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ForgottenPassword(controller);
				dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnBack.setBounds(43, 218, 85, 21);
		contentPane.add(btnBack);
		
		JButton btnVerify = new JButton(langManager.getString("verifyText"));
		btnVerify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtcode.getText().equals(code)) {
					new ChangePass(controller);
				}
			}
		});
		btnVerify.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnVerify.setBounds(314, 218, 85, 21);
		contentPane.add(btnVerify);
		
		txtcode = new JTextField();
		txtcode.setBounds(171, 106, 96, 19);
		contentPane.add(txtcode);
		txtcode.setColumns(10);
	}
}
