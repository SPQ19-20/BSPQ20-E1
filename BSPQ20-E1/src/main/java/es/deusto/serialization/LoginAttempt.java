package es.deusto.serialization;

public class LoginAttempt {

    private String email;
    private String password;
    private boolean organizer;

    public LoginAttempt() {
        this.email = "";
        this.password = "";
        this.organizer = false;
    }

    public LoginAttempt(String email, String password, boolean organizer) {
        this.email = email;
        this.password = password;
        this.organizer = organizer;
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

    public boolean isOrganizer() {
        return this.organizer;
    }

    public void setOrganizer(boolean organizer) {
        this.organizer = organizer;
    }

    public String toString() {
        return "LoginAttempt[email=" + email + ", password=" + password + "]";
    }
}