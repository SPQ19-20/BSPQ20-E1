package es.deusto.serialization;

import java.util.ArrayList;

import es.deusto.server.data.Event;
import es.deusto.server.data.Organizer;
import es.deusto.server.data.User;

public class SignupAttempt {

    // Common atributes (Both normal Users and Organizers have them)
    private String email, password, name;
    private ArrayList<TopicInfo> interests;
    private ArrayList<EventInfo> savedEvents;//information about the saved events.
    
    // Normal user
    private String city, country;
    
    // Organizer
    private String organization;
    
    public SignupAttempt() {
        this.email = "";
        this.password = "";
        this.name = "";
        this.city = "";
        this.country = "";
        this.organization = "";
        this.interests = new ArrayList<TopicInfo>(); //the signup works with the objects of its own package
        this.savedEvents = new ArrayList<EventInfo>();
    }

    public SignupAttempt(UserInfo userInfo) {
        this.email = userInfo.getEmail();
        this.password = "";
        this.name = userInfo.getName();
        this.city = userInfo.getCity();
        this.country = userInfo.getCountry();
        this.organization = "";
        this.interests = userInfo.getInterests();//the signup works with the objects of its own package
        this.savedEvents = userInfo.getSavedEvents();
    }

    // getters and setters
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public ArrayList<TopicInfo> getInterests() { 
        return this.interests; 
    }

    public void setInterests(ArrayList<TopicInfo> interests) {
         this.interests = interests; 
    }

    public ArrayList<EventInfo> getSavedEvents() { 
        return this.savedEvents; 
    }

    public void setSaveEvents(ArrayList<EventInfo> savedEvents) {
         this.savedEvents = savedEvents; 
    }

    /**
     * Builds an instance of the business object {@link User}.
     * The main purpose of this function is prevent object repetition. It's only used in the server.
     * @return instance of es.deusto.server.data.User.java
     */
    public User buildUser() {
        User user = new User();
        
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setCity(this.city);
        user.setCountry(this.country);
        // TODO DOUBT!! maybe we need to create the Arraylist<Topic> first
        //if the following line of code is uncommented please add the import for TOPIC !
        //user.setInterests(new ArrayList<>());
        for (TopicInfo interest : interests) {
            user.addInterest(TopicInfo.parseTopic(interest));
        }
        for (EventInfo eventInfo : savedEvents) {
            user.addEvent(new Event(eventInfo));
        }
        return user;
    }
    /**
     * Builds an instance of the business object {@link Organizer}. 
     * The main purpose of this function is prevent object repetition. It's only used in the server.
     * @return instance of {@link Organizer}.
     */
    public Organizer buildOrganizer() {
        Organizer organizer = new Organizer();
        
        organizer.setEmail(this.email);
        organizer.setName(this.name);
        organizer.setPassword(this.password);
        organizer.setOrganization(this.organization);
       // organizer.setInterests(this.interests);

        return organizer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String toString() {
        return "SignupAttempt[email=" + email + ", password=" + password + "]";
    }
}