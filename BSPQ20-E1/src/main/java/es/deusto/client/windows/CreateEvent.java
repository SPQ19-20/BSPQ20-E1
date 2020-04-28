package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CreateEvent extends JFrame implements ActionListener{
		
	private static final long serialVersionUID = 1L;
	public JButton bCreate;
	public JTextField t1;
	public JTextField t2;
	public JTextField t3;
	public JTextField t4;
	public JTextField t5;
	public JTextField t6;
	
	private Controller controller;
	private LanguageManager langManager;
		
	public CreateEvent(Controller controller) {
	
		this.controller = controller;
		this.langManager = controller.getLanguageManager();
		
		getContentPane().setLayout(null);
		setTitle("Create Event");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		bCreate = new JButton("Create Event");
		bCreate.setBounds(235, 336, 120, 28);
		getContentPane().add(bCreate);
		bCreate.addActionListener(this);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(63, 65, 79, 16);
		getContentPane().add(lblTitle);

		t1 = new JTextField();
		t1.setBounds(97, 62, 160, 22);
		getContentPane().add(t1);
		t1.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(22, 106, 101, 16);
		getContentPane().add(lblDescription);
		
		t2 = new JTextField();
		t2.setBounds(97, 103, 160, 22);
		getContentPane().add(t2);
		
		JLabel lblTopic = new JLabel("Topic:");
		lblTopic.setBounds(56, 147, 101, 16);
		getContentPane().add(lblTopic);
		
		t3 = new JTextField();
		t3.setBounds(97, 144, 160, 22);
		getContentPane().add(t3);
		
		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setBounds(55, 188, 101, 16);
		getContentPane().add(lblPlace);
		
		t4 = new JTextField();
		t4.setBounds(97, 185, 160, 22);
		getContentPane().add(t4);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(65, 229, 101, 16);
		getContentPane().add(lblCity);
		
		t5 = new JTextField();
		t5.setBounds(97, 226, 160, 22);
		getContentPane().add(t5);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(60, 270, 101, 16);
		getContentPane().add(lblDate);
		
		t6 = new JTextField();
		t6.setBounds(97, 267, 160, 22);
		getContentPane().add(t6);
			
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
			EventInfo newEvent = new EventInfo;
			newEvent.name=title;
			newEvent.description = description;
			newEvent.topic = topic;
			controller.createEvent(newEvent);
		}	
	}
}

