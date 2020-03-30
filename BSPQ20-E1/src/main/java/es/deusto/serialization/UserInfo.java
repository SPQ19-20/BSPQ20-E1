package es.deusto.serialization;

import es.deusto.server.data.User;

public class UserInfo {

    private String name, email;
    private String city;

    public UserInfo(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.city = user.getCity();
    }

    public String getName() {
        return this.name;
    }    

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}