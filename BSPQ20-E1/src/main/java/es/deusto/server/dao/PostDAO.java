package es.deusto.server.dao;

import java.util.ArrayList;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.server.data.Event;
import es.deusto.server.data.Post;

/**
 * This classes is the one used to create a DAO for a post.	 
 */

public class PostDAO {
    private PersistenceManager pm;
	
	protected PostDAO(PersistenceManager pm) {
		this.pm = pm;
    }
    
    public ArrayList<Post> getPostsByEvent(Event event) {
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
		}
		
		return posts;
    }

	public void storePost(Post post) {
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
		}
    }
	
    public void updatePost(Post post) {
		storePost(post);
	}

	public void deletePost(Post post) {
		pm.setDetachAllOnCommit(true);
		Transaction tx = pm.currentTransaction();
		
		Post toDelete = null;
		EventDAO edao = DAOFactory.getInstance().createEventDAO();
		for (Post p: getPostsByEvent(edao.getEvents(post.getEventName()).get(0))) {
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
		}	

		DAOFactory.getInstance().closeDAO(edao);
	}

	protected PersistenceManager getPersistenceManager() {
		return this.pm;
	}

	protected void close() {
		this.pm = null;
	}
	
}   