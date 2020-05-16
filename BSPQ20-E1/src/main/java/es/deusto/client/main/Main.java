package es.deusto.client.main;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.CreateEvent;
import es.deusto.client.windows.EventWindow;
import es.deusto.client.windows.ForgottenPassword;
import es.deusto.client.windows.LogInWindow;

import es.deusto.client.windows.UserEventsWindow;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.OrganizerInfo;
import es.deusto.serialization.PostInfo;
import es.deusto.serialization.TopicInfo;

/**
 * This class is the one that starts the aplication.
 * 
 * It creates a new controller for the user to connect to the
 * server and opens a new LogInWindow where the user can start
 * using the application by login in or creating a new user.
 */

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Controller c = new Controller("localhost", "8080");

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		new LogInWindow(c);

		// OrganizerInfo organizer = new OrganizerInfo();
		// organizer.setName("Organizer name");
		// organizer.setEmail("this.is@somemail.com");
		// organizer.setOrganization("Save the organizers");

		// ArrayList<EventInfo> events = new ArrayList<>();

		// EventInfo event = new EventInfo();
		// event.setName("My testing event");
		// event.setDescription("This is the description of the event. This text should consist of a couple of sentendes.");
		// event.setOrganizerEmail("this.is@somemail.org");
		// event.setTopic(new TopicInfo("ToToTopic"));
		// ArrayList<PostInfo> posts = new ArrayList<>();
		// PostInfo p1 = new PostInfo();
		// p1.setDate(new Date());
		// p1.setTitle("This is the title of the first post");
		// p1.setDescription("This is the description (or body) of the post inside the event");
		// p1.setEventName("This should be the same as the name of the event");
		// p1.setOrganizerEmail("This should be the email of the organizer");
		
		// PostInfo p2 = new PostInfo();
		// p2.setDate(new Date());
		// p2.setTitle("This is the title of the second post");
		// p2.setDescription("This is the description (or body) of the second post inside the event");
		// p2.setEventName("This should be the same as the name of the event");
		// p2.setOrganizerEmail("This should be the email of the organizer");

		// event.getPosts().add(p1);
		// event.getPosts().add(p2);

		// EventInfo event2 = new EventInfo();
		// event2.setName("My second testing event");
		// event2.setDescription("This is the description of the second event. This text should consist of a couple of sentendes.");
		// event2.setOrganizerEmail("this.is@somemail.org");
		// event2.setTopic(new TopicInfo("ToToTopic"));
		// event2.setPosts(new ArrayList<>());

		// events.add(event);
		// events.add(event2);
		// organizer.setCreatedEvents(events);

		// c.setOrganizer(organizer);

		// new CreateEvent(c);
		// new OrganizerHome(c);
	}

}