package es.deusto.server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.Topic;

public class TopicDAO {

    private PersistenceManagerFactory pmf;
	
	protected TopicDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }
    
    public Topic getTopic(String name) {
        PersistenceManager pm = pmf.getPersistenceManager();
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
			
			pm.close();
		}
		
		return topic;
    }

	public void storeTopic(Topic topic) {
		PersistenceManager pm = pmf.getPersistenceManager();
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
			
			pm.close();
		}
    }
    
    public void updateTopic(Topic topic) {
		storeTopic(topic);
	}

}