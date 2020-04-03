package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)
public class Event{

	private String name;
	private String description;
	
	@Persistent(defaultFetchGroup="true")
	private Channel channel;
	
	@Persistent(defaultFetchGroup="true")
	private Organizer organizer; 
	// public User(String code, String name) {
	// 	this.code = code;
	// 	this.name = name;
	// }

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

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public Organizer getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}
	@Override
	public String toString() {
		return "Event [ name=" + name + ", description= "+ description +", channel= "+ channel.toString() +", organizer= "+ organizer + "]";
	}
	
}
