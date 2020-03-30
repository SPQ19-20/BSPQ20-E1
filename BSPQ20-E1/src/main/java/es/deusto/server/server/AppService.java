package es.deusto.server.server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import es.deusto.serialization.LoginAttempt;
import es.deusto.serialization.SignupAttempt;

import es.deusto.server.data.GenericUser;
import es.deusto.server.data.Organizer;
import es.deusto.server.data.User;

public class AppService {

    /**
     * Application Service class
     * 
     * This class will include methods representing
     * the different functionalities of the server. 
     * 
     * This class should be called by the Server class,
     * so that the Server is abstracted from the
     * business logic of the project.
     * 
    */

    private PersistenceManager pm = null;
	private Transaction tx = null;

    public AppService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
    }

    public User attemptNormalLogin(LoginAttempt login) {
        // fetch the database and return the user (if found)
        return null;
    }

    public Organizer attemptOrganizerLogin(LoginAttempt login) {
        // fetch the database and return the organizer (if found)
        return null;
    }

    public User attemptNormalSignup(SignupAttempt signup) {
        
        return null;
    }

    public Organizer attemptOrganizerSignup(SignupAttempt signup) {
        return null;
    }

    public void recoverPassword(LoginAttempt login) {
        String email = login.getEmail();

        System.out.println("Sending new password to " + email);
    }


    // --------------------------------------------------------------

    // method used for manual testing 
    public void hello() {
        try
        {	
            tx.begin();
            System.out.println("Persisting users");

            Organizer orga = new Organizer();
            orga.setName("Norman");
            orga.setEmail("norman@EPCsol.es");
            orga.setPassword("1234easy");
            orga.setOrganization("EPC solutions");

            es.deusto.server.data.Channel cinema = new es.deusto.server.data.Channel();
            cinema.setName("cinema");
            
            es.deusto.server.data.Event popcorn = new es.deusto.server.data.Event();
        	popcorn.setName("Popcorn Party (PP)");
        	popcorn.setDescription("a popcorn party");
        	popcorn.setChannel(cinema);
        	popcorn.setOrganizer(orga);
        	
            	
            User kiraYoshikage = new User();
            kiraYoshikage.setName("Kira");
            kiraYoshikage.setEmail("Kira@killerqueen.es");
            kiraYoshikage.setPassword("4567Hard");
            kiraYoshikage.setCity("Morioh");
        	kiraYoshikage.addEvent(popcorn);
            	
			pm.makePersistent(orga);
			pm.makePersistent(cinema);	
			pm.makePersistent(popcorn);	
			pm.makePersistent(kiraYoshikage);
			
            tx.commit();
            System.out.println("User and his messages have been persisted");
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        System.out.println("");
    }

}