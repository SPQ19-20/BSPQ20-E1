package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import es.deusto.serialization.EventInfo;
import es.deusto.server.dao.DAOFactory;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)
public class Event{

	private String name;
	private String description;
	
	@Persistent(defaultFetchGroup="true")
	private Topic topic;
	
	@Persistent(defaultFetchGroup="true")
	private Organizer organizer; 
	// public User(String code, String name) {
	// 	this.code = code;
	// 	this.name = name;
	// }

	public Event(EventInfo info) {
		this.name = info.getName();
		this.description = info.getDescription();
		this.topic = new Topic(info.getTopic());
		this.organizer = DAOFactory.getInstance().createOrganizerDAO().getOrganizer(info.getOrganizer());
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
	@Override
	public String toString() {
		return "Event [ name=" + name + ", description= "+ description +", Topic= "+ topic.getName() +", organizer= "+ organizer + "]";
	}
	
}
