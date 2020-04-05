package es.deusto.client.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.*;
import es.deusto.serialization.EventInfo;


public class EventWindow extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	public JButton bBack;
	public JButton bProfile;
	public JLabel title;
	public JLabel description;
	
	private Controller controller;

	public EventWindow(Controller controller, EventInfo e) {
		
		this.controller = controller;

		getContentPane().setLayout(null);
		setTitle(e.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		bBack = new JButton("");
		bBack.setBounds(54, 61, 119, 16);
		bProfile.setContentAreaFilled(false);
		bProfile.setBorderPainted(false);
		bBack.setIcon(new ImageIcon("src\\images\\Button Previous.png"));
		getContentPane().add(bBack);
		bBack.addActionListener(this);
		
		bProfile = new JButton("Profile");
		bProfile.setBounds(54, 61, 119, 16);
		bProfile.setContentAreaFilled(false);
		bProfile.setBorderPainted(false);
		getContentPane().add(bProfile);
		bProfile.addActionListener((ActionListener) this);
		
		JLabel jTitle = new JLabel(e.getName());
		jTitle.setBounds(74, 159, 119, 16);
		getContentPane().add(jTitle);
		
		JLabel jDescription = new JLabel(e.getDescription());
		jDescription.setBounds(74, 159, 119, 16);
		getContentPane().add(jDescription);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton == bBack) {
			int posY = this.getY();
			int posX = this.getX();
			int height = this.getHeight();
			int width = this.getWidth();
			LogInWindow l = new LogInWindow(this.controller);
			l.setVisible(true);
			l.setSize(width, height);
			l.setLocation(posX, posY);
			l.setResizable(false);
			this.setVisible(false);
			this.dispose();
		} else if (boton == bProfile) {
			int posY = this.getY();
			int posX = this.getX();
			int height = this.getHeight();
			int width = this.getWidth();
			Profile p = new Profile(this.controller);
			p.setVisible(true);
			p.setSize(width, height);
			p.setLocation(posX, posY);
			p.setResizable(false);
			this.setVisible(false);
			this.dispose();
		}

		
	}
}
