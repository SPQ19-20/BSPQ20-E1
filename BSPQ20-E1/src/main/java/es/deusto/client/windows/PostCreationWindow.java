package es.deusto.client.windows;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.PostInfo;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;

/**
 * This class is the one used to create a window that allows
 * an organizer to have like a double check of the event he
 * has just created.
 */

public class PostCreationWindow extends JFrame {
    
    private Controller controller;
    private LanguageManager langManager;

    private EventInfo event;

    private JTextField titleField;
    private JTextArea descArea;

    public PostCreationWindow(Controller controller, EventInfo event) {
        super();
        this.controller = controller;
        this.langManager = controller.getLanguageManager();
        this.event = event;

        initComponents();

        setVisible(true);
        setSize(new Dimension(485, 500));
        setResizable(false);
    }

    private void initComponents() {
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(initTopPanel(), BorderLayout.NORTH);
        cp.add(initMainPanel(), BorderLayout.CENTER);
        cp.add(initBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel initTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttonPanel, titlePanel;
        buttonPanel = new JPanel(new BorderLayout());
        titlePanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton(langManager.getString("closeButton"));
        buttonPanel.setBorder(new EmptyBorder(20,20,20,20));
        buttonPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel(langManager.getString("newPost"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.setBorder(new EmptyBorder(20,20,20,20));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(titlePanel, BorderLayout.CENTER);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        return panel;
    }

    private JPanel initMainPanel() {
        JPanel panel = new JPanel(null);
        
        JLabel eventLabel, titleLabel, descLabel;
        JTextField eventField;

        eventLabel = new JLabel(langManager.getString("Event"));
        titleLabel = new JLabel(langManager.getString("Title"));
        descLabel = new JLabel(langManager.getString("Description"));

        eventField = new JTextField(30);
        titleField = new JTextField(30);
        descArea = new JTextArea();
        descArea.setLineWrap(true);

        eventField.setText(event.getName());
        eventField.setEditable(false);

        eventLabel.setBounds(20, 10, 90, 20);
        titleLabel.setBounds(20, 60, 90, 20);
        descLabel.setBounds(20, 110, 90, 20);

        eventField.setBounds(160, 10, 200, 20);
        titleField.setBounds(160, 60, 200, 20);
        descArea.setBounds(160, 110, 300, 130);
        
        panel.add(eventLabel);
        panel.add(titleLabel);
        panel.add(descLabel);
        panel.add(eventField);
        panel.add(titleField);
        panel.add(descArea);
        

        return panel;
    }

    private JPanel initBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JButton createButton = new JButton(langManager.getString("Create"));
        panel.setBorder(new EmptyBorder(20,20,20,20));
        panel.add(createButton, BorderLayout.EAST);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostInfo post = new PostInfo();
                post.setDate(new Date());
                post.setTitle(titleField.getText());
                post.setDescription(descArea.getText());
                post.setEventName(event.getName());
                post.setOrganizerEmail(controller.getOrganize().getEmail());
                controller.createPost(event, post);
                //event.getPosts().add(post);

                dispose();
                new OrganizerHome(controller);
            }
        });

        return panel;
    }

}