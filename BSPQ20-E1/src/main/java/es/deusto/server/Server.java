package es.deusto.server;

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

import es.deusto.server.data.Organizer;

@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
public class Server {

	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;

	public Server() {
		// System.out.println("vamos vamos vamos");
		// PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		// this.pm = pmf.getPersistenceManager();
		// this.tx = pm.currentTransaction();

        // System.out.println("DataNucleus AccessPlatform with JDO");
        // System.out.println("===================================");

        // // Persistence of a set of Accounts and a User
        // try
        // {	
        //     tx.begin();
        //     System.out.println("Persisting users");

        //     Organizer orga = new Organizer();
        //     orga.setName("Luis");
        //     orga.setOrganization("ferrari");

		// 	pm.makePersistent(orga);					 
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

	// @POST
	// @Path("/sayMessage")
	// public Response sayMessage(DirectedMessage directedMessage) {
	// 	User user = null;
	// 	try{
	// 		tx.begin();
	// 		System.out.println("Creating query ...");
			
	// 		Query<User> q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE login == \"" + directedMessage.getUserData().getLogin() + "\" &&  password == \"" + directedMessage.getUserData().getPassword() + "\"");
	// 		q.setUnique(true);
	// 		user = (User)q.execute();
			
	// 		System.out.println("User retrieved: " + user);
	// 		if (user != null)  {
	// 			Message message = new Message(directedMessage.getMessageData().getMessage());
	// 			user.getMessages().add(message);
	// 			pm.makePersistent(user);					 
	// 		}
	// 		tx.commit();
	// 	} finally {
	// 		if (tx.isActive()) {
	// 			tx.rollback();
	// 		}
	// 	}
		
	// 	if (user != null) {
	// 		cont++;
	// 		System.out.println(" * Client number: " + cont);
	// 		MessageData messageData = new MessageData();
	// 		messageData.setMessage(directedMessage.getMessageData().getMessage());
	// 		return Response.ok(messageData).build();
	// 	} else {
	// 		return Response.status(Status.BAD_REQUEST).entity("Login details supplied for message delivery are not correct").build();
	// 	}
	// }
	
	// @POST
	// @Path("/register")
	// public Response registerUser(UserData userData) {
	// 	try
    //     {	
    //         tx.begin();
    //         System.out.println("Checking whether the user already exits or not: '" + userData.getLogin() +"'");
	// 		User user = null;
	// 		try {
	// 			user = pm.getObjectById(User.class, userData.getLogin());
	// 		} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
	// 			System.out.println("Exception launched: " + jonfe.getMessage());
	// 		}
	// 		System.out.println("User: " + user);
	// 		if (user != null) {
	// 			System.out.println("Setting password user: " + user);
	// 			user.setPassword(userData.getPassword());
	// 			System.out.println("Password set user: " + user);
	// 		} else {
	// 			System.out.println("Creating user: " + user);
	// 			user = new User(userData.getLogin(), userData.getPassword());
	// 			pm.makePersistent(user);					 
	// 			System.out.println("User created: " + user);
	// 		}
	// 		tx.commit();
	// 		return Response.ok().build();
    //     }
    //     finally
    //     {
    //         if (tx.isActive())
    //         {
    //             tx.rollback();
    //         }
      
	// 	}
	// }

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		System.out.println("vamos vamos vamos");
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();

        System.out.println("DataNucleus AccessPlatform with JDO");
        System.out.println("===================================");

        // Persistence of a set of Accounts and a User
        try
        {	
            tx.begin();
            System.out.println("Persisting users");

            Organizer orga = new Organizer();
            orga.setName("Norman");
            orga.setOrganization("Foster");

			pm.makePersistent(orga);					 
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
