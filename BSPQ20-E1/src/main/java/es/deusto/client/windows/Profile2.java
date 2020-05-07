package es.deusto.client.windows;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.UserInfo;
import es.deusto.serialization.TopicInfo;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class Profile2 extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private Controller controller;
    private LanguageManager langManager;

    public Profile2(Controller controller) {
        super();
        
        this.controller = controller;
        this.langManager = controller.getLanguageManager();

        initComponents();

        this.setSize(new Dimension(400, 655));
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initComponents() {
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(20,20,20,20));

        container.add(initTopPanel(), BorderLayout.NORTH);
        container.add(initMainPanel(), BorderLayout.CENTER);
        container.add(initBottomPanel(), BorderLayout.SOUTH);

        cp.add(container, BorderLayout.CENTER);
    }

    private JPanel initTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JButton homeButton = new JButton(UIManager.getIcon("FileChooser.homeFolderIcon"));
        panel.add(homeButton, BorderLayout.EAST);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        return panel;
    }

    private JPanel initMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int margin = 5;

        // name
        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel nameTitlePanel = new JPanel(new BorderLayout());
        JPanel nameContentPanel = new JPanel(new BorderLayout());

        JLabel nameLabel = new JLabel(langManager.getString("nameLabel"));
        nameTitlePanel.add(nameLabel, BorderLayout.WEST);
        
        JTextField nameField = new JTextField(controller.getUser().getName());
        nameField.setEditable(false);
        JPanel namec = new JPanel();
        namec.add(nameField);
        nameContentPanel.add(namec, BorderLayout.WEST);
        nameContentPanel.setBorder(new EmptyBorder(margin, margin, margin, margin));

        namePanel.add(nameTitlePanel, BorderLayout.NORTH);
        namePanel.add(nameContentPanel, BorderLayout.CENTER);

        // email
        JPanel emailPanel = new JPanel(new BorderLayout());
        JPanel emailTitlePanel = new JPanel(new BorderLayout());
        JPanel emailContentPanel = new JPanel(new BorderLayout());

        JLabel emailLabel = new JLabel(langManager.getString("emailLabel"));
        emailTitlePanel.add(emailLabel, BorderLayout.WEST);

        JTextField emailField = new JTextField(controller.getUser().getEmail());
        emailField.setEditable(false);
        JPanel emailc = new JPanel();
        emailc.add(emailField);
        emailContentPanel.add(emailc, BorderLayout.WEST);
        emailContentPanel.setBorder(new EmptyBorder(margin, margin, margin, margin));

        emailPanel.add(emailTitlePanel, BorderLayout.NORTH);
        emailPanel.add(emailContentPanel, BorderLayout.CENTER);

        // city
        JPanel cityPanel = new JPanel(new BorderLayout());
        JPanel cityTitlePanel = new JPanel(new BorderLayout());
        JPanel cityContentPanel = new JPanel(new BorderLayout());

        JLabel cityLabel = new JLabel(langManager.getString("cityLabel"));
        cityTitlePanel.add(cityLabel, BorderLayout.WEST);

        JTextField cityField = new JTextField(controller.getUser().getCity());
        JPanel cityc = new JPanel();
        cityc.add(cityField);
        cityContentPanel.add(cityc, BorderLayout.WEST);
        cityContentPanel.setBorder(new EmptyBorder(margin, margin, margin, margin));

        cityPanel.add(cityTitlePanel, BorderLayout.NORTH);
        cityPanel.add(cityContentPanel, BorderLayout.CENTER);
        
        // country
        JPanel countryPanel = new JPanel(new BorderLayout());
        JPanel countryTitlePanel = new JPanel(new BorderLayout());
        JPanel countryContentPanel = new JPanel(new BorderLayout());

        JLabel countryLabel = new JLabel(langManager.getString("countryLabel"));
        countryTitlePanel.add(countryLabel, BorderLayout.WEST);

        JTextField countryField = new JTextField(controller.getUser().getCountry());
        JPanel countryc = new JPanel();
        countryc.add(countryField);
        countryContentPanel.add(countryc, BorderLayout.WEST);
        countryContentPanel.setBorder(new EmptyBorder(margin, margin, margin, margin));

        countryPanel.add(countryTitlePanel, BorderLayout.NORTH);
        countryPanel.add(countryContentPanel, BorderLayout.CENTER);

        // interests
        JPanel interestsPanel = new JPanel(new BorderLayout());
        JPanel interestsTitlePanel = new JPanel(new BorderLayout());
        JPanel interestsContentPanel = new JPanel(new BorderLayout());

        JLabel interestsLabel = new JLabel(langManager.getString("interestsLabel"));
        interestsTitlePanel.add(interestsLabel, BorderLayout.WEST);

        // meter lista de intereses
        
        
        interestsPanel.add(interestsTitlePanel, BorderLayout.NORTH);
        interestsPanel.add(interestsContentPanel, BorderLayout.CENTER);

        int sep = 5;
        panel.add(namePanel);
        panel.add(Box.createVerticalStrut(sep));
        panel.add(emailPanel);
        panel.add(Box.createVerticalStrut(sep));
        panel.add(cityPanel);
        panel.add(Box.createVerticalStrut(sep));
        panel.add(countryPanel);
        panel.add(Box.createVerticalStrut(sep));
        panel.add(interestsPanel);
        panel.add(Box.createVerticalStrut(sep));

        return panel;
    }

    private JPanel initBottomPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        JButton saveButton, deleteButton;
        saveButton = new JButton(langManager.getString("saveButton"));
        deleteButton = new JButton(langManager.getString("deleteButton"));

        JPanel savePanel, deletePanel;
        savePanel = new JPanel();
        deletePanel = new JPanel();

        savePanel.add(saveButton);
        deletePanel.add(deleteButton);

        savePanel.setBorder(new EmptyBorder(10,0,10,10));
        deletePanel.setBorder(new EmptyBorder(10,10,10,0));

        panel.add(savePanel);
        panel.add(deletePanel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        
        return panel;
    }

}