package es.deusto.serialization;

import java.util.Date;

import es.deusto.server.data.Post;

public class PostInfo {

    private String title;
    private String description;
    private Date date;
    private String eventName;
    private String organizerEmail;

    public PostInfo() {
        this.title = "";
        this.description = "";
        this.date = null;
        this.eventName = "";
        this.organizerEmail = "";
    }

    public PostInfo(Post post) {
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.date = post.getDate();
        this.eventName = post.getEventName();
        this.organizerEmail = post.getOrganizerEmail();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }
    

}