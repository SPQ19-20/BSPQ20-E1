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

/**
 * This class is the one used to create a window that allows
 * an organizer to have all of his events.
 */

public class OrganizerEvents extends JFrame {

    private static final long serialVersionUID = 1L;

    private Controller controller;
    private LanguageManager langManager;

    private JButton logoutButton;

    public OrganizerEvents(Controller controller) {
        super();
        this.controller = controller;
        this.langManager = controller.getLanguageManager();

        initComponents();
        setListeners();

        this.setVisible(true);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    private void initComponents() {
        this.setTitle("");
        getContentPane().setLayout(new BorderLayout());

        JPanel topControlPanel = initTopControlPanel();
        JPanel contentPanel = initContentPanel();

        getContentPane().add(topControlPanel, BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel initTopControlPanel() {
        JPanel topControlPanel = new JPanel(new BorderLayout());

        logoutButton = new JButton(langManager.getString("logout"));
        JPanel languageButtonsPanel = new JPanel(new GridLayout(1, 3));

        try {
            ImageIcon iconEN, iconES, iconIT, iconGR;
            iconEN = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gb.png").getFile())));
            iconES = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/es.png").getFile())));
            iconIT = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/it.png").getFile())));
            iconGR = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gr.png").getFile())));

            JPanel enPanel, esPanel, itPanel, grPanel;
            enPanel = new JPanel();
            enPanel.add(new JLabel(iconEN));
            esPanel = new JPanel();
            esPanel.add(new JLabel(iconES));
            itPanel = new JPanel();
            itPanel.add(new JLabel(iconIT));
            grPanel = new JPanel();
            grPanel.add(new JLabel(iconGR));

            enPanel.setBorder(new EmptyBorder(2,2,2,2));
            esPanel.setBorder(new EmptyBorder(2,2,2,2));
            itPanel.setBorder(new EmptyBorder(2,2,2,2));
            grPanel.setBorder(new EmptyBorder(2,2,2,2));

            enPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!langManager.getLanguage().equals("en")) {
                        controller.getLanguageManager().setLanguage("en");
                        dispose();
                        new OrganizerEvents(controller);
                    }
                }
            });

            esPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!langManager.getLanguage().equals("es")) {
                        controller.getLanguageManager().setLanguage("es");
                        dispose();
                        new OrganizerEvents(controller);
                    }
                }
            });

            itPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!langManager.getLanguage().equals("it")) {
                        controller.getLanguageManager().setLanguage("it");
                        dispose();
                        new OrganizerEvents(controller);
                    }
                }
            });

            grPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!langManager.getLanguage().equals("el")) {
                        controller.getLanguageManager().setLanguage("el");
                        dispose();
                        new OrganizerEvents(controller);
                    }
                }
            });

            languageButtonsPanel.add(enPanel);
            languageButtonsPanel.add(esPanel);
            languageButtonsPanel.add(itPanel);
            languageButtonsPanel.add(grPanel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel logoutButtonContainer = new JPanel();
        logoutButtonContainer.add(logoutButton);

        JPanel languageButtonsPanelContainer = new JPanel();
        languageButtonsPanelContainer.add(languageButtonsPanel);

        topControlPanel.add(logoutButtonContainer, BorderLayout.WEST);
        topControlPanel.add(languageButtonsPanelContainer, BorderLayout.EAST);

        return topControlPanel;
    }

    private JPanel initContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel welcomePanel = new JPanel(new BorderLayout());
        JPanel mainPanel = initMainPanel();

        JLabel welcomeLabel = new JLabel(langManager.getString("welcome") + ", " + controller.getOrganize().getName());
        welcomePanel.add(welcomeLabel);

        contentPanel.add(welcomePanel, BorderLayout.NORTH);
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel initMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonsContainer = new JPanel();

        JPanel eventsPanel = initEventsPanel();

        mainPanel.add(buttonsContainer, BorderLayout.NORTH);
        mainPanel.add(eventsPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel initEventsPanel() {
        JPanel eventsPanel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(langManager.getString("yourEvents"));
        titlePanel.add(titleLabel);

        JPanel eventListPanel = new JPanel();
        eventListPanel.setLayout(new BoxLayout(eventListPanel, BoxLayout.Y_AXIS));

        for (EventInfo e: controller.getOrganize().getCreatedEvents()) {
            eventListPanel.add(new EventListItem(e));
            eventListPanel.add(Box.createVerticalGlue());
        }

        eventListPanel.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(
                eventListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        eventsPanel.add(titlePanel, BorderLayout.NORTH);
        eventsPanel.add(scroll, BorderLayout.CENTER);

        return eventsPanel;
    }

    private void setListeners() {

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setOrganizer(null);
                new LogInWindow(controller);
            }
        });

    }

    private class EventListItem extends JPanel {

        private static final long serialVersionUID = 1L;

        private EventInfo event;

        public EventListItem(EventInfo event) {
            super();
            this.event = event;

            initComponents();
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            JPanel leftPanel, middlePanel, rightPanel;

            leftPanel = initLeftPanel();
            middlePanel = initMiddlePanel();
            rightPanel = initRightPanel();

            JPanel leftContainer, rightContainer, middleContainer;
            leftContainer = new JPanel();
            middleContainer = new JPanel();
            rightContainer = new JPanel();

            // leftContainer.add(leftPanel);
            // middleContainer.add(middlePanel);
            rightContainer.add(rightPanel);


            add(leftPanel, BorderLayout.WEST);
            add(middlePanel, BorderLayout.CENTER);
            add(rightContainer, BorderLayout.EAST);
        }

        private JPanel initLeftPanel() {
            JPanel leftPanel = new JPanel(new GridLayout(2, 2));

            JLabel labelName = new JLabel(langManager.getString("name"));
            JLabel labelDescription = new JLabel(langManager.getString("description"));
            JLabel labelNameContent = new JLabel(event.getName());
            JLabel labelDescriptionContent = new JLabel(event.getDescription());
            JLabel labelInterested = new JLabel(langManager.getString("interested"));
            JLabel labelInterestedContent = new JLabel(String.valueOf(event.getInterested()));

            leftPanel.add(labelName);
            leftPanel.add(labelNameContent);
            leftPanel.add(labelDescription);
            leftPanel.add(labelDescriptionContent);
            leftPanel.add(labelInterested);
            leftPanel.add(labelInterestedContent);

            return leftPanel;
        }

        private JPanel initMiddlePanel() {
            JPanel middlePanel = new JPanel(new GridLayout(1, 2));

            JLabel labelTopic = new JLabel(langManager.getString("topic"));
            JLabel labelTopicContent = new JLabel(event.getTopic().getName());

            middlePanel.add(labelTopic);
            middlePanel.add(labelTopicContent);

            return middlePanel;
        }

        private JPanel initRightPanel() {
            JPanel rightPanel = new JPanel();

            JButton buttonDetails = new JButton(langManager.getString("detailsText"));
            rightPanel.add(buttonDetails);

            buttonDetails.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new EventWindow(controller, event);
                }
            });

            return rightPanel;
        }

    }

}