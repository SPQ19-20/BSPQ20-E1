package es.deusto.client.controller;

import es.deusto.serialization.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Controller {

    private UserInfo user;

    private Client client;
	private WebTarget webTarget;

    public Controller(String hostname, String port) {
        client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest", hostname, port));
    }

    public UserInfo getUser() {
        return this.user;
    }

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

    public UserInfo attemptNormalSignup(String email, String password,
                    String name, String city) {
        
        
        return null;
    }

}