package es.deusto.client.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.*;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.PostInfo;


public class EventWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JButton backButton;

	private EventInfo event;
	private Controller controller;
	private LanguageManager langManager;

	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Font fontBold = new Font("Arial", Font.BOLD, 20);

	public EventWindow(Controller controller, EventInfo e) {
		super();

		this.controller = controller;
		this.langManager = controller.getLanguageManager();
		this.event = e;

		initComponents();

		this.setVisible(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	private void initComponents() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		this.setTitle(this.event.getName()+" event page");

		JPanel topControlPanel, contentPanel;
		topControlPanel = initTopControlPanel();
		contentPanel = initContentPanel();

		cp.add(topControlPanel, BorderLayout.NORTH);
		cp.add(contentPanel, BorderLayout.CENTER);
	
		setListeners();
	}

	private JPanel initTopControlPanel() {
		JPanel topControlPanel = new JPanel(new BorderLayout());

		backButton = new JButton(langManager.getString("closeButton"));
		JPanel languageButtonsPanel = new JPanel(new GridLayout(1, 3));

		try {
			ImageIcon iconEN, iconES, iconIT;
			iconEN = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gb.png").getFile())));
			iconES = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/es.png").getFile())));
			iconIT = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/it.png").getFile())));

			JPanel enPanel, esPanel, itPanel;
			enPanel = new JPanel();
			enPanel.add(new JLabel(iconEN));
			esPanel = new JPanel();
			esPanel.add(new JLabel(iconES));
			itPanel = new JPanel();
			itPanel.add(new JLabel(iconIT));

			enPanel.setBorder(new EmptyBorder(2,2,2,2));
			esPanel.setBorder(new EmptyBorder(2,2,2,2));
			itPanel.setBorder(new EmptyBorder(2,2,2,2));

			enPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!langManager.getLanguage().equals("en")) {
						controller.getLanguageManager().setLanguage("en");
						dispose();
						new EventWindow(controller, event);
					}
				}
			});

			esPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!langManager.getLanguage().equals("es")) {
						controller.getLanguageManager().setLanguage("es");
						dispose();
						new EventWindow(controller, event);
					}
				}
			});

			itPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!langManager.getLanguage().equals("it")) {
						controller.getLanguageManager().setLanguage("it");
						dispose();
						new EventWindow(controller, event);
					}
				}
			});

			languageButtonsPanel.add(enPanel);
			languageButtonsPanel.add(esPanel);
			languageButtonsPanel.add(itPanel);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		JPanel backButtonContainer = new JPanel();
		backButtonContainer.add(backButton);

		JPanel languageButtonsPanelContainer = new JPanel();
		languageButtonsPanelContainer.add(languageButtonsPanel);
		
		topControlPanel.add(backButtonContainer, BorderLayout.WEST);
		topControlPanel.add(languageButtonsPanelContainer, BorderLayout.EAST);

		return topControlPanel;
	}

	private JPanel initContentPanel() {
		JPanel contentPanel = new JPanel(new BorderLayout());
		
		JPanel leftContentPanel = initLeftContentPanel();
		JPanel rightContentPanel = initRightContentPanel();

		JPanel leftContentPanelContainer = new JPanel();
		JPanel rightContentPanelContainer = new JPanel();
		leftContentPanelContainer.add(leftContentPanel);
		rightContentPanelContainer.add(rightContentPanel);

		contentPanel.add(leftContentPanelContainer, BorderLayout.WEST);
		contentPanel.add(rightContentPanelContainer, BorderLayout.CENTER);

		return contentPanel;
	}

	private JPanel initLeftContentPanel() {
		JPanel leftContentPanel = new JPanel(new BorderLayout());

		JLabel eventNameLabel = new JLabel(event.getName());
		eventNameLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		JPanel eventNameLabelContainer = new JPanel();
		eventNameLabelContainer.add(eventNameLabel);

		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel eventDetailsPanel = initEventDetailsPanel();
		JPanel eventDescriptionPanel = new JPanel();

		JLabel eventDescriptionLabel = new JLabel(event.getDescription());
		eventDescriptionPanel.add(eventDescriptionLabel);

		JPanel eventDetailsPanelContainer = new JPanel();
		eventDetailsPanelContainer.add(eventDetailsPanel);

		mainPanel.add(eventDetailsPanelContainer, BorderLayout.NORTH);
		mainPanel.add(eventDescriptionPanel, BorderLayout.CENTER);

		JPanel mainPanelContainer = new JPanel();
		mainPanelContainer.add(mainPanel);

		leftContentPanel.add(eventNameLabelContainer, BorderLayout.NORTH);
		leftContentPanel.add(mainPanelContainer, BorderLayout.CENTER);

		return leftContentPanel;
	}

	private JPanel initEventDetailsPanel() {
		JPanel detailsPanel = new JPanel(new GridLayout(4, 2));
		
		JLabel organizerLabel = new JLabel(langManager.getString("organizer")+": ");
		JLabel topicLabel = new JLabel(langManager.getString("topic") + ": ");
		JLabel locationLabel = new JLabel(langManager.getString("location") + ": ");
		JLabel dateLabel = new JLabel(langManager.getString("date") + ": ");

		JLabel organizerContentLabel = new JLabel(event.getOrganizerEmail());
		JLabel topicContentLabel = new JLabel(event.getTopic().getName());
		JLabel locationContentLabel = new JLabel("London");
		JLabel dateContentLabel = new JLabel("17/05/21");

		JPanel organizerLabelContainer, organizerContentLabelContainer;
		JPanel topicLabelContainer, topicContentLabelContainer;
		JPanel locationLabelContainer, locationContentLabelContainer;
		JPanel dateLabelContainer, dateContentLabelContainer;

		organizerLabelContainer = new JPanel();
		organizerContentLabelContainer = new JPanel();
		locationLabelContainer = new JPanel();
		locationContentLabelContainer = new JPanel();
		dateLabelContainer = new JPanel();
		dateContentLabelContainer = new JPanel();
		topicLabelContainer = new JPanel();
		topicContentLabelContainer = new JPanel();

		organizerLabelContainer.add(organizerLabel);
		organizerContentLabelContainer.add(organizerContentLabel);
		topicLabelContainer.add(topicLabel);
		topicContentLabelContainer.add(topicContentLabel);
		locationLabelContainer.add(locationLabel);
		locationContentLabelContainer.add(locationContentLabel);
		dateLabelContainer.add(dateLabel);
		dateContentLabelContainer.add(dateContentLabel);

		detailsPanel.add(organizerLabelContainer);
		detailsPanel.add(organizerContentLabelContainer);
		detailsPanel.add(topicLabelContainer);
		detailsPanel.add(topicContentLabelContainer);
		detailsPanel.add(locationLabelContainer);
		detailsPanel.add(locationContentLabelContainer);
		detailsPanel.add(dateLabelContainer);
		detailsPanel.add(dateContentLabelContainer);		
		

		return detailsPanel;
	}

	private JPanel initRightContentPanel() {
		JPanel rightPanel = new JPanel(new BorderLayout());

		JPanel topTitlePanel, mainPanel;

		topTitlePanel = new JPanel();
		JLabel titleLabel = new JLabel(langManager.getString("postsTitle") + ": ");
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		topTitlePanel.add(titleLabel);

		mainPanel = initMainPostsPanel();

		rightPanel.add(topTitlePanel, BorderLayout.NORTH);
		rightPanel.add(mainPanel, BorderLayout.CENTER);

		return rightPanel;
	}

	private JPanel initMainPostsPanel() {
		JPanel container = new JPanel();

		JPanel postsPanel = new JPanel();
		postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

		// add here the posts
		for (PostInfo p: event.getPosts()) {
			// generate post panel and add it to postsPanel
			postsPanel.add(new PostPanel(p));
			postsPanel.add(Box.createVerticalGlue());
		}

		postsPanel.add(Box.createVerticalGlue());

		JScrollPane scroll = new JScrollPane(
			postsPanel,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);

		container.add(scroll);

		return container;
	}

	private void setListeners() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if (controller.getUser() != null) {
					new UserEventsWindow(controller);
				} else {
					new OrganizerHome(controller);
				}
			}
		});
	}

	private class PostPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private PostInfo post;

		public PostPanel(PostInfo post) {
			super();
			this.post = post;

			initComponents();
		}

		private void initComponents() {
			setLayout(new BorderLayout());

			JPanel titlePanel = new JPanel();
			JPanel bodyPanel = new JPanel();

			JLabel titleLabel = new JLabel(post.getTitle());
			titlePanel.add(titleLabel);

			JPanel descriptionPanel, datePanel;

			descriptionPanel = new JPanel();
			JLabel descriptionLabel = new JLabel(post.getDescription());
			descriptionPanel.add(descriptionLabel);

			datePanel = new JPanel(new BorderLayout());
			JLabel dateLabel = new JLabel(post.getDate().toString());
			datePanel.add(dateLabel, BorderLayout.EAST);

			bodyPanel.add(descriptionPanel, BorderLayout.CENTER);
			bodyPanel.add(datePanel, BorderLayout.SOUTH);

			add(titlePanel, BorderLayout.NORTH);
			add(bodyPanel, BorderLayout.CENTER);

			setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		}

	}
	

}
