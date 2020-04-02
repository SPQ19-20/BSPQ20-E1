import com.mongodb.*;
import com.mongodb.util.JSON;

import es.deusto.client.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.*;

public class Profile extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    public JButton saveButton;;
    private JTextField city, email;
    private JCheckBox musicBox, theaterBox, cinemaBox, sportsBox, artBox, cultureBox, foodBox, festivalsBox, moreBox;
    private Controller controller;
    private static MongoClient mongoClient = null;
    static {
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static DB database = mongoClient.getDB("bspq20e1");
    private static DBCollection users = database.getCollection("users");

    public Profile(Controller controller) {
        this.controller = controller;

        // TODO get the logged in user email
        DBObject userData = getUserData("email");
        String userInterests = (String) userData.get("interests");

        getContentPane().setLayout(null);
        setTitle("Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel username = new JLabel("Username:");
        username.setBounds(40, 50, 80, 16);
        getContentPane().add(username);

        JTextField user = new JTextField((String) userData.get("username"));
        user.setBounds(40, 70, 80, 20);
        user.setEditable(false);
        getContentPane().add(user);

        JLabel emailAdd = new JLabel("Email:");
        emailAdd.setBounds(40, 100, 80, 20);
        getContentPane().add(emailAdd);

        email = new JTextField((String) userData.get("email"));
        email.setBounds(40, 120, 150, 20);
        email.setEditable(false);
        getContentPane().add(email);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(40, 150, 80, 20);
        getContentPane().add(cityLabel);

        city = new JTextField((String) userData.get("city"));
        city.setBounds(40, 170, 150, 20);
        city.setEditable(true);
        getContentPane().add(city);

        JLabel interests = new JLabel("Interests:");
        interests.setBounds(40, 200, 80, 20);
        getContentPane().add(interests);

        musicBox = new JCheckBox("Music", userInterests.contains("Music"));
        musicBox.setBounds(40, 220, 150, 20);
        getContentPane().add(musicBox);

        cinemaBox = new JCheckBox("Cinema", userInterests.contains("Cinema"));
        cinemaBox.setBounds(40, 240, 150, 20);
        getContentPane().add(cinemaBox);

        theaterBox = new JCheckBox("Theater", userInterests.contains("Theater"));
        theaterBox.setBounds(40, 260, 150, 20);
        getContentPane().add(theaterBox);

        sportsBox = new JCheckBox("Sports", userInterests.contains("Sports"));
        sportsBox.setBounds(40, 280, 150, 20);
        getContentPane().add(sportsBox);

        cultureBox = new JCheckBox("Culture", userInterests.contains("Culture"));
        cultureBox.setBounds(40, 300, 150, 20);
        getContentPane().add(cultureBox);

        artBox = new JCheckBox("Arts", userInterests.contains("Arts"));
        artBox.setBounds(40, 320, 150, 20);
        getContentPane().add(artBox);

        foodBox = new JCheckBox("Food", userInterests.contains("Food"));
        foodBox.setBounds(40, 340, 150, 20);
        getContentPane().add(foodBox);

        festivalsBox = new JCheckBox("Festivals", userInterests.contains("Festivals"));
        festivalsBox.setBounds(40, 360, 150, 20);
        getContentPane().add(festivalsBox);

        moreBox = new JCheckBox("More..", userInterests.contains("More.."));
        moreBox.setBounds(40, 380, 150, 20);
        getContentPane().add(moreBox);

        saveButton = new JButton("Save changes");
        saveButton.setBounds(120, 430, 140, 25);
        getContentPane().add(saveButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();
        if (button == saveButton) {
            String interests = "";
            if (musicBox.isSelected()) interests += "Music ";
            if (theaterBox.isSelected()) interests += "Theater ";
            if (cinemaBox.isSelected()) interests += "Cinema ";
            if (sportsBox.isSelected()) interests += "Sports ";
            if (artBox.isSelected()) interests += "Arts ";
            if (cultureBox.isSelected()) interests += "Culture ";
            if (foodBox.isSelected()) interests += "Food ";
            if (festivalsBox.isSelected()) interests += "Festival ";
            if (moreBox.isSelected()) interests += "More ";
            users.update((DBObject) JSON.parse("{'email':'"+ email.getText() + "'}"), (DBObject) JSON.parse("{'$set':{'city':'" + city.getText() + "'}}"));
            users.update((DBObject) JSON.parse("{'email':'"+ email.getText() + "'}"), (DBObject) JSON.parse("{'$set':{'interests':'" + interests + "'}}"));
        }
    }

    private static DBObject getUserData(String email) {
        DBObject query = new BasicDBObject("email", email);
        DBCursor cursor = users.find(query);
        DBObject userData = cursor.one();
        return userData;
    }

}
