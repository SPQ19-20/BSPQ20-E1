package es.deusto.server.server;

import javax.print.attribute.standard.Media;
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

	/**
	 * This class is the one that receives the requests from the client.
	 * There is no functionality implemented in this class, all the possible
	 * requests have a corresponding method inside the AppService class
	 * in which all the functionality is implemented. That way, the Server
	 * class is abstracted from the business objects.
	 */

	private AppService appService;

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
		System.out.println("Login attempt received: "+login);

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
		System.out.println("Organizer login attempt received: "+login);
		
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
		System.out.println("Signup attempt received: "+signup);

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
		System.out.println("Signup attempt received: "+signup);

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
		System.out.println("Update attempt received: "+signup);

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
		System.out.println("Update attempt received: "+signup);

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
	 * /passwordRecovery . It is used for password recovery for users who can't remember their passwords.
	 * @param userInfo LoginAttempt with the email of the user who needs a new password
	 * @return Response object with the following message: "OK"
	 */
	@POST
	@Path("/passwordRecovery")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recoverPassword(LoginAttempt userInfo){
		appService.recoverPassword(userInfo);
		return Response.ok("OK").build();
	}

	@POST
	@Path("/createEvent")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEvent(EventInfo eventInfo) {
		appService.createEvent(eventInfo);
		return Response.ok("").build();
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
