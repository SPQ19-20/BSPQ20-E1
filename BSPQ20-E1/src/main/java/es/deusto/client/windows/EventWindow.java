package es.deusto.client.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
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
		setSize(new Dimension(1000, 650));
		setResizable(false);
		// this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	private void initComponents() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		if (controller.getUser() == null && controller.getOrganize() != null) {
			initMenuBar();
		}

		this.setTitle(langManager.getString("eventPage"));

		JPanel topControlPanel, contentPanel;
		topControlPanel = initTopControlPanel();
		contentPanel = initContentPanel();

		cp.add(topControlPanel, BorderLayout.NORTH);
		cp.add(contentPanel, BorderLayout.CENTER);
	
		setListeners();
	}

	private void initMenuBar() {
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);

		JMenu postsMenu = new JMenu(langManager.getString("Posts"));
		JMenuItem createPost = new JMenuItem(langManager.getString("newPost"));

		createPost.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PostCreationWindow(controller, event);
			}
		});

		postsMenu.add(createPost);
		bar.add(postsMenu);
	}

	private JPanel initTopControlPanel() {
		JPanel topControlPanel = new JPanel(new BorderLayout());

		backButton = new JButton(langManager.getString("closeButton"));
		
		JPanel backButtonContainer = new JPanel(new BorderLayout());
		backButtonContainer.setBorder(new EmptyBorder(20,20,10,10));
		backButtonContainer.add(backButton, BorderLayout.WEST);

		JLabel eventNameLabel = new JLabel(event.getName());
		eventNameLabel.setFont(new Font("Arial", Font.BOLD, 25));

		JPanel titleContainer = new JPanel(new BorderLayout());
		titleContainer.setBorder(new EmptyBorder(20,20,10,10));
		titleContainer.add(eventNameLabel, BorderLayout.WEST);
		
		topControlPanel.add(backButtonContainer, BorderLayout.NORTH);
		topControlPanel.add(titleContainer, BorderLayout.CENTER);

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

		JLabel eventNameLabel = new JLabel(langManager.getString("BasicInformation"));
		eventNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
		JPanel eventNameLabelContainer = new JPanel(new BorderLayout());
		eventNameLabelContainer.setBorder(new EmptyBorder(10,15,10,10));
		eventNameLabelContainer.add(eventNameLabel, BorderLayout.WEST);

		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel eventDetailsPanel = initEventDetailsPanel();
		JPanel eventDescriptionPanel = new JPanel(new BorderLayout());
		
		JLabel eventDescriptionLabel = new JLabel(event.getDescription());
		eventDescriptionPanel.setBorder(new EmptyBorder(25,30,25,25));
		eventDescriptionPanel.add(eventDescriptionLabel, BorderLayout.WEST);

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
		int rows = (controller.getUser() == null && controller.getOrganize() != null) ? 5 : 4;
		JPanel detailsPanel = new JPanel(new GridLayout(rows, 2));
		
		Font f = new Font("Arial", Font.PLAIN, 15);
		Font fb = new Font("Arial", Font.BOLD, 15);

		JLabel interestedLabel = new JLabel(langManager.getString("interestedUsers"));
		JLabel organizerLabel = new JLabel(langManager.getString("organizer")+": ");
		JLabel topicLabel = new JLabel(langManager.getString("topic") + ": ");
		JLabel locationLabel = new JLabel(langManager.getString("location") + ": ");
		JLabel dateLabel = new JLabel(langManager.getString("date") + ": ");

		interestedLabel.setFont(fb);
		organizerLabel.setFont(fb);
		topicLabel.setFont(fb);
		locationLabel.setFont(fb);
		dateLabel.setFont(fb);

		JLabel interestedContentLabel = new JLabel(event.getInterested()+" "+ langManager.getString("users")); //number of interested users
		JLabel organizerContentLabel = new JLabel(event.getOrganizerEmail());
		JLabel topicContentLabel = new JLabel(event.getTopic().getName());
		JLabel locationContentLabel = new JLabel(event.getCity() + ", " + event.getCountry());
		JLabel dateContentLabel = new JLabel(event.getDate().toLocaleString());

		interestedContentLabel.setFont(f);
		organizerContentLabel.setFont(f);
		topicContentLabel.setFont(f);
		locationContentLabel.setFont(f);
		dateContentLabel.setFont(f);

		JPanel interestedLabelContainer, interestedContentLabelContainer;
		JPanel organizerLabelContainer, organizerContentLabelContainer;
		JPanel topicLabelContainer, topicContentLabelContainer;
		JPanel locationLabelContainer, locationContentLabelContainer;
		JPanel dateLabelContainer, dateContentLabelContainer;

		interestedLabelContainer = new JPanel(new BorderLayout());
		interestedContentLabelContainer = new JPanel(new BorderLayout());
		organizerLabelContainer = new JPanel(new BorderLayout());
		organizerContentLabelContainer = new JPanel(new BorderLayout());
		locationLabelContainer = new JPanel(new BorderLayout());
		locationContentLabelContainer = new JPanel(new BorderLayout());
		dateLabelContainer = new JPanel(new BorderLayout());
		dateContentLabelContainer = new JPanel(new BorderLayout());
		topicLabelContainer = new JPanel(new BorderLayout());
		topicContentLabelContainer = new JPanel(new BorderLayout());

		int t = 15;
		
		interestedLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		interestedContentLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		organizerLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		organizerContentLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		locationLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		locationContentLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		dateLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		dateContentLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		topicLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		topicContentLabelContainer.setBorder(new EmptyBorder(t,t*2,t,0));
		
		interestedLabelContainer.add(interestedLabel, BorderLayout.WEST);
		interestedContentLabelContainer.add(interestedContentLabel, BorderLayout.WEST);
		organizerLabelContainer.add(organizerLabel, BorderLayout.WEST);
		organizerContentLabelContainer.add(organizerContentLabel, BorderLayout.WEST);
		topicLabelContainer.add(topicLabel, BorderLayout.WEST);
		topicContentLabelContainer.add(topicContentLabel, BorderLayout.WEST);
		locationLabelContainer.add(locationLabel, BorderLayout.WEST);
		locationContentLabelContainer.add(locationContentLabel, BorderLayout.WEST);
		dateLabelContainer.add(dateLabel, BorderLayout.WEST);
		dateContentLabelContainer.add(dateContentLabel, BorderLayout.WEST);

		if (controller.getUser() == null && controller.getOrganize() != null) {
			detailsPanel.add(interestedLabelContainer);
			detailsPanel.add(interestedContentLabelContainer);
		}

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
		JPanel topTitlePanel;

		topTitlePanel = new JPanel();
		JLabel titleLabel = new JLabel(langManager.getString("postsTitle") + ": ");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		topTitlePanel.add(titleLabel);

		rightPanel.add(topTitlePanel, BorderLayout.NORTH);
		rightPanel.add(initMainPostsPanel(), BorderLayout.CENTER);

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
			postsPanel.add(Box.createHorizontalStrut(10));
		}

		JScrollPane scroll = new JScrollPane(
			postsPanel,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);

		scroll.setPreferredSize(new Dimension(500, 400));

		container.add(scroll);
		return container;
	}

	private void setListeners() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
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
			setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));


			JPanel topPanel, bottomPanel;
			topPanel = new JPanel();
			bottomPanel = new JPanel(new BorderLayout());

			// top panel
			topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

			JPanel titlePanel, datePanel;
			titlePanel = new JPanel(new BorderLayout());
			datePanel = new JPanel(new BorderLayout());

			JLabel titleLabel = new JLabel(post.getTitle());
			titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
			titlePanel.add(titleLabel, BorderLayout.WEST);

			JLabel dateLabel = new JLabel(post.getDate()+"");
			dateLabel.setFont(new Font("Arial", Font.ITALIC, 12));
			datePanel.add(dateLabel, BorderLayout.WEST);

			topPanel.add(titlePanel);
			topPanel.add(Box.createHorizontalStrut(20));
			topPanel.add(datePanel);

			// bottom panel
			JLabel descLabel = new JLabel(post.getDescription());
			descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
			bottomPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
			bottomPanel.add(descLabel, BorderLayout.WEST);

			add(topPanel, BorderLayout.CENTER);
			add(bottomPanel, BorderLayout.SOUTH);

			setBorder(new CompoundBorder(new EmptyBorder(10,20,10,20), BorderFactory.createMatteBorder(0,0,2,0,Color.lightGray)));
		}

	}
	

}
