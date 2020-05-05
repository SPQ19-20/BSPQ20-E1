package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import es.deusto.server.data.GenericUser;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Join;
import java.util.ArrayList;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.COMPLETE_TABLE)
public class User extends GenericUser {
	// atributes
	private String city, country;

	@Join
	@Element(dependent = "false")
	@Persistent(defaultFetchGroup = "true")
	private ArrayList<Event> savedEvents = new ArrayList<Event>();

	@Join
	@Element(dependent = "false")
	@Persistent(defaultFetchGroup = "true")
	private ArrayList<Topic> interests = new ArrayList<>();

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
	public ArrayList<Topic> getInterests() { 
		return interests; 
	}

	public void setInterests(ArrayList<Topic> topics) {
		 this.interests = topics; 
	}

	public void addInterest(Topic topic) {
		this.interests.add(topic); 
   }  

	@Override
	public String toString() {
		return "User [name= " + getName() +" city=" + city +", Saved Events= " +savedEvents.toString() + " interests= " + interests.toString()+ "]";
	}
	
}
