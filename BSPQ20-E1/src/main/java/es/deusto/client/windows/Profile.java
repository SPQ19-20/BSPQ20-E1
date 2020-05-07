package es.deusto.client.windows;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.UserInfo;
import es.deusto.serialization.TopicInfo;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Profile extends JFrame {

    private static final long serialVersionUID = 1L;
    public JButton saveButton, homeButton, deleteButton;
    private JTextField city, email;
    private JCheckBox musicBox, theaterBox, cinemaBox, sportsBox, artBox, cultureBox, foodBox, festivalsBox, moreBox;
    private ArrayList<TopicInfo> interests = new ArrayList<>(); //list of interests of the user PLEASE DO NOT DELETE MORE TIMES!
    private Controller controller;
    private LanguageManager langManager;

    public Profile(Controller controller) {
        this.controller = controller;
        this.langManager = controller.getLanguageManager();

        // TODO get the logged in user email
        String userInterests = "Music Theater";//(String) userData.get("interests");

        getContentPane().setLayout(null);
        setTitle("Profile");
        this.setSize(new Dimension(400, 550));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        homeButton = new JButton(UIManager.getIcon("FileChooser.homeFolderIcon"));
        homeButton.setBounds(320, 25, 25, 25);
        getContentPane().add(homeButton);

        UserInfo userInfo = this.controller.getUser();

        JLabel username = new JLabel(langManager.getString("nameLabel"));
        username.setBounds(40, 50, 80, 16);
        getContentPane().add(username);

        JTextField user = new JTextField(userInfo.getName());
        user.setBounds(40, 70, 80, 20);
        user.setEditable(false);
        getContentPane().add(user);

        JLabel emailAdd = new JLabel(langManager.getString("emailLabel"));
        emailAdd.setBounds(40, 100, 80, 20);
        getContentPane().add(emailAdd);

        email = new JTextField(userInfo.getEmail());
        email.setBounds(40, 120, 150, 20);
        email.setEditable(false);
        getContentPane().add(email);

        JLabel cityLabel = new JLabel(langManager.getString("cityLabel"));
        cityLabel.setBounds(40, 150, 80, 20);
        getContentPane().add(cityLabel);

        city = new JTextField(userInfo.getCity());
        city.setBounds(40, 170, 150, 20);
        city.setEditable(true);
        getContentPane().add(city);

        JLabel interests = new JLabel(langManager.getString("interestsLabel"));
        interests.setBounds(40, 200, 80, 20);
        getContentPane().add(interests);

        JPanel interestsContainer = new JPanel();
        interestsContainer.setBackground(Color.RED);
        interestsContainer.setBounds(40, 220, 100, 180);
        interestsContainer.add(new JLabel("hey"));
        // musicBox.setBounds(40, 220, 150, 20);
        // moreBox.setBounds(40, 380, 150, 20);

        saveButton = new JButton(langManager.getString("saveButton"));
        saveButton.setBounds(40, 430, 140, 25);
        getContentPane().add(saveButton);

        deleteButton = new JButton(langManager.getString("deleteButton"));
        deleteButton.setBounds(200, 430, 140, 25);
        getContentPane().add(deleteButton);

        this.setListeners();
        this.setVisible(true);
    }

     /**Actions when the save button is clicked */
     private void setListeners() {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String interests = ""; //now it's a arrayList<TopicInfo>
                if (musicBox.isSelected()) interests.add(new TopicInfo(langManager.getString("musicCheck")));
                if (theaterBox.isSelected()) interests.add(new TopicInfo(langManager.getString("theaterCheck")));
                if (cinemaBox.isSelected()) interests.add(new TopicInfo(langManager.getString("cinemaCheck")));
                if (sportsBox.isSelected()) interests.add(new TopicInfo(langManager.getString("sportsCheck")));
                if (artBox.isSelected()) interests.add(new TopicInfo(langManager.getString("artsCheck")));
                if (cultureBox.isSelected()) interests.add(new TopicInfo(langManager.getString("cultureCheck")));
                if (foodBox.isSelected()) interests.add(new TopicInfo(langManager.getString("foodCheck")));
                if (festivalsBox.isSelected()) interests.add(new TopicInfo(langManager.getString("festivalsCheck")));
                if (moreBox.isSelected()) interests.add(new TopicInfo(langManager.getString("moreCheck")));
                
                // update user (since Sprint 2)
                
                controller.getUser().setCity(city.getText()); //change the users city
                controller.getUser().setInterests(interests); //change the users interests.
                if (controller.attemptNormalUpdate()) { ///sends the modified user in the controller to the server.
                    JOptionPane.showMessageDialog(null, langManager.getString("updateText"), langManager.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, langManager.getString("failUpdateText"), langManager.getString("error"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserEventsWindow home = new UserEventsWindow(controller);
                setVisible(false);
                dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null,
                        langManager.getString("deleteMessage"),
                        langManager.getString("deleteTitle"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (res == 0) {
                    // TODO Delete Account - Delete user data from DB
                    if (controller.attemptUserDelete()) {
                        JOptionPane.showMessageDialog(null, langManager.getString("deleteConf"), langManager.getString("deleteTitle"), JOptionPane.INFORMATION_MESSAGE);
                        LogInWindow logIn = new LogInWindow(controller);
                        setVisible(false);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, langManager.getString("deleteConf1"), langManager.getString("deleteTitle"), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

    }   

}
