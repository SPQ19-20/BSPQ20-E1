package es.deusto.server.server;

import java.util.ArrayList;
import java.util.logging.*;

import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.serialization.*;
import es.deusto.server.dao.DAOFactory;
import es.deusto.server.data.*;

@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
public class Server {
	//initialise the logger for the server
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
	private static Handler fileHandler;
	private static Handler consoleHandler;
	/**
	 * This class is the one that receives the requests from the client.
	 * There is no functionality implemented in this class, all the possible
	 * requests have a corresponding method inside the AppService class
	 * in which all the functionality is implemented. That way, the Server
	 * class is abstracted from the business objects.
	 */

	private AppService appService;

	static {
		try {
			consoleHandler = new ConsoleHandler();
            fileHandler = new FileHandler("./log/logServer.log", true); 
           
            fileHandler.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(consoleHandler);  
            LOGGER.addHandler(fileHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Server() {
		this.appService = new AppService();
	}

	//--------------------------LOG IN -------------------------------------------------------------------------------

	/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /login . It is used for the login process of regular users.
	 * @param login Login information of the user (email and password)
	 * @return Response object with the information of the user, in case of valid credentials. 
	 * If not, an empty response is returned
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptNormalLogin(LoginAttempt login) {
		LOGGER.log(Level.INFO, "Login attempt received: ", login);

		User user = appService.attemptNormalLogin(login);

		Object resp = null;
		if (user != null) {
			resp = new UserInfo(user);
		}

		return Response.ok(resp).build();
	}

	/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /loginOrganizer . It is used for the login process of organizers.
	 * @param login Login information of the organizer (email and password)
	 * @return Response object with the information of the organizer, in case of valid credentials. 
	 * If not, an empty response is returned
	 */
	@POST
	@Path("/loginOrganizer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptOrganizerLogin(LoginAttempt login) {
		LOGGER.log(Level.INFO, "Organizer login attempt received: ", login);

		Organizer organizer = appService.attemptOrganizerLogin(login);

		Object resp = null;
		if (organizer != null) {
			resp = new OrganizerInfo(organizer);
		}

		return Response.ok(resp).build();
	}

	//--------------------SIGN UP -----------------------------------------------------------------------------------------

	/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /signup . It is used of the signup method of regular users.
	 * @param signup Signup information of the user (name, email, password, city and interests)
	 * @return Response object with the information of the user, in case of correct user registration. 
	 * If not, an empty response is returned
	 */
	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptNormalSignup(SignupAttempt signup) {
		LOGGER.log(Level.INFO, "Signup attempt received: ", signup);

		User user = appService.attemptNormalSignup(signup);

		Object resp = null;
		if (user != null) {
			resp = new UserInfo(user);
		}

		return Response.ok(resp).build();
	}

	/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /signupOrganizer . It is used of the signup method of organizers.
	 * @param signup Signup information of the organizer (name, email, password and organization)
	 * @return Response object with the information of the organizer, in case of correct registration. 
	 * If not, an empty response is returned
	 */
	@POST
	@Path("/signupOrganizer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptOrganizerSignup(SignupAttempt signup) {
		LOGGER.log(Level.INFO, " Organizer Signup attempt received: ", signup);

		Organizer organizer = appService.attemptOrganizerSignup(signup);

		Object resp = null;
		if (organizer != null) {
			resp = new OrganizerInfo(organizer);
		}

		return Response.ok(resp).build();
	}

	//------------------------------------UPDATE USERS-----------------------------------------------------

/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /update . It is used of the signup method of regular users.
	 * @param signup Signup information of the user (name, email, password, city and interests)
	 * @since Sprint 2
	 * @return Response object with the information of the user, in case of correct user registration. 
	 * If not, an empty response is returned
	 */
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptNormalUpdate(SignupAttempt signup) {
		LOGGER.log(Level.INFO, "Update attempt received: ", signup);

		User user = appService.attemptNormalUpdate(signup); //returns the updated user

		Object resp = null;
		if (user != null) {
			resp = new UserInfo(user); //the response (UserInfo) doesn't need a password
		}

		return Response.ok(resp).build();
	}

		/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /signupOrganizer . It is used of the signup method of organizers.
	 * @param signup Signup information of the organizer (name, email, password and organization)
	 * @since Sprint 2
	 * @return Response object with the information of the organizer, in case of correct registration. 
	 * If not, an empty response is returned
	 */
	@POST
	@Path("/updateOrganizer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptOrganizerUpdate(SignupAttempt signup) {
		LOGGER.log(Level.INFO, "Organizer Update attempt received: ", signup);

		Organizer organizer = appService.attemptOrganizerUpdate(signup);

		Object resp = null;
		if (organizer != null) {
			resp = new OrganizerInfo(organizer); //the response (OrganizerInfo) doesn't need a password
		}

		return Response.ok(resp).build();
	}
	
	//-----------------------------Password and event management------------------------------------------

	/**
 	 * This method is invoked whenever a POST request is made to the following path:
	 * /recomendation . It is used for recommend events to users.
	 * @param signupAttempt Signupattempt with the interests and locations of the user 
	 * @return Response object with the events information
	 */
	@POST
	@Path("/recommendation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecommendedEvents(SignupAttempt signupAttempt) {
		
		LOGGER.log(Level.INFO, "sending recommendations...");

		ArrayList<EventInfo> resp = appService.getRecommendedEvents(signupAttempt);
		SignupAttempt r = new SignupAttempt();
		r.setSaveEvents(resp);

		return Response.ok(r).build();
	}
	
	/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /passwordRecovery . It is used for password recovery for users who can't remember their passwords.
	 * @param userInfo LoginAttempt with the email of the user who needs a new password
	 * @return Response object with the following message: "OK"
	 */
	@POST
	@Path("/passwordRecovery")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recoverPassword(LoginAttempt userInfo){
		LOGGER.log(Level.INFO, "Password Recovery attempt received: "+ userInfo.getEmail());

		appService.recoverPassword(userInfo);
		return Response.ok("OK").build();
	}

	/**
	 * This method is envoked whenever a POST request is made to the following path:
	 * /createEvent . It is used for the creation of new events from a given organizer.
	 * @param eventInfo EventInfo with the data of the event that will be created
	 * @return Response an empty response
	 */
	@POST
	@Path("/createEvent")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEvent(EventInfo eventInfo) {
		LOGGER.log(Level.INFO, "Event creation attempt received: ", eventInfo);

		Event e = appService.createEvent(eventInfo);
		return Response.ok(new EventInfo(e)).build();
	}

	@POST
	@Path("/createPost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPost(PostInfo postInfo) {
		LOGGER.log(Level.INFO, "POST creation attempt received: ", postInfo);

		Post p = appService.createPost(postInfo);
		return Response.ok(new PostInfo(p)).build();
	}

	//------------------------------------DELETE USER-----------------------------------------------------

	/**
	 * This method is invoked whenever a POST request is made to the following path:
	 * /delete . It is used of the delete method of users.
	 * @param userInfo - UserInfo object containing user's email
	 * @since Sprint 2
	 * @return Response string with the user's email, in case of user delete was successful.
	 * If not, an empty response is returned
	 */
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptUserDelete(UserInfo userInfo) {
		String email = userInfo.getEmail();
		LOGGER.log(Level.INFO, "Delete attempt received:  " + email);

		boolean check = appService.deleteUser(email); //returns true if user was deleted successfully

		String resp = null;
		if (check) resp = "Delete: " + email;

		return Response.ok(resp).build();
	}

	@POST
	@Path("/deleteOrganizer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attemptOrganizerDelete(OrganizerInfo organizerInfo) {
		
		String email = organizerInfo.getEmail();
		LOGGER.log(Level.INFO, "Organizer delete attempt received:  " + email);

		boolean check = appService.deleteOrganizer(email); //returns true if organizer was deleted successfully

		String resp = null;
		if (check) resp = "Delete: " + email;

		return Response.ok(resp).build();
	}



	// --------------------------------------------------------------

	// method used for manual testing 
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
        // Persistence of a set of Accounts and a User
		appService.hello();
		return Response.ok("Hello world!").build();
	}
}
