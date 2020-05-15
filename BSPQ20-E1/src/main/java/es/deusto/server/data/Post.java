package es.deusto.server.data;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import es.deusto.serialization.PostInfo;

@PersistenceCapable(detachable="true")

/**
 * This classes is the one that defines a post and it is 
 * used to create one.	 
 */

public class Post {

    @Persistent(defaultFetchGroup="true")
    private String title;
    
    @Persistent(defaultFetchGroup="true")
    private String description;
    
    @Persistent(defaultFetchGroup="true")
    private Date date;

    @Persistent(defaultFetchGroup="true")
    private String eventName;

    @Persistent(defaultFetchGroup="true")
    private String organizerEmail;

    public Post() {
        this.title = "";
        this.description = "";
        this.date = null;
        this.eventName = "";
        this.organizerEmail = "";
    }

    public Post(PostInfo info) {
        this.title = info.getTitle();
        this.description = info.getDescription();
        this.date = info.getDate();
        this.eventName = info.getEventName();
        this.organizerEmail = info.getOrganizerEmail();
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