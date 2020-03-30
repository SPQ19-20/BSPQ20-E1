package es.deusto.server.server;

import es.deusto.serialization.LoginAttempt;
import es.deusto.serialization.SignupAttempt;
import es.deusto.server.dao.DAOFactory;
import es.deusto.server.dao.OrganizerDAO;
import es.deusto.server.dao.UserDAO;
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
     * In order to acces to the database, use the
     * DAO objects (create them using DAOFactory class).
    */

    public AppService() {
        
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
        UserDAO dao = DAOFactory.getInstance().createUserDAO();
        
        // 1. Make sure the email is not in use
        User user = dao.getUser(signup.getEmail());
        if (user != null) {
            return null;
        }

        // 2. Create the user in the DB
        user = signup.buildUser();
        dao.storeUser(user);
        
        return user;
    }

    public Organizer attemptOrganizerSignup(SignupAttempt signup) {
        OrganizerDAO dao = DAOFactory.getInstance().createOrganizerDAO();
        
        // 1. Make sure the email is not in use
        Organizer organizer = dao.getOrganizer(signup.getEmail());
        if (organizer != null) {
            return null;
        }

        // 2. Create the user in the DB
        organizer = signup.buildOrganizer();
        dao.storeOrganizer(organizer);
        
        return organizer;
    }

    public void recoverPassword(LoginAttempt login) {
        String email = login.getEmail();

        System.out.println("Sending new password to " + email);
    }


    // --------------------------------------------------------------

    // method used for manual testing 
    public void hello() {
        // try
        // {	
        //     tx.begin();
        //     System.out.println("Persisting users");

        //     Organizer orga = new Organizer();
        //     orga.setName("Norman");
        //     orga.setEmail("norman@EPCsol.es");
        //     orga.setPassword("1234easy");
        //     orga.setOrganization("EPC solutions");

        //     es.deusto.server.data.Channel cinema = new es.deusto.server.data.Channel();
        //     cinema.setName("cinema");
            
        //     es.deusto.server.data.Event popcorn = new es.deusto.server.data.Event();
        // 	popcorn.setName("Popcorn Party (PP)");
        // 	popcorn.setDescription("a popcorn party");
        // 	popcorn.setChannel(cinema);
        // 	popcorn.setOrganizer(orga);
        	
            	
        //     User kiraYoshikage = new User();
        //     kiraYoshikage.setName("Kira");
        //     kiraYoshikage.setEmail("Kira@killerqueen.es");
        //     kiraYoshikage.setPassword("4567Hard");
        //     kiraYoshikage.setCity("Morioh");
        // 	kiraYoshikage.addEvent(popcorn);
            	
		// 	pm.makePersistent(orga);
		// 	pm.makePersistent(cinema);	
		// 	pm.makePersistent(popcorn);	
		// 	pm.makePersistent(kiraYoshikage);
			
        //     tx.commit();
        //     System.out.println("User and his messages have been persisted");
        // }
        // finally
        // {
        //     if (tx.isActive())
        //     {
        //         tx.rollback();
        //     }
        //     pm.close();
        // }
        // System.out.println("");
    }

}