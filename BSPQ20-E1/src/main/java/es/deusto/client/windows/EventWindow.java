package es.deusto.client.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.*;
import es.deusto.serialization.EventInfo;


public class EventWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JButton backButton;

	private EventInfo event;
	private Controller controller;

	public EventWindow(Controller controller, EventInfo e) {
		super();

		this.controller = controller;
		this.event = e;

		initComponents();

		this.setVisible(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	private void initComponents() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		this.setTitle(this.event.getName()+" event page");

		Font font = new Font("Arial", Font.PLAIN, 20);
		Font fontBold = new Font("Arial", Font.BOLD, 20);

		JPanel titlePanel, mainPanel;

		titlePanel = new JPanel(new BorderLayout());
		mainPanel = new JPanel(new GridLayout(1, 2));

		JLabel titleLabel = new JLabel("Event: "+this.event.getName());
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));

		JPanel title_panel = new JPanel();
		title_panel.add(titleLabel);
		
		titlePanel.add(title_panel, BorderLayout.CENTER);

		backButton = new JButton(LogInWindow.localization.getStringById("closeButton"));
		JPanel backBtn_panel = new JPanel(new BorderLayout());
		backBtn_panel.add(backButton, BorderLayout.EAST);

		titlePanel.add(backBtn_panel, BorderLayout.EAST);

		JPanel mainLeftPanel, mainRightPanel;
		mainLeftPanel = new JPanel(new BorderLayout());
		mainRightPanel = new JPanel(new GridLayout(2, 1));

		JLabel descriptionLabel_title = new JLabel(LogInWindow.localization.getStringById("descriptionText"));
		JLabel descriptionLabel = new JLabel(this.event.getDescription());

		descriptionLabel_title.setFont(fontBold);
		descriptionLabel.setFont(font);

		JPanel descTitle_panel = new JPanel();
		descTitle_panel.add(descriptionLabel_title);

		JPanel desc_panel = new JPanel();
		desc_panel.add(descriptionLabel);

		mainLeftPanel.add(descTitle_panel, BorderLayout.NORTH);
		mainLeftPanel.add(desc_panel);

		mainLeftPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		JPanel mainRightTopPanel, mainRightBottomPanel;
		mainRightTopPanel = new JPanel(new BorderLayout());
		mainRightBottomPanel = new JPanel(new BorderLayout());

		JLabel topicLabel_title = new JLabel(LogInWindow.localization.getStringById("categoryLabel"));
		JLabel topicLabel = new JLabel(this.event.getTopic().getName());

		topicLabel_title.setFont(fontBold);
		topicLabel.setFont(font);

		JPanel topicTitle_panel = new JPanel();
		topicTitle_panel.add(topicLabel_title);

		JPanel topic_panel = new JPanel();
		topic_panel.add(topicLabel);

		mainRightTopPanel.add(topicTitle_panel, BorderLayout.NORTH);
		mainRightTopPanel.add(topic_panel, BorderLayout.CENTER);

		mainRightTopPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		JLabel organizerLabel_title, organizerLabel;
		organizerLabel_title = new JLabel(LogInWindow.localization.getStringById("organizerLabel"));
		organizerLabel = new JLabel(this.event.getOrganizer());

		organizerLabel_title.setFont(fontBold);
		organizerLabel.setFont(font);

		JPanel organizerTitle_panel = new JPanel();
		organizerTitle_panel.add(organizerLabel_title);
		
		JPanel organizer_panel = new JPanel();
		organizer_panel.add(organizerLabel);

		mainRightBottomPanel.add(organizerTitle_panel, BorderLayout.NORTH);
		mainRightBottomPanel.add(organizer_panel, BorderLayout.CENTER);

		mainRightBottomPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		mainRightPanel.add(mainRightTopPanel);
		mainRightPanel.add(mainRightBottomPanel);

		mainPanel.add(mainLeftPanel);
		mainPanel.add(mainRightPanel);

		cp.add(titlePanel, BorderLayout.NORTH);
		cp.add(mainPanel, BorderLayout.CENTER);
	
		setListeners();
	}

	private void setListeners() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new UserEventsWindow(controller);
			}
		});
	}

}
