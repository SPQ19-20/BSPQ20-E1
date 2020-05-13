package es.deusto.client.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.EventInfo;

/**
 * This class is the one used to create a window that allows
 * to an organizer to change or administrate one of his events.
 */

public class EventOrganizerWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private Container cp;
    private JPanel titlePanel, mainPanel;
    private JButton profileCustomBtn;

    private Controller controller;
    private LanguageManager langManager;

    public EventOrganizerWindow(Controller controller) {
        super();
        this.controller = controller;
        this.langManager = controller.getLanguageManager();

        initComponents();
        
        this.setResizable(true);
        this.setSize(new Dimension(1600, 900));
        this.setVisible(true);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        this.cp = this.getContentPane();
        this.cp.setLayout(new BorderLayout());

        this.titlePanel = new JPanel(new BorderLayout());
        this.mainPanel = new JPanel();

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
        for (EventInfo e : this.controller.getOrganize().getCreatedEvents()){ //only the events created by the user are displayed originally
            this.mainPanel.add(new EventListElement(this, e));//
        }
        this.mainPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(this.mainPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.cp.add(this.titlePanel, BorderLayout.NORTH);
        this.cp.add(scrollPane, BorderLayout.CENTER);

       // this.setListeners();
    }
/*for the moment the organizer can't update i'ts profile, WE need a GUI for supporting this functionality
  
    private void setListeners() {
        profileCustomBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Profile(controller);
            }
        });
    }
*/
    private class EventListElement extends JPanel {

        private static final long serialVersionUID = 1L;

        private JButton detailsButton;
        EventOrganizerWindow window;

        private EventInfo event;

        public EventListElement(EventOrganizerWindow window, EventInfo event) {
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