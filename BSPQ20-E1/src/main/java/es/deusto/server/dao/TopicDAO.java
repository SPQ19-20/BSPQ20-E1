package es.deusto.server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.Topic;

/**
 * This classes is the one used to create a DAO a topic.	 
 */

public class TopicDAO {

    private PersistenceManager pm;
	
	protected TopicDAO(PersistenceManager pm) {
		this.pm = pm;
    }
    
    public Topic getTopic(String name) {
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
		Topic topic = null;
		
		try {
			tx.begin();
			
			Extent<Topic> extent = pm.getExtent(Topic.class, true);
			for (Topic u : extent) {
				if (u.getName().equals(name)) {
					topic = u;
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
		
		return topic;
    }

	public void storeTopic(Topic topic) {
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			
			pm.makePersistent(topic);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
		}
    }
    
    public void updateTopic(Topic topic) {
		storeTopic(topic);
	}

	public void deleteTopic(String name) {
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		Topic topic = getTopic(name);

		try {
			tx.begin();

			pm.deletePersistent(topic);

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