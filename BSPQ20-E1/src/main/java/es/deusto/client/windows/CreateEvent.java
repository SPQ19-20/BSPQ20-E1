package es.deusto.client.windows;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.TopicInfo;

/**
 * This class is the one used to create a window that it allows the
 * user to create a new event.
 */

public class CreateEvent extends JFrame implements ActionListener{
		
	private static final long serialVersionUID = 1L;
	private JButton bCreate;
	private JTextField t1;
	private JTextField t2;
	private JTextField t3;
	private JTextField t4;
	private JTextField t6;
	
	private Controller controller;
	private LanguageManager langManager;
		
	public CreateEvent(Controller controller) {
	
		this.controller = controller;
		this.langManager = controller.getLanguageManager();
		
		getContentPane().setLayout(null);
		setTitle(langManager.getString("createEvent"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(400, 500));
			
		bCreate = new JButton(langManager.getString("createEvent"));
		bCreate.setBounds(235, 336, 120, 28);
		getContentPane().add(bCreate);
		bCreate.addActionListener(this);
		
		JLabel lblTitle = new JLabel(langManager.getString("title") + ": ");
		lblTitle.setBounds(63, 65, 79, 16);
		getContentPane().add(lblTitle);

		int offset = 50;
		
		t1 = new JTextField();
		t1.setBounds(97+offset, 62, 160, 22);
		getContentPane().add(t1);
		t1.setColumns(10);
		
		JLabel lblDescription = new JLabel(langManager.getString("description") + ":");
		lblDescription.setBounds(22, 106, 101, 16);
		getContentPane().add(lblDescription);
		
		t2 = new JTextField();
		t2.setBounds(97+offset, 103, 160, 22);
		getContentPane().add(t2);
		
		JLabel lblTopic = new JLabel(langManager.getString("topic") + ":");
		lblTopic.setBounds(56, 147, 101, 16);
		getContentPane().add(lblTopic);
		
		t3 = new JTextField();
		t3.setBounds(97+offset, 144, 160, 22);
		getContentPane().add(t3);
		
		JLabel lblPlace = new JLabel(langManager.getString("location") + ":");
		lblPlace.setBounds(55, 188, 101, 16);
		getContentPane().add(lblPlace);
		
		t4 = new JTextField();
		t4.setBounds(97+offset, 185, 160, 22);
		getContentPane().add(t4);
		
		JLabel lblDate = new JLabel(langManager.getString("date") + ":");
		lblDate.setBounds(65, 229, 101, 16);
		getContentPane().add(lblDate);
		
		t6 = new JTextField();
		t6.setBounds(97+offset, 226, 160, 22);
		getContentPane().add(t6);
			
		setVisible(true);
		setResizable(false);

		setListeners();
	}

	private void setListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				new OrganizerHome(controller);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton == bCreate) {
			String title = this.t1.getText();
			String description = this.t2.getText();
			String topic = this.t3.getText();
			//String place = this.t4.getText();
			//String city = this.t5.getText();
			//String date = this.t6.getText();
			EventInfo newEvent = new EventInfo();
			newEvent.setName(title);
			newEvent.setDescription(description);
			newEvent.setTopic(new TopicInfo(topic));
			newEvent.setOrganizerEmail(controller.getOrganize().getEmail());
			newEvent.setPosts(new ArrayList<>());
			controller.createEvent(newEvent);

			dispose();
			new OrganizerHome(controller);
		}	
	}
}

