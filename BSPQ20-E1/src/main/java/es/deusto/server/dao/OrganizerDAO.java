package es.deusto.server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.Organizer;

/**
 * This classes is the one used to create an organizer DAO.	 
 */

public class OrganizerDAO {

    private PersistenceManager pm;
	
	protected OrganizerDAO(PersistenceManager pm) {
		this.pm = pm;
    }
    
    public Organizer getOrganizer(String email) {
		pm.getFetchPlan().setMaxFetchDepth(40);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
		Organizer organizer = null;
		
		try {
			tx.begin();
			
			Extent<Organizer> extent = pm.getExtent(Organizer.class, true);
			for (Organizer o : extent) {
				if (o.getEmail().equals(email)) {
					organizer = o;
					break;
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
		
		return organizer;
    }

	public void storeOrganizer(Organizer organizer) {
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			
			pm.makePersistent(organizer);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}
    }
    
    public void updateOrganizer(Organizer organizer) {
		storeOrganizer(organizer);
	}

	public void deleteOrganizer(String email) {
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		Organizer organizer = getOrganizer(email);

		try {
			tx.begin();

			pm.deletePersistent(organizer);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
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