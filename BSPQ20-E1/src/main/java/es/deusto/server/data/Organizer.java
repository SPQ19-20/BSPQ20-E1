package es.deusto.server.data;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
// import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;

import es.deusto.server.dao.DAOFactory;
import es.deusto.server.dao.EventDAO;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)

/**
 * This classes is the one that defines an organizer and it is 
 * used to create one.	 
 */

public class Organizer extends GenericUser {
	
	private String organization;

	// @Join
    // @Element(dependent = "false")
	// @Persistent(defaultFetchGroup="true")
	@NotPersistent
	private ArrayList<Event> createdEvents = new ArrayList<>();

	public Organizer() {
		this.createdEvents = new ArrayList<>();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public ArrayList<Event> getCreatedEvents() {
		EventDAO dao = DAOFactory.getInstance().createEventDAO();
		this.createdEvents = dao.getEventsByOrganizer(this);
		DAOFactory.getInstance().closeDAO(dao);
		return this.createdEvents;
	}

	/**
	 * @param createdEvents the createdEvents to set
	 */
	public void setCreatedEvents(ArrayList<Event> createdEvents) {
		this.createdEvents = createdEvents;
	}

	public void addCreatedEvent(Event e) {
		this.createdEvents.add(e);
	}

	@Override
	public String toString() {
		return "Organizer [organization=" + organization + "]";
	}
	
}
