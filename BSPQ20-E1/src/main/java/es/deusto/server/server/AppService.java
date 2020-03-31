package es.deusto.server.server;

import es.deusto.serialization.LoginAttempt;
import es.deusto.serialization.SignupAttempt;
import es.deusto.server.dao.DAOFactory;
import es.deusto.server.dao.OrganizerDAO;
import es.deusto.server.dao.UserDAO;
import es.deusto.server.data.Organizer;
import es.deusto.server.data.User;

import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mongodb.*;
import com.mongodb.util.JSON;

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
     * In order to access to the database, use the
     * DAO objects (create them using DAOFactory class).
    */

    public AppService() {
        
    }

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

        return user;
    }

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

        return organizer;
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
        String newPassword = generatePassword();
        sendEmail(email, newPassword);
        System.out.println("Changing and sending new password for " + email);
    }

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
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

        DB database = mongoClient.getDB("bspq20e1");
        DBCollection users = database.getCollection("users");
        users.update((DBObject) JSON.parse("{'email':'"+ email + "'}"), (DBObject) JSON.parse("{'$set':{'password':'" + password + "'}}"));
        //Another way if we use password encryption with encrypted password and salt fields
        //String salt = "";
        //users.update((DBObject) JSON.parse("{'email':'"+ email + "'}"), (DBObject) JSON.parse("{'$set':{'password':'" + password + ",'salt':" + salt + "}}"));

        System.out.println("Completed successfully.");
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