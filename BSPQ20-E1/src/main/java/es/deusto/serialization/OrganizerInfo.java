package es.deusto.serialization;

import java.util.ArrayList;

import es.deusto.server.data.Event;
import es.deusto.server.data.Organizer;

public class OrganizerInfo {

    private String name, email;
    private String organization;
    private ArrayList<EventInfo> createdEvents;

    public OrganizerInfo(Organizer organizer) {
        this.name = organizer.getName();
        this.email = organizer.getEmail();
        this.organization = organizer.getOrganization();
        ArrayList<Event> events = organizer.getCreatedEvents();
        this.createdEvents = new ArrayList<>();
        if (events.isEmpty()) return;
        for (Event e: events) {
            this.createdEvents.add(new EventInfo(e));
        }
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

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public ArrayList<EventInfo> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(ArrayList<EventInfo> createdEvents) {
        this.createdEvents = createdEvents;
    }

}