package es.deusto.server.data;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import es.deusto.serialization.EventInfo;
import es.deusto.server.dao.DAOFactory;
import es.deusto.server.dao.OrganizerDAO;
import es.deusto.server.dao.PostDAO;
import es.deusto.server.dao.TopicDAO;

@PersistenceCapable(detachable = "true")
public class Event {

	private String name;
	private String description;
	private int interested;
	private String city, country;

	@Persistent(defaultFetchGroup = "true")
	private Date date;

	@Persistent(defaultFetchGroup = "true")
	private Topic topic;

	@Persistent(defaultFetchGroup = "true")
	private Organizer organizer;

	private ArrayList<Post> posts;
	// public User(String code, String name) {
	// this.code = code;
	// this.name = name;
	// }

	public Event() {
		this.posts = new ArrayList<>();
	}

	public Event(EventInfo info) {
		this.name = info.getName();
		this.description = info.getDescription();
		TopicDAO tdao = DAOFactory.getInstance().createTopicDAO();
		this.topic = tdao.getTopic(info.getTopic().getName());
		if (this.topic == null) {
			this.topic = new Topic(info.getTopic());
		}
		OrganizerDAO odao = DAOFactory.getInstance().createOrganizerDAO();
		this.organizer = odao.getOrganizer(info.getOrganizerEmail());
		this.interested = info.getInterested();
		this.city = info.getCity();
		this.country = info.getCountry();
		this.date = info.getDate();

		DAOFactory.getInstance().closeDAO(tdao);
        DAOFactory.getInstance().closeDAO(odao);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public Organizer getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getInterested() { 
		return interested; 
	}

	public void setInterested(int interested) { 
		this.interested = interested; 
	}

	public void addInterested() { 
		interested++; 
	}

	public void reduceInterested() { 
		interested--; 
	}

	public ArrayList<Post> getPosts() {
		PostDAO pdao = DAOFactory.getInstance().createPostDAO();
		posts = pdao.getPostsByEvent(this);
		DAOFactory.getInstance().closeDAO(pdao);
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	
	@Override
	public String toString() {
		return "Event [ name=" + name + ", description= "+ description +", Topic= "+ topic.getName() +", organizer= "+ organizer + ", interested= "+ interested +" ]";
	}
}
