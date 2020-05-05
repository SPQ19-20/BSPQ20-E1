package es.deusto.server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.User;

public class UserDAO {

    private PersistenceManager pm;
	
	protected UserDAO(PersistenceManager pm) {
		this.pm = pm;
    }
    
    public User getUser(String email) {
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
		}
		
		return user;
    }

	public void storeUser(User user) {
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
		}
    }
	
    public void updateUser(User user) {
		storeUser(user);
	}

	public void deleteUser(String email) {
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		User user = getUser(email);

		try {
			tx.begin();

			pm.deletePersistent(user);

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