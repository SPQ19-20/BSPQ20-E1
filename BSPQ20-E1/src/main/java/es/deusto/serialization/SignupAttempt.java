package es.deusto.serialization;

import es.deusto.server.data.Organizer;
import es.deusto.server.data.User;

public class SignupAttempt {

    private String email;
    private String password;
    private String name;
    
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
    }

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

    public User buildUser() {
        User user = new User();
        
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setCity(this.city);

        return user;
    }

    public Organizer buildOrganizer() {
        Organizer organizer = new Organizer();
        
        organizer.setEmail(this.email);
        organizer.setName(this.name);
        organizer.setPassword(this.password);
        organizer.setOrganization(this.organization);

        return organizer;
    }

    public String toString() {
        return "SignupAttempt[email=" + email + ", password=" + password + "]";
    }
}