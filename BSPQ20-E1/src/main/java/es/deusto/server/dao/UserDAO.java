package es.deusto.server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.User;

public class UserDAO {

    private PersistenceManagerFactory pmf;
	
	private UserDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }
    
    public User getUser(String email) {
        PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
		User user = null;
		
		try {
			tx.begin();
			
			Extent<User> extent = pm.getExtent(User.class, true);
			for (User u : extent) {
				if (u.getEmail().equals(email)) {
					user = u;
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
		
		return user;
    }

	public void storeUser(User user) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			
			pm.makePersistent(user);
			
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
    
    public void updateUser(User user) {
		storeUser(user);
	}

}