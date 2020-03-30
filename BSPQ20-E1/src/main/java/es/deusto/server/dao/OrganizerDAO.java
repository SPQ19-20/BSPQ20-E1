package es.deusto.server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.Organizer;

public class OrganizerDAO {

    private PersistenceManagerFactory pmf;
	
	protected OrganizerDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }
    
    public Organizer getOrganizer(String email) {
        PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(4);
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
			
			pm.close();
		}
		
		return organizer;
    }

	public void storeOrganizer(Organizer organizer) {
		PersistenceManager pm = pmf.getPersistenceManager();
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
			
			pm.close();
		}
    }
    
    public void updateOrganizer(Organizer organizer) {
		storeOrganizer(organizer);
	}

}