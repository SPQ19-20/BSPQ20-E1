package es.deusto.client.controller;

import es.deusto.client.windows.LanguageManager;
import es.deusto.serialization.*;
import javassist.bytecode.stackmap.TypeData.ClassName;

import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Controller {
    
    /**
     * This class is the one carrying out all the communication with
     * the server. The GUI components use it as a middle-man in order to
     * retrieve or update information of the server.
     * 
     * This class will contain the UserInfo property containing the
     * information of the user who has logged in, and will offer it
     * to the components of the GUI.
     */

    private UserInfo user; //Controller's user
    private OrganizerInfo organizer; //in case it's a organizer, it necesary for the progeam to be able to retrieve a Organizer.
    private Client client;
    private WebTarget webTarget;
    private LanguageManager langManager;
    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private static Handler fileHandler;
    /**
     * Constructor
     * @param hostname A String object with the path of the server
     * @param port A String object with the port number of the server
     */
    public Controller(String hostname, String port) {
        client = ClientBuilder.newClient();
        webTarget = client.target(String.format("http://%s:%s/rest", hostname, port));
        langManager = new LanguageManager();

        //initialise the logger
        try {
            fileHandler = new FileHandler("BSPQ20-E1/src/main/java/es/deusto/client", true);   
            LOGGER.addHandler(fileHandler);
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public Controller(WebTarget target) {
        this.webTarget = target;
        langManager = new LanguageManager();
    }

    public UserInfo getUser() {
        return this.user;
    }

    public OrganizerInfo getOrganize(){
        return this.organizer;
    }

    //--------------------------LOG IN----------------------------------------------------------------------------
    /**
     * This method is invoked by the login button in the GUI, and it makes
     * a login request to the server with the specified email and password.
     * It is used only for regular users, not organizers.
     * @param email Email of the user taken from the GUI
     * @param password Password taken from the GUI
     * @return true if the login was successful, otherwise it returns false
     */
    public boolean attemptNormalLogin(String email, String password) { 
        LoginAttempt login = new LoginAttempt(email, password, false);//The boolean is never used!
        WebTarget donationsWebTarget = webTarget.path("server/login");
        LOGGER.log(Level.FINE, "Loggin USER: "+ email);
		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(login, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            // System.out.println("Not OK status code");
            LOGGER.log(Level.SEVERE, "Not OK status code: User Login failed");
            return false;
        }
        
        user = response.readEntity(UserInfo.class);

        if (user != null) {
            // System.out.println("We got something");
            // System.out.println(user.getName());
            LOGGER.log(Level.FINE, "We got something, " + user.getName());
        }

        return user != null;
    }



    /**
     *    * This method is invoked by the login button in the GUI, and it makes
     * a login request to the server with the specified email and password.
     * It is used only for organizers.
     * @param email email of the {@link Organizer} 
     * @param password password of the organizer
     * @return true  if succesful
     * @since Sprint 2
     */
    public boolean attemptNormalLoginOrganizer(String email, String password) { 
        LoginAttempt login = new LoginAttempt(email, password, true);//The boolean is never used!
        WebTarget donationsWebTarget = webTarget.path("server/loginOrganizer");
        LOGGER.log(Level.FINE, "Loggin ORGANIZER: "+ email);

		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(login, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            // System.out.println("Not OK status code");
            LOGGER.log(Level.SEVERE, "Not OK status code: User Login failed");
            return false;
        }
        
        organizer = response.readEntity(OrganizerInfo.class);

        if (organizer != null) {
           // System.out.println("We got something");
           // System.out.println(organizer.getName());
            LOGGER.log(Level.FINE, "We got something, " + user.getName());

        }

        return organizer != null;
    }

    /***
     *  This method is invoked by the login button in the GUI, and it makes
     * a login request to the server with the specified email and password.
     * The class of object must be indicated in the boolean input.
     * This method works as a solution for both organizers and logins
     * @param email email of the {@link User} or {@link Organizer}
     * @param password password of the of the {@link User} or {@link Organizer}
     * @param organizer boolean that indicates whether it's a {@link User} or {@link Organizer}
     * @deprecated beeter to use attemptNormalLogin(email, password) and attemptNormalLoginOrganizer !
     * @since Sprint 2
     * @return  boolean if succesful
     */
    public boolean attemptNormalLogin(String email, String password, boolean organizer) { //tal vez seria bueno que hubiese un combobox que envia un dato para saber si es Organizador o Usuario
        LoginAttempt login = new LoginAttempt(email, password, organizer);
        WebTarget donationsWebTarget;
        if(organizer == true){
             donationsWebTarget = webTarget.path("server/loginOrganizer");
        }else{
             donationsWebTarget = webTarget.path("server/login");
        }
		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(login, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            System.out.println("Not OK status code");
            return false;
        }

        if (organizer== true) {
            this.organizer = response.readEntity(OrganizerInfo.class); //<-- como puedo hacer que no me salga error
            if (this.organizer != null) {
                System.out.println("We got something");
                System.out.println(user.getName());
            }
            return this.organizer != null;
        } else {
            user = response.readEntity(UserInfo.class); 
            if (user != null) {
                System.out.println("We got something");
                System.out.println(user.getName());
            }
            return user != null;
        }
       

    }
    //-----------------------------------SIGN UP--------------------------------------------------------------
    /** 
     * This method is invoked by the signup button in the GUI, and makes
     * a signup request to the server with the specified fields (email, password, name and city).
     * It is used only for regular users, not organizers.
     * @param email Email String taken from the GUI
     * @param password Password String taken from the GUI
     * @param name Name String taken from the GUI
     * @param city City String taken from the GUI
     * @return true if the signup process was successful, otherwise it returns false
    */
    public boolean attemptNormalSignup(String email, String password, String name, String city, ArrayList<TopicInfo>interests) {
        
        SignupAttempt signup = new SignupAttempt();
        signup.setEmail(email);
        signup.setName(name);
        signup.setPassword(password);
        signup.setCity(city);
        signup.setInterests(interests);

        WebTarget donationsWebTarget = webTarget.path("server/signup");
        LOGGER.log(Level.FINE, "Signing up USER");
		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(signup, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            System.out.println("Not OK status code");
            LOGGER.log(Level.SEVERE, "Not OK status code : error while singing up user");
            return false;
        }

        this.user = response.readEntity(UserInfo.class);

        if (user != null) {
            LOGGER.log(Level.FINE, "Signup for USER completed " + email);
            return true;
        }

        return false;
    }

    /** 
     * This method is invoked by the signup button in the GUI, and makes
     * a signup request to the server with the specified fields (email, password, name and organization).
     * It is used only for organizers.
     * @param email Email String taken from the GUI
     * @param password Password String taken from the GUI
     * @param name Name String taken from the GUI
     * @param city City String taken from the GUI
     * @since Sprint 2
     * @return true if the signup process was successful, otherwise it returns false
    */
    public boolean attemptOrganizerSignup(String email, String password, String name, String organization) {
        
        SignupAttempt signup = new SignupAttempt();
        signup.setEmail(email);
        signup.setName(name);
        signup.setPassword(password);
        signup.setOrganization(organization);

        WebTarget donationsWebTarget = webTarget.path("server/signupOrganizer");
        LOGGER.log(Level.FINE, "Signing up organizer " + email);
		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
        Response response = invocationBuilder.post(Entity.entity(signup, MediaType.APPLICATION_JSON));
        
		if (response.getStatus() != Status.OK.getStatusCode()) {
           // System.out.println("Not OK status code");
           LOGGER.log(Level.SEVERE, "Not OK status code : error wile signing up Organizer");
            return false;
        }

        this.organizer = response.readEntity(OrganizerInfo.class);

        if (organizer != null) {
            LOGGER.log(Level.FINE, "sIGNUP ORGANIZER COMPLETED");
            return true;
        }

        return false;
    }

    //--------------------------------------UPDATE USER-----------------------------------------------------------

    /**
     * Updates the user stored in the Controller, in order to use this function a user must log in before.
     * @return true if the process is successful
     * @since Sprint 2
     */
    public boolean attemptNormalUpdate() {
        SignupAttempt signup = new SignupAttempt();
        signup.setEmail(this.getUser().getEmail());
        signup.setName(this.getUser().getName());
        signup.setCity(this.getUser().getCity());
        signup.setInterests(this.getUser().getInterests());

        WebTarget donationsWebTarget = webTarget.path("server/update");
        LOGGER.log(Level.FINE, "Updating the user: " + this.user.getEmail());
        Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(signup, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            LOGGER.log(Level.SEVERE, "Not OK status code: not possible to update USER " + this.user.getEmail());
            // System.out.println("Not OK status code");
            return false;
        }

        this.user = response.readEntity(UserInfo.class);

        if (user != null) {
            LOGGER.log(Level.FINE, "User updated");
            return true;
        }
        LOGGER.log(Level.FINE, "Not possible to updated");
        return false;
    }

    /**
     * Updates the Organizer stored in the Controller, in order to use this function a Organizer must log in before.
    *  @return true if the process is successful
    *  @since Sprint 2
    */
    public boolean attemptOrganizerUpdate() {
        
        SignupAttempt signup = new SignupAttempt();
        signup.setEmail(this.getOrganize().getEmail());
        signup.setName(this.getOrganize().getName());
        signup.setOrganization(this.getOrganize().getOrganization());

        WebTarget donationsWebTarget = webTarget.path("server/updateOrganizer");
        LOGGER.log(Level.FINE, "Updating the organizer: " + this.organizer.getEmail());
		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(signup, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            LOGGER.log(Level.SEVERE, "Not OK status code: Error while updating Organizer ");
            //System.out.println("Not OK status code");
            return false;
        }

        this.organizer = response.readEntity(OrganizerInfo.class);

        if (organizer != null) {
            LOGGER.log(Level.FINE, "Organizer Updated succesfully");
            return true;
        }

        return false;
    }

    //--------------------------------------DELETE USER-----------------------------------------------------------

    /**
     * Deletes the user stored in the Controller.
     * @return true if the process is successful, false if not
     * @since Sprint 2
     */
    public boolean attemptUserDelete() {
        WebTarget donationsWebTarget = webTarget.path("server/delete");
        LOGGER.log(Level.FINE, "deleting user: "+ this.user.getEmail());
        Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(this.getUser(), MediaType.APPLICATION_JSON));

        if (response.getStatus() != Status.OK.getStatusCode()) {
            
            LOGGER.log(Level.SEVERE, "Not OK status code : error while DELETING user");
            //System.out.println("Not OK status code");
            return false;
        }

        this.user = null;
        
        return true;
    }

    public boolean attemptOrganizerDelete() {
        WebTarget donationsWebTarget = webTarget.path("server/deleteOrganizer");
        LOGGER.log(Level.FINE, "Deleting organizer " + this.organizer.getEmail());
        Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(this.organizer, MediaType.APPLICATION_JSON));

        if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            System.out.println("Not OK status code");
            LOGGER.log(Level.SEVERE, "Not OK status code");
            return false;
        }

        this.organizer = null;
        LOGGER.log(Level.FINE, "Organizer succesfully deleted");
        return true;
    }

//-------------------------------------EVENT & POST-----------------------------------------------------

    /**
     * sends {@link EventInfo} to the server, to use this method the organizer must be logged in.
     * @param eventInfo event to be sent to the server
     * @return true if succesful
     * @since sprint 2
     */
    public boolean createEvent(EventInfo eventInfo){
        eventInfo.setOrganizerEmail(this.organizer.getEmail());
        this.organizer.getCreatedEvents().add(eventInfo);

        WebTarget donationsWebTarget = webTarget.path("server/createEvent");
        LOGGER.log(Level.FINE, "Creating the Event: " + eventInfo.getName());
        Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(eventInfo, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            LOGGER.log(Level.SEVERE, "Not OK status code: not possible create Event " + eventInfo.getName());
            // System.out.println("Not OK status code");
            return false;
        }

        LOGGER.log(Level.FINE, "Event Created");
        return true;
        
    }

    /**
     * Adds a post on the indicated Event and notifies the server.
     * @param eventInfo Event where the post is going to be held.
     * @param postInfo Post to be added.
     * @return true if succesful
     * @since sprint 2
     */
    public boolean createPost(EventInfo eventInfo, PostInfo postInfo){
        postInfo.setEventName(eventInfo.getName());
        postInfo.setOrganizerEmail(eventInfo.getOrganizerEmail());
        eventInfo.getPosts().add(postInfo);
        WebTarget donationsWebTarget = webTarget.path("server/createPost");
        LOGGER.log(Level.FINE, "Creating the Post for event: " + eventInfo.getName());
        Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(eventInfo, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            LOGGER.log(Level.SEVERE, "Not OK status code: not possible to create post for Event " + eventInfo.getName());
            // System.out.println("Not OK status code");
            return false;
        }
        LOGGER.log(Level.FINE, "POST Created");
        return true;
    }

    public LanguageManager getLanguageManager() {
        return this.langManager;
    }

    

}