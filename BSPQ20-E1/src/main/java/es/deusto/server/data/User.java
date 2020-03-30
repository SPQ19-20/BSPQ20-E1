package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import es.deusto.server.data.GenericUser;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Join;
import java.util.ArrayList;
// import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)
public class User extends GenericUser {
	// TODO change the name of this class
	private String city;
	
	@Join
    @Element(dependent = "false")
    @Persistent(defaultFetchGroup="true")
	private ArrayList<Event> savedEvents= new ArrayList<Event>();
	

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	
	public ArrayList<Event> getSavedEvents() {
		return savedEvents;
	}

	public void setSavedEvents(ArrayList<Event> savedEvents) {
		this.savedEvents = savedEvents;
	}

	public void addEvent(Event event) {
		this.savedEvents.add(event);
	}
	@Override
	public String toString() {
		return "User [name= " + getName() +" city=" + city +", Saved Events= " +savedEvents.toString() + "]";
	}
	
}
