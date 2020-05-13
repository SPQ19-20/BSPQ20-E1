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
 * to scroll, search and choose from all of the events stored 
 * in the database.
 */

public class SavedEventsWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private Container cp;
    private JPanel titlePanel, mainPanel;
    private JButton profileCustomBtn;

    private Controller controller;
    private LanguageManager langManager;

    public SavedEventsWindow(Controller controller) {
        super();
        this.controller = controller;
        this.langManager = controller.getLanguageManager();

        initComponents();
        
        this.setResizable(false);
        this.setSize(new Dimension(650, 750));
        //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        this.cp = this.getContentPane();
        this.cp.setLayout(new BorderLayout());

        JPanel bigMainPanel = new JPanel(new BorderLayout());

        this.titlePanel = new JPanel(new BorderLayout());
        this.mainPanel = new JPanel();

        // TOP PANEL
        JLabel pageTitle = new JLabel(langManager.getString("yourEvents"));
        JPanel title_panel = new JPanel();
        title_panel.add(pageTitle);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 30));
        title_panel.setBorder(new EmptyBorder(20,20,20,20));

        JButton backButton = new JButton(langManager.getString("closeButton"));
        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.setBorder(new EmptyBorder(20,20,20,20));
        backPanel.add(backButton, BorderLayout.WEST);

        titlePanel.add(backPanel, BorderLayout.CENTER);
        titlePanel.add(title_panel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserEventsWindow(controller);
            }
        });

        profileCustomBtn = new JButton(langManager.getString("customizeText"));
        JPanel profileCustom_panel = new JPanel();
        profileCustom_panel.add(profileCustomBtn);

        // this.titlePanel.add(profileCustom_panel, BorderLayout.EAST);

        // MAIN PANEL
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        ArrayList<EventInfo> rec = controller.getUser().getSavedEvents();
        for (EventInfo e : rec) {
            for (int i = 0; i < 1; i++) {
                this.mainPanel.add(new EventListItem(controller, e));
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

    private void setListeners() {
        
    }

}