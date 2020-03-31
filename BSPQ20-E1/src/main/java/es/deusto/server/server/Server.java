package es.deusto.server.server;

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

	private AppService appService;

	public Server() {
		this.appService = new AppService();
	}

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

	@POST
	@Path("/passwordRecovery")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recoverPassword(LoginAttempt userInfo){
		appService.recoverPassword(userInfo);



		return Response.ok("OK").build();
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
