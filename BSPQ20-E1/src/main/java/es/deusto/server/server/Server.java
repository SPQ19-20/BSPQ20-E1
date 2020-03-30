package es.deusto.server.server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.serialization.*;
import es.deusto.server.data.*;

@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
public class Server {

	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;

	public Server() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptLogin(LoginAttempt login) {
		System.out.println("Login attempt received: "+login);

		// TODO check if the credentials are valid

		return Response.ok(login).build();
	}

	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptSignup(SignupAttempt signup) {
		System.out.println("Signup attempt received: "+signup);

		// TODO check if email is already in use

		// TODO store new user in DB


		return Response.ok(signup).build();
	}

	@POST
	@Path("/passwordRecovery")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recoverPassword(LoginAttempt userInfo){
		String email = userInfo.getEmail();

		// TODO send email here

		// TODO change user's password in DB

		System.out.println("Sending new password to " + email);

		return null;
	}

	@POST
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
        // Persistence of a set of Accounts and a User
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
		return Response.ok("Hello world!").build();
	}
}
