package es.deusto.client.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.EventInfo;

/**
 * This class is the one used to create a window that allows
 * a user to scroll, search and select from a list of events
 * he previously has chosen from. A personal event list.
 */

public class UserEventsWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private Container cp;
    private JPanel titlePanel, mainPanel;
    private JButton profileCustomBtn;

    private Controller controller;
    private LanguageManager langManager;

    JFrame me;

    public UserEventsWindow(Controller controller) {
        super();
        this.controller = controller;
        this.langManager = controller.getLanguageManager();
        this.me = this;

        initComponents();
        
        this.setResizable(false);
        this.setSize(new Dimension(650, 750));
        //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        this.setMenuBar();
        this.cp = this.getContentPane();
        this.cp.setLayout(new BorderLayout());

        JPanel bigMainPanel = new JPanel(new BorderLayout());

        this.titlePanel = new JPanel(new BorderLayout());
        this.mainPanel = new JPanel();

        // TOP PANEL
        JLabel pageTitle = new JLabel(langManager.getString("suggestionsForYou"));
        JPanel title_panel = new JPanel();
        title_panel.add(pageTitle);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 30));
        title_panel.setBorder(new EmptyBorder(20,20,20,20));

        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(new EmptyBorder(10,10,0,10));
        JLabel welcomeLabel = new JLabel(langManager.getString("welcome") +", "+controller.getUser().getName());
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 25));

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        titlePanel.add(welcomePanel, BorderLayout.CENTER);
        titlePanel.add(title_panel, BorderLayout.SOUTH);

        profileCustomBtn = new JButton(langManager.getString("customizeText"));
        JPanel profileCustom_panel = new JPanel();
        profileCustom_panel.add(profileCustomBtn);

        // this.titlePanel.add(profileCustom_panel, BorderLayout.EAST);

        // MAIN PANEL
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        ArrayList<EventInfo> rec = controller.getRecommendations();
        for (EventInfo e : rec) {
            for (int i = 0; i < 1; i++) {
                // e.setName(e.getName()+"kkk");
                this.mainPanel.add(new EventListItem(controller, e, this));
            }
        }
        
        this.mainPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(this.mainPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setViewportBorder(new EmptyBorder(10,10,10,10));

        bigMainPanel.add(this.titlePanel, BorderLayout.NORTH);
        bigMainPanel.add(scrollPane, BorderLayout.CENTER);
        
        this.cp.add(bigMainPanel, BorderLayout.CENTER);

        this.setListeners();
    }

    private void setMenuBar() {
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        JMenu accountMenu = new JMenu(langManager.getString("Account"));
        JMenu eventsMenu = new JMenu(langManager.getString("Events"));
        JMenu settingsMenu = new JMenu(langManager.getString("Settings"));

        // account menu
        JMenuItem logoutItem = new JMenuItem(langManager.getString("Logout"));
        JMenuItem customItem = new JMenuItem(langManager.getString("customizeText"));

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setUser(null);
                dispose();
                new LogInWindow(controller);
            }
        });

        customItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Profile(controller, me);
            }
        });
        
        accountMenu.add(customItem);
        accountMenu.add(logoutItem);
        
        // events menu
        JMenuItem yourEventsItem = new JMenuItem(langManager.getString("eventsText"));
        JMenuItem refreshItem = new JMenuItem(langManager.getString("refreshRecc"));

        yourEventsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SavedEventsWindow(controller);
            }
        });

        refreshItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserEventsWindow(controller);
            }
        });
        
        eventsMenu.add(refreshItem);
        eventsMenu.add(yourEventsItem);

        // settingsMenu
        JMenu languageMenu = new JMenu(langManager.getString("Language"));
        JMenuItem english = new JMenuItem("EN");
        JMenuItem spanish = new JMenuItem("ES");
        JMenuItem greek = new JMenuItem("EL");
        JMenuItem italian = new JMenuItem("IT");

        try {
            ImageIcon iconEN, iconES, iconIT, iconGR;
            iconEN = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gb.png").getFile())));
            iconES = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/es.png").getFile())));
            iconIT = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/it.png").getFile())));
            iconGR = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/gr.png").getFile())));
            english.setIcon(iconEN);
            spanish.setIcon(iconES);
            greek.setIcon(iconGR);
            italian.setIcon(iconIT);
        } catch (Exception e) {}

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!langManager.getLanguage().equals("en")) {
                    controller.getLanguageManager().setLanguage("en");
                    dispose();
                    new UserEventsWindow(controller);
                }
            }
        });

        spanish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!langManager.getLanguage().equals("es")) {
                    controller.getLanguageManager().setLanguage("es");
                    dispose();
                    new UserEventsWindow(controller);
                }
            }
        });

        italian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!langManager.getLanguage().equals("it")) {
                    controller.getLanguageManager().setLanguage("it");
                    dispose();
                    new UserEventsWindow(controller);
                }
            }
        });

        greek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!langManager.getLanguage().equals("el")) {
                    controller.getLanguageManager().setLanguage("el");
                    dispose();
                    new UserEventsWindow(controller);
                }
            }
        });

        languageMenu.add(english);
        languageMenu.add(spanish);
        languageMenu.add(greek);
        languageMenu.add(italian);
        
        settingsMenu.add(languageMenu);       

        bar.add(accountMenu);
        bar.add(eventsMenu);
        bar.add(settingsMenu);
    }

    private void setListeners() {
        
    }

}