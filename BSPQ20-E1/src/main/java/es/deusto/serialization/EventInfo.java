package es.deusto.serialization;

import es.deusto.server.data.Event;

public class EventInfo {

    private String name;
	private String description;
	private ChannelInfo channel;
    private String organizer; 
    
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
        this.organizer = e.getOrganizer().getName();
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

    public String getOrganizer() {
        return this.organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}