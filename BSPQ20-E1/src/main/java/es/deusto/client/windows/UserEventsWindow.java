package es.deusto.client.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.EventInfo;

public class UserEventsWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private Controller controller;
    
    private Container cp;
    private JPanel titlePanel;
    private JPanel mainPanel;

    public UserEventsWindow(Controller controller) {
        super();
        this.controller = controller;

        initComponents();
        

        this.setResizable(true);
        this.setSize(new Dimension(1600, 900));
        this.setVisible(true);

        // this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        this.cp = this.getContentPane();
        this.cp.setLayout(new BorderLayout());

        this.titlePanel = new JPanel();
        this.mainPanel = new JPanel();

        // TOP PANEL
        JLabel pageTitle = new JLabel("Your events:");
        this.titlePanel.add(pageTitle);

        // MAIN PANEL
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        for (EventInfo e : this.controller.getUser().getSavedEvents()) {
            this.mainPanel.add(new EventListElement(this, e));
        }

        this.cp.add(this.titlePanel, BorderLayout.NORTH);
        this.cp.add(this.mainPanel, BorderLayout.CENTER);
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
            detailsButton = new JButton("See details");
            detailsButtonPanel.add(Box.createVerticalGlue());
            detailsButtonPanel.add(detailsButton);
            detailsButtonPanel.add(Box.createVerticalGlue());
            this.add(detailsButtonPanel);

            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new Profile(window.controller);
                    new EventWindow(window.controller, event);
                    window.dispose();
                }
            });

            this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
        
    }

}