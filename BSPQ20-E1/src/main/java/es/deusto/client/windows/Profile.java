package es.deusto.client.windows;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.TopicInfo;
import es.deusto.serialization.UserInfo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Profile extends JFrame {

    private static final long serialVersionUID = 1L;
    public JButton saveButton, homeButton;
    private JTextField city, email;
    private JCheckBox musicBox, theaterBox, cinemaBox, sportsBox, artBox, cultureBox, foodBox, festivalsBox, moreBox;
    
    private Controller controller;
    private LanguageManager langManager;
    private ArrayList<TopicInfo> interests = new ArrayList<>(); //list of interests of the user

    public Profile(Controller controller) {
        this.controller = controller;
        this.langManager = controller.getLanguageManager();

        // TODO get the logged in user email
        String userInterests = "Music Theater";//(String) userData.get("interests");

        getContentPane().setLayout(null);
        setTitle("Profile");
        this.setSize(new Dimension(400, 600));
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

        musicBox = new JCheckBox(langManager.getString("musicCheck"), userInterests.contains("Music"));
        musicBox.setBounds(40, 220, 150, 20);
        getContentPane().add(musicBox);

        cinemaBox = new JCheckBox(langManager.getString("cinemaCheck"), userInterests.contains("Cinema"));
        cinemaBox.setBounds(40, 240, 150, 20);
        getContentPane().add(cinemaBox);

        theaterBox = new JCheckBox(langManager.getString("theaterCheck"), userInterests.contains("Theater"));
        theaterBox.setBounds(40, 260, 150, 20);
        getContentPane().add(theaterBox);

        sportsBox = new JCheckBox(langManager.getString("sportsCheck"), userInterests.contains("Sports"));
        sportsBox.setBounds(40, 280, 150, 20);
        getContentPane().add(sportsBox);

        cultureBox = new JCheckBox(langManager.getString("cultureCheck"), userInterests.contains("Culture"));
        cultureBox.setBounds(40, 300, 150, 20);
        getContentPane().add(cultureBox);

        artBox = new JCheckBox(langManager.getString("artsCheck"), userInterests.contains("Arts"));
        artBox.setBounds(40, 320, 150, 20);
        getContentPane().add(artBox);

        foodBox = new JCheckBox(langManager.getString("foodCheck"), userInterests.contains("Food"));
        foodBox.setBounds(40, 340, 150, 20);
        getContentPane().add(foodBox);

        festivalsBox = new JCheckBox(langManager.getString("festivalsCheck"), userInterests.contains("Festivals"));
        festivalsBox.setBounds(40, 360, 150, 20);
        getContentPane().add(festivalsBox);

        moreBox = new JCheckBox(langManager.getString("moreCheck"), userInterests.contains("More.."));
        moreBox.setBounds(40, 380, 150, 20);
        getContentPane().add(moreBox);

        saveButton = new JButton(langManager.getString("saveButton"));
        saveButton.setBounds(120, 430, 140, 25);
        getContentPane().add(saveButton);

        this.setListeners();
        this.setVisible(true);
    }

    /**Actions when the save button is clicked */
    private void setListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String interests = ""; //now it's a arrayList<TopicInfo>
                if (musicBox.isSelected()) interests.add(new TopicInfo("Music"));
                if (theaterBox.isSelected()) interests.add(new TopicInfo("Theater"));
                if (cinemaBox.isSelected()) interests.add(new TopicInfo("Cinema"));
                if (sportsBox.isSelected()) interests.add(new TopicInfo("Sports"));
                if (artBox.isSelected()) interests.add(new TopicInfo("Art"));
                if (cultureBox.isSelected()) interests.add(new TopicInfo("Culture"));
                if (foodBox.isSelected()) interests.add(new TopicInfo("Food"));
                if (festivalsBox.isSelected()) interests.add(new TopicInfo("Festivals"));
                if (moreBox.isSelected()) interests.add(new TopicInfo("More"));
                
                // update user (since Sprint 2)
                controller.getUser().setInterests(interests); //change the users interests.
                controller.attemptNormalUpdate(); ///sends the modified user in the controller to the server.
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("home button pressed");
                int posY = getY();
                int posX = getX();
                int altura = getHeight();
                int anchura = getWidth();
                UserEventsWindow home = new UserEventsWindow(controller);
                setVisible(false);
                dispose();
            }
        });
    }

}
