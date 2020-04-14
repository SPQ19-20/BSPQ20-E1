package es.deusto.serialization;

import es.deusto.server.data.Event;

public class EventInfo {

    private String name;
	private String description;
	private TopicInfo topic;
    private String organizer; 
    
    public EventInfo() {
        this.name = "";
        this.description = "";
        this.topic = null;
        this.organizer = null;
    }

    public EventInfo(Event e) {
        this.name = e.getName();
        this.description = e.getDescription();
        this.topic = new TopicInfo(e.getTopic());
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

    public TopicInfo getTopic() {
        return this.topic;
    }

    public void setTopic(TopicInfo topic) {
        this.topic = topic;
    }

    public String getOrganizer() {
        return this.organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}