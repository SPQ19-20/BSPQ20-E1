package es.deusto.serialization;

import es.deusto.server.data.Organizer;
import es.deusto.server.data.User;

public class SignupAttempt {

    // Common atributes (Both normal Users and Organizers have them)
    private String email, password, name, interests;

    
    // Normal user
    private String city;
    
    // Organizer
    private String organization;
    
    public SignupAttempt() {
        this.email = "";
        this.password = "";
        this.name = "";
        this.city = "";
        this.organization = "";
        this.interests = "";
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

    public String getInterests() { return this.interests; }

    public void setInterests(String interests) { this.interests = interests; }


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
        user.setInterests(this.interests);

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
        organizer.setInterests(this.interests);

        return organizer;
    }

    public String toString() {
        return "SignupAttempt[email=" + email + ", password=" + password + "]";
    }
}