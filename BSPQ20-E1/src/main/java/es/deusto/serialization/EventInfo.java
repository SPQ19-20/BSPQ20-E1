package es.deusto.serialization;

import es.deusto.server.data.Event;

public class EventInfo {

    private String name;
	private String description;
	private ChannelInfo channel;
    private OrganizerInfo organizer; 
    
    public EventInfo() {
        this.name = "";
        this.description = "";
        this.channel = null;
        this.organizer = null;
    }

    public EventInfo(Event e) {
        this.name = e.getName();
        this.description = e.getDescription();
        this.channel = new ChannelInfo(e.getChannel());
        this.organizer = new OrganizerInfo(e.getOrganizer());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    } 

    public ChannelInfo getChannel() {
        return this.channel;
    }

    public void setChannel(ChannelInfo channel) {
        this.channel = channel;
    }

    public OrganizerInfo getOrganizer() {
        return this.organizer;
    }

    public void setOrganizer(OrganizerInfo organizer) {
        this.organizer = organizer;
    }
}