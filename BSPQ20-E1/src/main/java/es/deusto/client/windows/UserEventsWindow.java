package es.deusto.client.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.EventInfo;

public class UserEventsWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private Container cp;
    private JPanel titlePanel, mainPanel;
    private JButton profileCustomBtn;

    private Controller controller;
    private LanguageManager langManager;

    public UserEventsWindow(Controller controller) {
        super();
        this.controller = controller;
        this.langManager = controller.getLanguageManager();

        initComponents();
        
        this.setResizable(true);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        this.cp = this.getContentPane();
        this.cp.setLayout(new BorderLayout());

        JPanel bigMainPanel = new JPanel(new BorderLayout());

        this.titlePanel = new JPanel(new BorderLayout());
        this.mainPanel = new JPanel();

        JPanel languageSwitchPanel = new JPanel();
        JPanel langPanel = new JPanel();

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
                        new UserEventsWindow(controller);
					}
				}
			});

			esPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!langManager.getLanguage().equals("es")) {
						controller.getLanguageManager().setLanguage("es");
						dispose();
						new UserEventsWindow(controller);
					}
				}
			});

			itPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!langManager.getLanguage().equals("it")) {
						controller.getLanguageManager().setLanguage("it");
						dispose();
						new UserEventsWindow(controller);
					}
				}
			});

            
			langPanel.add(enPanel);
			langPanel.add(esPanel);
			langPanel.add(itPanel);
		} catch (Exception ex) {
			ex.printStackTrace();
        }
        
        languageSwitchPanel.add(langPanel, BorderLayout.EAST);
        languageSwitchPanel.add(new JPanel(), BorderLayout.CENTER);

        // TOP PANEL
        JLabel pageTitle = new JLabel(langManager.getString("eventsText"));
        JPanel title_panel = new JPanel();
        title_panel.add(pageTitle);

        this.titlePanel.add(title_panel, BorderLayout.CENTER);

        this.profileCustomBtn = new JButton(langManager.getString("customizeText"));
        JPanel profileCustom_panel = new JPanel();
        profileCustom_panel.add(profileCustomBtn);

        this.titlePanel.add(profileCustom_panel, BorderLayout.EAST);

        // MAIN PANEL
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        for (EventInfo e : this.controller.getUser().getSavedEvents()) {
            this.mainPanel.add(new EventListElement(this, e));
        }
        this.mainPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(this.mainPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        bigMainPanel.add(this.titlePanel, BorderLayout.NORTH);
        bigMainPanel.add(scrollPane, BorderLayout.CENTER);
        
        this.cp.add(languageSwitchPanel, BorderLayout.NORTH);
        this.cp.add(bigMainPanel, BorderLayout.CENTER);

        this.setListeners();
    }

    private void setListeners() {
        profileCustomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Profile(controller);
            }
        });
    }

    private class EventListElement extends JPanel {

        private static final long serialVersionUID = 1L;

        private JButton detailsButton;
        UserEventsWindow window;

        private EventInfo event;

        public EventListElement(UserEventsWindow window, EventInfo event) {
            super();

            this.window = window;
            this.event = event;

            this.initComponents();
        }

        private void initComponents() {
            this.setLayout(new GridLayout(1, 3));
            this.add(new JLabel(this.event.getName()));
            this.add(new JLabel(this.event.getDescription()));
            
            JPanel detailsButtonPanel = new JPanel();
            detailsButtonPanel.setLayout(new BoxLayout(detailsButtonPanel, BoxLayout.Y_AXIS));
            detailsButton = new JButton(langManager.getString("detailsText"));
            detailsButtonPanel.add(Box.createVerticalGlue());
            detailsButtonPanel.add(detailsButton);
            detailsButtonPanel.add(Box.createVerticalGlue());
            this.add(detailsButtonPanel);

            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new EventWindow(window.controller, event);
                    window.dispose();
                }
            });

            this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            this.setPreferredSize(new Dimension(2000, 200));
        }
        
    }

}