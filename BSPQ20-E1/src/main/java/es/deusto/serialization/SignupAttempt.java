package es.deusto.serialization;

public class SignupAttempt {

    private String email;
    private String password;
    private String name;
    private String city;

    public SignupAttempt() {
        this.email = "";
        this.password = "";
        this.name = "";
        this.city = "";
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

    public String toString() {
        return "SignupAttempt[email=" + email + ", password=" + password + "]";
    }
}