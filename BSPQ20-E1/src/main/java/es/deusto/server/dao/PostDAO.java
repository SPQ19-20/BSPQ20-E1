package es.deusto.server.dao;

import java.util.ArrayList;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.Event;
import es.deusto.server.data.Post;

public class PostDAO {
    private PersistenceManagerFactory pmf;
	
	protected PostDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }
    
    public ArrayList<Post> getPostsByEvent(Event event) {
        PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(4);
		pm.setDetachAllOnCommit(true);
		
		Transaction tx = pm.currentTransaction();
		
		ArrayList<Post> posts = new ArrayList<>();
		
		try {
			tx.begin();
			
			Extent<Post> extent = pm.getExtent(Post.class, true);
			for (Post p : extent) {
				if (p.getEventName().equals(event.getName())) {
					posts.add(p);
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
		
		return posts;
    }

	public void storePost(Post post) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			
			pm.makePersistent(post);
			
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
	
    public void updatePost(Post post) {
		storePost(post);
	}

	public void deletePost(Post post) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		Post toDelete = null;
		for (Post p: getPostsByEvent(DAOFactory.getInstance().createEventDAO().getEvents(post.getEventName()).get(0))) {
			if (p.getTitle().equals(post.getTitle())) {
				toDelete = p;
				break;
			}
		}

		try {
			tx.begin();

			pm.deletePersistent(toDelete);

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
}   