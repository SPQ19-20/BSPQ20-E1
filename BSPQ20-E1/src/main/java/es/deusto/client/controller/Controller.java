package es.deusto.client.controller;

import es.deusto.client.windows.LanguageManager;
import es.deusto.serialization.*;

import java.util.ArrayList;

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

    private UserInfo user;

    private Client client;
    private WebTarget webTarget;
    
    private LanguageManager langManager;

    /**
     * Constructor
     * @param hostname A String object with the path of the server
     * @param port A String object with the port number of the server
     */
    public Controller(String hostname, String port) {
        client = ClientBuilder.newClient();
        webTarget = client.target(String.format("http://%s:%s/rest", hostname, port));
        langManager = new LanguageManager();
    }

    public UserInfo getUser() {
        return this.user;
    }

    /**
     * This method is invoked by the login button in the GUI, and it makes
     * a login request to the server with the specified email and password.
     * It is used only for regular users, not organizers.
     * @param email Email of the user taken from the GUI
     * @param password Password taken from the GUI
     * @return true if the login was successful, otherwise it returns false
     */
    public boolean attemptNormalLogin(String email, String password) {
        LoginAttempt login = new LoginAttempt(email, password, false);
        WebTarget donationsWebTarget = webTarget.path("server/login");
		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(login, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            System.out.println("Not OK status code");
            return false;
        }
        
        user = response.readEntity(UserInfo.class);

        if (user != null) {
            System.out.println("We got something");
            System.out.println(user.getName());
        }

        return user != null;
    }

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
		Invocation.Builder invocationBuilder = donationsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(signup, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
            // TODO handle this situation
            System.out.println("Not OK status code");
            return false;
        }

        this.user = response.readEntity(UserInfo.class);

        if (user != null) {
            return true;
        }

        return false;
    }

    public LanguageManager getLanguageManager() {
        return this.langManager;
    }

}