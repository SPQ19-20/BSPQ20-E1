package es.deusto.server.dao;

import java.util.ArrayList;
import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import es.deusto.server.data.Event;
import es.deusto.server.data.Organizer;

public class EventDAO {
	private PersistenceManagerFactory pmf;
	
	protected EventDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }
    
	/**
	 * Retrieves List of events from the database given the full name or part of it.
	 * @param name name of the event
	 * @return list of events from the database
	 */
    public ArrayList<Event> getEvents(String name) {
        PersistenceManager pm = pmf.getPersistenceManager();
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
				}else{
					System.out.println("no events found with given name");
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
			
			pm.close();
		}
		
		return event;
	}
	
	/**
	 * Retrieves List of events from the database given the Topic name.
	 * @param topic name of the topic
	 * @return list of events from the database
	 */
    public ArrayList<Event> getEventsbyTopic(String topic) {
        PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
	
		ArrayList<Event> event = new ArrayList<Event>();
		try {
			tx.begin();
			
			Extent<Event> extent = pm.getExtent(Event.class, true);
			for (Event u : extent) {
				if (u.getTopic().getName().contains(topic)){
					event.add(u); //adds the event to the list.
				}else{
					System.out.println("no events found with given name");
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
			
			pm.close();
		}
		
		return event;
	}

	/**
	 * Retrieves List of the events of a specific Organizer
	 * @param Organizer
	 * @return list of events from the database
	 */
    public ArrayList<Event> getEventsByOrganizer(Organizer organizer) {
        PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
	
		ArrayList<Event> event = new ArrayList<Event>();
		try {
			tx.begin();
			
			Extent<Event> extent = pm.getExtent(Event.class, true);
			for (Event u : extent) {
				if (u.getOrganizer().getEmail().equals(organizer.getEmail())){
					event.add(u); //adds the event to the list.
				}else{
					System.out.println("no events found with given name");
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
			
			pm.close();
		}
		
		return event;
	}

	/**
	 * Retrieves a list of all the events from the database
	 */
	public ArrayList<Event> getEvents() {
        PersistenceManager pm = pmf.getPersistenceManager();
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
			
			pm.close();
		}
		
		return event;
    }

	/**
	 * Stores a event in the database
	 * @param event event to be submited
	 */
	public void storeEvent(Event event) {
		PersistenceManager pm = pmf.getPersistenceManager();
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
			pm.close();
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
		PersistenceManager pm = pmf.getPersistenceManager();
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
			pm.close();
		}	
		
	}
}
