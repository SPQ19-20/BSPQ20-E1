package es.deusto.serialization;

/**
 * This class encapsulates all the information required when a user 
 * (Either normal User or Organizer) logs in.
 * 
 * @since Sprint 1
 */
public class LoginAttempt {

    private String email;
    private String password;
    private boolean organizer;

    public LoginAttempt() {
        this.email = "";
        this.password = "";
        this.organizer = false;
    }

    /**
     * Constructor of a Log in attempt. Encapsulates the information returned by the server when a user (Either normal User or Organizer) Logs in.
     * @param email email of the user that will log.
     * @param password password of the account.
     * @param organizer boolean parameter that states if this user is an organizer or not.
     */
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