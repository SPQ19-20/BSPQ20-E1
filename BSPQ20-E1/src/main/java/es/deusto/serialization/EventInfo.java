package es.deusto.serialization;

import java.util.ArrayList;

import es.deusto.server.data.Event;
import es.deusto.server.data.Post;

public class EventInfo {

    private String name;
	private String description;
	private TopicInfo topic;
    private String organizerEmail;
    private int interested;
    
    private ArrayList<PostInfo> posts;
    
    public EventInfo() {
        this.name = "";
        this.description = "";
        this.topic = null;
        this.organizerEmail = null;
        this.posts = new ArrayList<>();
        this.interested = 0;
    }

    public EventInfo(Event e) {
        this.name = e.getName();
        this.description = e.getDescription();
        this.topic = new TopicInfo(e.getTopic());
        this.organizerEmail = e.getOrganizer().getEmail();
        ArrayList<Post> actualPosts = e.getPosts();
        this.posts = new ArrayList<>();
        if (actualPosts.isEmpty()) return;
        for (Post p: actualPosts) {
            this.posts.add(new PostInfo(p));
        }
        this.interested = e.getInterested();
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

    public String getOrganizerEmail() {
        return this.organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public ArrayList<PostInfo> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostInfo> posts) {
        this.posts = posts;
    }

    public int getInterested() { return interested; }

    public void setInterested(int interested) { this.interested = interested; }

    public void addInterested() { interested++; }

    public void reduceInterested() { interested--; }
}