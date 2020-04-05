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
		
		titlePanel.add(titleLabel, BorderLayout.CENTER);

		JPanel mainLeftPanel, mainRightPanel;
		mainLeftPanel = new JPanel(new BorderLayout());
		mainRightPanel = new JPanel(new GridLayout(2, 1));

		JLabel descriptionLabel_title = new JLabel("Event description:");
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

		JLabel channelLabel_title = new JLabel("Channel: ");
		JLabel channelLabel = new JLabel(this.event.getChannel().getName());

		channelLabel_title.setFont(fontBold);
		channelLabel.setFont(font);

		JPanel channelTitle_panel = new JPanel();
		channelTitle_panel.add(channelLabel_title);

		JPanel channel_panel = new JPanel();
		channel_panel.add(channelLabel);

		mainRightTopPanel.add(channelTitle_panel, BorderLayout.NORTH);
		mainRightTopPanel.add(channel_panel, BorderLayout.CENTER);

		mainRightTopPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		JLabel organizerLabel_title, organizerLabel;
		organizerLabel_title = new JLabel("Event organizer: ");
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
	}

}
