package es.deusto.server.server;

import es.deusto.serialization.EventInfo;
import es.deusto.serialization.LoginAttempt;
import es.deusto.serialization.PostInfo;
import es.deusto.serialization.SignupAttempt;
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
     * This class will include methods representing
     * the different functionalities of the server. 
     * 
     * This class should be called by the Server class,
     * so that the Server is abstracted from the
     * business logic of the project.
     * 
     * In order to access to the database, use the
     * DAO objects (create them using DAOFactory class).
    */

    public AppService() {}

    //--------------------------------------LOGIN USERS-----------------------------------------------------

    /**
     * Receives a LoginAttempt (email and password string) and tries to log in.
     * This method is used for the login process of regular users (not organizers).
     * 
     * @param login LoginAttempt with the requester's email and password
     * @return User The user to which the login email corresponds. If the credentials are
     * invalid, null is returned
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
     * Receives a LoginAttempt (email and password string) and tries to log in.
     * This method is used for the login process of organizers (not regular users).
     * 
     * @param login LoginAttempt with the requester's email and password
     * @return Organizer The organizer to which the login email corresponds. If the credentials are
     * invalid, null is returned
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
     * Receives a SignupAttempt (name, email, password, etc.) and tries to create a new user.
     * This method is used for the signup process of regular users (not organizers).
     * 
     * @param signup SignupAttempt with the requester's signup data - name, email, password and city
     * @return User The user that has been created as a result of the request. If the email is already in
     * use, null is returned
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
     * Receives a SignupAttempt (name, email, password, etc.) and tries to create a new organizer.
     * This method is used for the signup process of organizers (not regular users).
     * 
     * @param signup SignupAttempt with the requester's signup data - name, email, password and organization
     * @return Organizer the organizer that has been created as a result of the request. If the email is already in
     * use, null is returned
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
     * Receives a SignupAttempt (name, email, etc.) and tries to update an existing user.
     * This method is used for the update process of regular users (not organizers).
     *
     * @param signup SignupAttempt with the requester's data - name, email, password(empty) and city
     * @since Sprint 2
     * @return User The user that has been created as a result of the request. If the email doesn't exist
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
     * Receives a SignupAttempt (name, email, password, etc.) and tries to UPDATE an existing organizer.
     * This method is used for the updating process of organizers (not regular users).
     * 
     * @param signup SignupAttempt with the requester's data - name, email, password (empty) and organization
     * @since Sprint 2
     * @return Organizer the organizer that has been updated as a result of the request. If the email doesn't exist
     * null is returned
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
     * Receives an user's email and tries to delete the user from the database.
     * This method is used for the delete process of all type of users.
     *
     * @param email - user's email
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
    public Event createEvent(EventInfo eventInfo) {
        Event e = new Event(eventInfo); 
        EventDAO dao = DAOFactory.getInstance().createEventDAO();
        dao.storeEvent(e);
        DAOFactory.getInstance().closeDAO(dao);
        return e;
    }

    public ArrayList<EventInfo> getRecomendedEvents(SignupAttempt signupAttempt){
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
     * This method is used for new password generation. It receives a LoginAttempt
     * with the email of the user that wants to recover his/her password, generates a new password 
     * for that user and updates the data in the database. It also sends an email to the received
     * email with the new password so that the user can log in. If there is no account in the database
     * with that same email linked to it, the method does nothing.
     * 
     * @param login LoginAttempt with the user's email
     */
    public void recoverPassword(LoginAttempt login) {
        String email = login.getEmail();
        String newPassword = generatePassword();
        sendEmail(email, newPassword);
    }

    public Post createPost(PostInfo info) {
        Post post = new Post(info);
        PostDAO pdao = DAOFactory.getInstance().createPostDAO();
        pdao.storePost(post);
        DAOFactory.getInstance().closeDAO(pdao);
        return post;
    }
    
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

    // -----------------------------------------------------------------------
    // UTILITY METHODS

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
            message.setText("Your new password is: " + password + "\nWe advise you to change your password as soon as you login into your account.\n\nThe team");
            Transport.send(message);
            changePassword(to, password);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private static void changePassword(String email, String password) {
        UserDAO dao = DAOFactory.getInstance().createUserDAO();
        User user = dao.getUser(email);
        user.setPassword(password);
        dao.updateUser(user);
        DAOFactory.getInstance().closeDAO(dao);
        
        // Another way if we use password encryption with encrypted password and salt fields
        // String salt = "";
        // users.update((DBObject) JSON.parse("{'email':'"+ email + "'}"), (DBObject) JSON.parse("{'$set':{'password':'" + password + ",'salt':" + salt + "}}"));
    }


    // --------------------------------------------------------------

    // method used for manual testing 
    public void hello() {
        Post p = new Post();
        p.setDate(new Date());
        p.setDescription("This is the super hyper mega description");
        p.setEventName("My fancy event");
        p.setOrganizerEmail("jack@blackpearl.com");
        p.setTitle("Title of the post");
        PostDAO pdao = DAOFactory.getInstance().createPostDAO();
        pdao.storePost(p);
        DAOFactory.getInstance().closeDAO(pdao);
    }

}