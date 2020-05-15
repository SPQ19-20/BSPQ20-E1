package es.deusto.server.dao;

import java.util.LinkedList;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * This class is used to create a DAO factory that is used by
 * all of the other DAO classes, to create them or to close them.
 */

public class DAOFactory {

    private static final int POOL_SIZE = 20;
    
    private LinkedList<PersistenceManager> connectionPool;

    private DAOFactory() {
        connectionPool = new LinkedList<>();
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

        for (int i = 0; i < POOL_SIZE; i++) {
            connectionPool.addLast(pmf.getPersistenceManager());
        }
    }

    private static DAOFactory instance = new DAOFactory();

    private synchronized PersistenceManager useConnectionFromPool(PersistenceManager pm) {
        if (pm == null) {
            // remove
                PersistenceManager ret;
            do {    
                ret = connectionPool.removeFirst();
                if (ret == null) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {}
                } else {
                    return ret;
                }
            } while (connectionPool.isEmpty());
        } else {
            // add
            connectionPool.addLast(pm);
            return null;
        }

        return null;
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO createUserDAO() {
        PersistenceManager pm = useConnectionFromPool(null);
        pm.refreshAll();
        return new UserDAO(pm);
    }

    public OrganizerDAO createOrganizerDAO() {
        PersistenceManager pm = useConnectionFromPool(null);
        pm.refreshAll();
        return new OrganizerDAO(pm);
    }

    public EventDAO createEventDAO(){
        PersistenceManager pm = useConnectionFromPool(null);
        pm.refreshAll();
        return new EventDAO(pm);
    }

    public TopicDAO createTopicDAO() {
        PersistenceManager pm = useConnectionFromPool(null);
        pm.refreshAll();
        return new TopicDAO(pm);
    }

    public PostDAO createPostDAO() {
        PersistenceManager pm = useConnectionFromPool(null);
        pm.refreshAll();
        return new PostDAO(pm);
    }

    public void closeDAO(PostDAO dao) {
        PersistenceManager pm = dao.getPersistenceManager();
        useConnectionFromPool(pm);
        dao.close();
    }

    public void closeDAO(TopicDAO dao) {
        PersistenceManager pm = dao.getPersistenceManager();
        useConnectionFromPool(pm);
        dao.close();
    }

    public void closeDAO(EventDAO dao) {
        PersistenceManager pm = dao.getPersistenceManager();
        useConnectionFromPool(pm);
        dao.close();
    }

    public void closeDAO(OrganizerDAO dao) {
        PersistenceManager pm = dao.getPersistenceManager();
        useConnectionFromPool(pm);
        dao.close();
    }

    public void closeDAO(UserDAO dao) {
        PersistenceManager pm = dao.getPersistenceManager();
        useConnectionFromPool(pm);
        dao.close();
    }
}