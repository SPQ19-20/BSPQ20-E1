package windows;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class CreateUserWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton bCreate;
	
	public CreateUserWindow() {
		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		bCreate = new JButton("Create user");
		bCreate.setBounds(234, 337, 111, 25);
		getContentPane().add(bCreate);
		bCreate.addActionListener(this);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(54, 61, 119, 16);
		getContentPane().add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(133, 58, 175, 22);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(54, 110, 119, 16);
		getContentPane().add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setBounds(133, 107, 175, 22);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblMail = new JLabel("E-mail:");
		lblMail.setBounds(74, 159, 119, 16);
		getContentPane().add(lblMail);
		
		textField_2 = new JTextField();
		textField_2.setBounds(133, 156, 175, 22);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(84, 208, 119, 16);
		getContentPane().add(lblCity);
		
		textField_3 = new JTextField();
		textField_3.setBounds(133, 205, 175, 22);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		Object boton = e.getSource();
		if (boton == bCreate) {
			int posY = this.getY();
			int posX = this.getX();
			int altura = this.getHeight();
			int anchura = this.getWidth();
			LogInWindow p = new LogInWindow();
			p.setVisible(true);
			p.setSize(anchura, altura);
			p.setLocation(posX, posY);
			p.setResizable(false);
			this.setVisible(false);
			this.dispose();
		}
	}
		

}
