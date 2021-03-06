package es.deusto.server.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import es.deusto.server.data.Event;
import es.deusto.server.data.Organizer;
import es.deusto.server.data.Topic;
import es.deusto.server.data.User;

/**
 * This classes is the one used to create an event DAO.	 
 */

public class EventDAO {
	private PersistenceManager pm;
	
	protected EventDAO(PersistenceManager pm) {
		this.pm = pm;
    }
    
	/**
	 * Retrieves List of events from the database given the full name or part of it.
	 * @param name name of the event
	 * @return list of events from the database
	 */
    public ArrayList<Event> getEvents(String name) {
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
	
		ArrayList<Event> event = new  ArrayList<Event>();
		try {
			tx.begin();
			
			Extent<Event> extent = pm.getExtent(Event.class, true);
			for (Event u : extent) {
				if (u.getName().contains(name)){
					event.add(u); //adds the event to the list.
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}
		
		return event;
	}
	
	/**
	 * Retrieves List of events from the database that match the user's interests and location.
	 * this is use to create recomendation lists. 
	 * @param user User
	 * @return list of events from the database
	 */
    public ArrayList<Event> getEventsbyUser(User user) {
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();

		ArrayList<Event> event = new ArrayList<Event>();
		try {
			tx.begin();
			pm.flush();

			Extent<Event> extent = pm.getExtent(Event.class, true);

			//get the events that match with the user's interests(topics) and location.
			Date now = new Date();
			for (Event u : extent) {
				for (Topic topic : user.getInterests()) {
					if (u.getTopic().getName().equals(topic.getName()) && 
						u.getCountry().equals(user.getCountry()) && 
						(u.getDate().equals(now) || u.getDate().after(now))) {
						event.add(u); //adds the event to the list.
						break;
					}
				}
			}
		
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}
		
		return event;
	}


	/**
	 * Retrieves List of the events of a specific Organizer
	 * @param organizer
	 * @return list of events from the database
	 */
    public ArrayList<Event> getEventsByOrganizer(Organizer organizer) {
		if (organizer == null) return new ArrayList<>();

		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
	
		ArrayList<Event> event = new ArrayList<Event>();
		try {
			tx.begin();
			
			Extent<Event> extent = pm.getExtent(Event.class, true);
			for (Event u : extent) {
				if (u.getOrganizer() == null) continue;
				if (u.getOrganizer().getEmail().equals(organizer.getEmail())){
					event.add(u); //adds the event to the list.
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}

		return event;
	}

	/**
	 * Retrieves a list of all the events from the database
	 */
	public ArrayList<Event> getEvents() {
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
	
		ArrayList<Event> event = new ArrayList<Event>();
		try {
			tx.begin();
			
			Extent<Event> extent = pm.getExtent(Event.class, true);
			for (Event u : extent) {
				event.add(u); //adds the event to the list.
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}
		
		return event;
    }

	/**
	 * Stores a event in the database
	 * @param event event to be submited
	 */
	public void storeEvent(Event event) {
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			
			pm.makePersistent(event);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}
    }
    /**
	 * Updates an event
	 * @param event
	 */
    public void updateEvent(Event event) {
		storeEvent(event);
	}

	/**
	 * deletes an event from the database.
	 * @param event event to be deleted
	 */
	public void deleteEvent(Event event){
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();

		Event e = getEvents(event.getName()).get(0);
	
		try {
			tx.begin();

			pm.deletePersistent(e);

			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}	
		
	}

	protected PersistenceManager getPersistenceManager() {
		return this.pm;
	}

	protected void close() {
		this.pm = null;
	}
}
