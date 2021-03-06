package es.deusto.server.server;

import es.deusto.serialization.EventInfo;
import es.deusto.serialization.LoginAttempt;
import es.deusto.serialization.PostInfo;
import es.deusto.serialization.SignupAttempt;
import es.deusto.serialization.TopicInfo;
import es.deusto.serialization.UserInfo;
import es.deusto.server.dao.TopicDAO;
import es.deusto.server.dao.DAOFactory;
import es.deusto.server.dao.EventDAO;
import es.deusto.server.dao.OrganizerDAO;
import es.deusto.server.dao.PostDAO;
import es.deusto.server.dao.UserDAO;
import es.deusto.server.data.Topic;
import es.deusto.server.data.Event;
import es.deusto.server.data.Organizer;
import es.deusto.server.data.Post;
import es.deusto.server.data.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.jdo.JDOHelper;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AppService {

    /**
     * This class will include methods representing the different functionalities of the server. 
     * This class should be called by the {@link Server} class, so that the Server is abstracted from the
     * business logic of the project.
     * In order to access to the database, use the DAO objects (create them using {@link DAOFactory} class).
    */

    public AppService() {}

    //--------------------------------------LOGIN USERS-----------------------------------------------------

    /**
     * This method is used for the login process of regular users.
     * Receives a {@link LoginAttempt} object with the email and password and compares it with the copy
     * stored in the database.
     * 
     * @param login {@link LoginAttempt} with the requester's email and password.
     * @return {@link User} object to which the login email corresponds. If the credentials are
     * invalid, null is returned.
     * @since Sprint 1
     */
    public User attemptNormalLogin(LoginAttempt login) {
        UserDAO dao = DAOFactory.getInstance().createUserDAO();

        // 1. Get user by email
        User user = dao.getUser(login.getEmail());
        if (user == null) {
            return null;
        }

        // 2. Check the password
        if (!user.getPassword().equals(login.getPassword())) {
            return null;
        }

        DAOFactory.getInstance().closeDAO(dao);

        return user;
    }

    /**
     * This method is used for the login process of organizers.
     * Receives a {@link LoginAttempt}  object with the email and password and compares it with the copy
     * stored in the database.
     * 
     * @param login {@link LoginAttempt} with the requester's email and password.
     * @return {@link Organizer} object to which the login email corresponds. If the credentials are
     * invalid, null is returned.
     * @since Sprint 2
     */
    public Organizer attemptOrganizerLogin(LoginAttempt login) {
        OrganizerDAO dao = DAOFactory.getInstance().createOrganizerDAO();

        // 1. Get organizer by email
        Organizer organizer = dao.getOrganizer(login.getEmail());
        if (organizer == null) {
            return null;
        }

        // 2. Check the password
        if (!organizer.getPassword().equals(login.getPassword())) {
            return null;
        }

        DAOFactory.getInstance().closeDAO(dao);
        return organizer;
    }

    //----------------------------------------SIGNUP USERS-----------------------------------------------------------------

    /**
     * This method is used for the signup process of regular users.
     * Receives a {@link SignupAttempt} object with the information of the user and tries to submit
     * it to the database.
     * 
     * @param signup {@link SignupAttempt} with the user's signup data (name, email, password and city).
     * @return {@link User} object that has been created as a result of the request. If the email is already in
     * use, null is returned.
     * @since Sprint 1
     */
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

        DAOFactory.getInstance().closeDAO(dao);
        
        return user;
    }

    /**
     * This method is used for the signup process of organizers.
     * Receives a {@link SignupAttempt} object with the information of the user and tries to submit
     * it to the database.
     * 
     * @param signup {@link SignupAttempt} with the organizer's signup data - name, email, password
     * and organization
     * @return {@link Organizer} object that has been created as a result of the request. 
     * If the email is already in use, null is returned.
     * @since Sprint 2
     */
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

        DAOFactory.getInstance().closeDAO(dao);
        
        return organizer;
    }

    //--------------------------UPDATE USERS-----------------------------------------------------------------------

     /**
     * This method is used for the update process of regular users (not organizers).
     * Receives a {@link SignupAttempt} object and tries to update an existing user in the database.
     * 
     * @param signup {@link SignupAttempt} with the user's data  (name, email, an empty password, city).
     * @since Sprint 2
     * @return {@link User} object that has been created as a result of the request. If the email doesn't exist
     * null is returned
     */
    public User attemptNormalUpdate(SignupAttempt signup) {
        UserDAO dao = DAOFactory.getInstance().createUserDAO();
        
        // 1. Make sure the email exists
        User user = dao.getUser(signup.getEmail());
        if (user == null) {
            return null;
        }

        //2. updates every atribute except the Password. --> if not the password is nullified.
        User u = signup.buildUser();
        user.setName(u.getName());
        user.setCity(u.getCity());
        user.setCountry(u.getCountry());
        user.setEmail(u.getEmail()); // necesary to update the email??
        user.setInterests(u.getInterests());
        // user.setSavedEvents(u.getSavedEvents());
        
        EventDAO eventDAO = DAOFactory.getInstance().createEventDAO();
        ArrayList<Event> toAdd = new ArrayList<>();
        for (Event e1: u.getSavedEvents()) {
            boolean isIncluded = false;
            for (Event e2: user.getSavedEvents()) {
                if (e1.getName().equals(e2.getName())) {
                    isIncluded = true;
                    break;
                }
            }

            if (!isIncluded) {
                Event e = eventDAO.getEvents(e1.getName()).get(0);
                if (e != null) toAdd.add(e);
            }
        }

        ArrayList<Event> toDelete = new ArrayList<>();
        for (Event e1: user.getSavedEvents()) {
            boolean missing = true;
            for (Event e2: u.getSavedEvents()) {
                if (e1.getName().equals(e2.getName())) {
                    missing = false;
                    break;
                }
            }

            if (missing) {
                Event e = eventDAO.getEvents(e1.getName()).get(0);
                if (e != null) toDelete.add(e1);
            }
        }

        for (Event e: toAdd) {
            user.getSavedEvents().add(e);
            e.addInterested();
        }

        for (Event e: toDelete) {
            user.getSavedEvents().remove(e);

            Event edb = eventDAO.getEvents(e.getName()).get(0);
            edb.reduceInterested();
            if (edb != null) eventDAO.updateEvent(edb);
        }

        //3. Update the user in the DB
        dao.storeUser(user);

        DAOFactory.getInstance().closeDAO(dao);
        DAOFactory.getInstance().closeDAO(eventDAO);
        
        return user;
    }

    /**
     * This method is used for the updating process of organizers.
     * Receives a {@link SignupAttempt} object and tries to update an existing organizer in the database.
     * 
     * @param signup {@link SignupAttempt} object with the requester's data (name, email, empty password, 
     * organization).
     * @since Sprint 2
     * @return {@link Organizer} object that has been updated as a result of the request.
     * If the email doesn't exist null is returned.
     */
    public Organizer attemptOrganizerUpdate(SignupAttempt signup) {
        OrganizerDAO dao = DAOFactory.getInstance().createOrganizerDAO();

        // 1. Make sure the email is not in use
        Organizer organizer = dao.getOrganizer(signup.getEmail());
        if (organizer == null) {
            return null;
        }

        // 2. update every atribute except the Password. --> if not the password is nullified.
        Organizer u = signup.buildOrganizer();
        organizer.setName(u.getName());
        organizer.setEmail(u.getEmail()); // necesary to update the email??
        organizer.setCreatedEvents(u.getCreatedEvents());
        organizer.setOrganization(u.getOrganization());

        //3.Store the user in the DB
        dao.storeOrganizer(organizer);
        DAOFactory.getInstance().closeDAO(dao);

        return organizer;
    }

    //--------------------------DELETE USER-----------------------------------------------------------------------

    /**
     * This method is used for the delete process of users.
     * Receives an String with the email and tries to delete the account related to it from the database.
     * 
     * @param email of the account to be deleted.
     * @since Sprint 2
     * @return boolean True if the email belongs to a user and the delete is complete successfully, false otherwise.
     */

    public boolean deleteUser(String email) {
        UserDAO dao = DAOFactory.getInstance().createUserDAO();

        // 1. Make sure that the email is in use
        User user = dao.getUser(email);
        if (user == null) return false;

        // 2. Delete user from the database
        dao.deleteUser(email);
        DAOFactory.getInstance().closeDAO(dao);
        return true;
    }

     /**
     * This method is used for the delete process of organizers.
     * Receives an String with the email and tries to delete the organizer account related to it from the database.
     * 
     * @param email of the organizer account to be deleted.
     * @since Sprint 2
     * @return boolean True if the email belongs to a organizer and the delete is complete successfully, false otherwise.
     */
	public boolean deleteOrganizer(String email) {
        OrganizerDAO dao = DAOFactory.getInstance().createOrganizerDAO();

        // 1. Make sure that the email is in use
        Organizer organizer = dao.getOrganizer(email);
        if (organizer == null) return false;

        // 2. Delete user from the database
        dao.deleteOrganizer(email);
        DAOFactory.getInstance().closeDAO(dao);

		return false;
	}

    //-------------------------------------------EVENT MANAGEMENT---------------------------------------------
    /**
     * Method used for storing new {@link Event}s in the database, used only by the organizers.
     * Receives an {@link EventInfo}, transforms it into a {@link Event} and stores it in the database.
     * 
     * @param eventInfo
     * @return {@link Event} object of the newly stored event.
     * @since sprint 2
     */
    public Event createEvent(EventInfo eventInfo) {
        Event e = new Event(eventInfo); 
        EventDAO dao = DAOFactory.getInstance().createEventDAO();
        dao.storeEvent(e);
        DAOFactory.getInstance().closeDAO(dao);
        return e;
    }

    /**
     * Creates a list of events that coincide with the interests ({@link Topic}) of a user.
     * This method reads the interests of a User, and returns the adecuate {@link Event}s from the database.
     * It is used for making automatic recomendations, a user should be able to see a list of events that 
     * he/she should be interested.
     * 
     * @param signupAttempt {@link SignupAttempt} object containing the interests of the user.
     * @return {@link ArrayList} of {@link EventInfo} list of events that coincide with the interests of a user.
     * @since Sprint 3
     */
    public ArrayList<EventInfo> getRecommendedEvents(SignupAttempt signupAttempt){
        User user = signupAttempt.buildUser();
        EventDAO dao = DAOFactory.getInstance().createEventDAO();

        ArrayList<Event> recommEvents = dao.getEventsbyUser(user);
        ArrayList<EventInfo> recommEventsInfo = new ArrayList<>();

        DAOFactory.getInstance().closeDAO(dao);

        //change from event to eventInfo
        for (Event event : recommEvents) {
            EventInfo eventInfo = new EventInfo(event);
            recommEventsInfo.add(eventInfo);
        }
        return recommEventsInfo;
    }

    /**
     * This method is used for event removal. 
     * It receives an {@link EventInfo} with the data of the event to be deleted. 
     * 
     * @param info EventInfo with the data of the event
     * @since Sprint 3
     */
    public void deleteEvent(EventInfo info) {
        EventDAO edao = DAOFactory.getInstance().createEventDAO();
        UserDAO udao = DAOFactory.getInstance().createUserDAO();

        ArrayList<Event> events = edao.getEvents(info.getName());
        if (events.isEmpty()) {
            DAOFactory.getInstance().closeDAO(edao);
            return;    
        } 

        Event e = events.get(0);        

        
        for (User u: udao.getAllUsers()) {
            ArrayList<Event> newEvents = new ArrayList<Event>();
            for (Event ev: u.getSavedEvents()) {
                if (!ev.getName().equals(e.getName())) {
                    newEvents.add(ev);
                }
            }
            u.setSavedEvents(newEvents);
            udao.updateUser(u);
        }

        edao.deleteEvent(e);       

        DAOFactory.getInstance().closeDAO(edao);
        DAOFactory.getInstance().closeDAO(udao);
    }

    /**
     * This method is used for new password generation. 
     * It receives a {@link LoginAttempt} with the email of the user that wants to recover his/her password, 
     * generates a new password for that user and updates the data in the database. 
     * It also sends an email to the received email with the new password so that the user can log in. 
     * If there is no account in the database with that same email linked to it, the method does nothing.
     * 
     * @param login LoginAttempt with the user's email
     * @since Sprint 1
     */
    public void recoverPassword(LoginAttempt login) {
        String email = login.getEmail();
        String newPassword = generatePassword();
        sendEmail(email, newPassword);
    }

    /**
	 * This method is used for the creation of new {@link Post} from a given Event.
	 * It is envoked whenever a POST request is made to the following path: /createEvent.
	 * @param info {@link PostInfo} object with the data of the event that will be created.
	 * @return {@link Post} object containing the information of the stored result.
	 * @since Sprint 2 
	 */
    public Post createPost(PostInfo info) {
        Post post = new Post(info);
        PostDAO pdao = DAOFactory.getInstance().createPostDAO();
        pdao.storePost(post);
        DAOFactory.getInstance().closeDAO(pdao);
        return post;
    }
    
  

    // ------------------------------UTILITY METHODS----------------------------------------

    /**
     * Method for creating randomized Strings.
     * Used for generation of randomized passwords.
     * 
     * @return String containing the password created.
     * @since Sprint 1
     */
    private static String generatePassword() {
        String SALTCHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 12) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    /**
     * Method for sending an email with the password to a email account.
     * @param to String email address.
     * @param password text to be sended, in this case a password.
     * @since Sprint 1
     */
    private static void sendEmail(String to, String password) {
        String from = "bspq20e1@gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bspq20e1@gmail.com", "spqproject20:D");
            }
        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Reset Password");
            message.setText("Your new password is: " + password + "\n\nThanks for using our app!\n\nThe team");
            Transport.send(message);
            changePassword(to, password);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    /**
     * Method to update the pasword of a account in the database.
     * It is part of the password management functionality.
     * @param email String with the email of the account.
     * @param password String with the new password.
     * @since Sprint 1
     */
    private static void changePassword(String email, String password) {
        UserDAO dao = DAOFactory.getInstance().createUserDAO();
        User user = dao.getUser(email);
        if (user != null) {
            user.setPassword(password);
            dao.updateUser(user);
            DAOFactory.getInstance().closeDAO(dao);
            return;
        }

        OrganizerDAO odao = DAOFactory.getInstance().createOrganizerDAO();
        Organizer org = odao.getOrganizer(email);
        if (org != null) {
            org.setPassword(password);
            odao.updateOrganizer(org);
            DAOFactory.getInstance().closeDAO(odao);
        }
    }


    // ----------------------------------------------------------------------------------------------------

    // method used for manual testing 

    /**Used for testing, creates an instance of a user and updates it in the database.
     * @deprecated no useful for normal use.
     */
    public void updateUser() {
        UserDAO udao = DAOFactory.getInstance().createUserDAO();
        EventDAO edao = DAOFactory.getInstance().createEventDAO();
        // TODO what's this??
        User user = udao.getUser("john@money.com");
        Event event = edao.getEvents("Popcorn Party - second edition (PP2)").get(0);
        user.addEvent(event);
        udao.updateUser(user);
        DAOFactory.getInstance().closeDAO(udao);
        DAOFactory.getInstance().closeDAO(edao);
    }

    /**Used for testing
     * @deprecated
     */
    public void hello() {
        OrganizerDAO dao = DAOFactory.getInstance().createOrganizerDAO();
        EventDAO edao = DAOFactory.getInstance().createEventDAO();
        ArrayList<Event> events = new ArrayList<>();

        Organizer org = new Organizer();
        org.setName("Test Organizer");
        org.setPassword("password");
        org.setEmail("testorganizer@test.com");
        org.setOrganization("Save the organizers");
        
        dao.storeOrganizer(org);

        Event e1 = new Event();
        e1.setName("testEvent1");
        e1.setDescription("testDescription1");
        e1.setCity("Bilbao");
        e1.setCountry("Spain");
        e1.setTopic(new Topic(new TopicInfo("Sport")));
        e1.setOrganizer(org);
        e1.setDate(new Date(1588780482445l)); 
        edao.storeEvent(e1);
        events.add(e1);
        
        Event e2 = new Event();
        e2.setName("testEvent2");
        e2.setDescription("testDescription2");
        e2.setCity("Bilbao");
        e2.setCountry("Spain");
        e2.setTopic(new Topic(new TopicInfo("Music")));
        e2.setOrganizer(org);
        e2.setDate(new Date(1588780492445l));
        edao.storeEvent(e2);
        events.add(e2);

        Event e3 = new Event();
        e3.setName("testEvent3");
        e3.setDescription("testDescription3");
        e3.setCity("Barcelona");
        e3.setCountry("Spain");
        e3.setTopic(new Topic(new TopicInfo("Sport")));
        e3.setOrganizer(org);
        e3.setDate(new Date(1588780502444l));
        edao.storeEvent(e3);
        events.add(e3);

        Event e4 = new Event();
        e4.setName("testEvent4");
        e4.setDescription("testDescription4");
        e4.setCity("Barcelona");
        e4.setCountry("Spain");
        e4.setTopic(new Topic(new TopicInfo("Theater")));
        e4.setOrganizer(org);
        e4.setDate(new Date(1588780510445l));
        edao.storeEvent(e4);
        events.add(e4);

        Event e5 = new Event();
        e5.setName("testEvent5");
        e5.setDescription("testDescription5");
        e5.setCity("Madrid");
        e5.setCountry("Spain");
        e5.setTopic(new Topic(new TopicInfo("Theater")));
        e5.setOrganizer(org);
        e5.setDate(new Date(1588780520446l));
        edao.storeEvent(e5);
        events.add(e5);

        Event e6 = new Event();
        e6.setName("testEvent6");
        e6.setDescription("testDescription6");
        e6.setCity("Madrid");
        e6.setCountry("Spain");
        e6.setTopic(new Topic(new TopicInfo("Music")));
        e6.setOrganizer(org);
        e6.setDate(new Date(1588780527446l));
        edao.storeEvent(e6);
        events.add(e6);

        Event e7 = new Event();
        e7.setName("testEvent7");
        e7.setDescription("testDescription7");
        e7.setCity("Paris");
        e7.setCountry("France");
        e7.setTopic(new Topic(new TopicInfo("Sport")));
        e7.setOrganizer(org);
        e7.setDate(new Date(1588780533446l));
        edao.storeEvent(e7);
        events.add(e7);

        Event e8 = new Event();
        e8.setName("testEvent8");
        e8.setDescription("testDescription8");
        e8.setCity("Paris");
        e8.setCountry("France");
        e8.setTopic(new Topic(new TopicInfo("Music")));
        e8.setOrganizer(org);
        e8.setDate(new Date(1588780533446l));
        edao.storeEvent(e8);
        events.add(e8);

        org.setCreatedEvents(events);

        dao.updateOrganizer(org);
        
        DAOFactory.getInstance().closeDAO(dao);
        DAOFactory.getInstance().closeDAO(edao);
    }

}