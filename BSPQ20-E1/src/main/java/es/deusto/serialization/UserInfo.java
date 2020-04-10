package es.deusto.serialization;

import java.util.ArrayList;

import es.deusto.server.data.Event;
import es.deusto.server.data.User;
    /**
     * All the ...Info classes work as a middleman for the client and server bussines objects.
     * Due to the functionality of this classes their structure is similar to the 'original' ones in the server
     * Examples: UserInfo -> User
     *           EventInfo -> Event
     * Also the ...Info clases are the ones shown in the GUI.
     */
public class UserInfo {

    private String name, email, city, interests;

    private ArrayList<EventInfo> savedEvents;

    public UserInfo(String name, String email, String city, String interests) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.savedEvents = new ArrayList<>();
        this.interests = interests;
    }

    public UserInfo() {
        this.name = "";
        this.email = "";
        this.city = "";
        this.savedEvents = new ArrayList<>();
        this.interests = "";
    }

    public UserInfo(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.city = user.getCity();
        this.savedEvents = new ArrayList<>();
        for (Event e: user.getSavedEvents()) {
            this.savedEvents.add(new EventInfo(e));
        }
        this.interests = user.getInterests();
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

    public ArrayList<EventInfo> getSavedEvents() {
        return this.savedEvents;
    }

    public void setSavedEvents(ArrayList<EventInfo> savedEvents) {
        this.savedEvents = savedEvents;
    }

    public String getInterests() { return this.interests; }

    public void setInterests(String interests) { this.interests = interests; }

}