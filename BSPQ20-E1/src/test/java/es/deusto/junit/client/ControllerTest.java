package es.deusto.junit.client;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.JUnit4TestAdapter;
import es.deusto.client.controller.Controller;
import es.deusto.client.windows.CreateEvent;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.OrganizerInfo;
import es.deusto.serialization.PostInfo;
import es.deusto.serialization.TopicInfo;
import es.deusto.server.server.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import org.databene.contiperf.*;
import org.databene.contiperf.junit.ContiPerfRule;

import java.util.logging.*;

/**
 * This classes is the one that tests the controller component 
 * to see if it is working as intended.	 
 */

public class ControllerTest extends JerseyTest {

    private final static Logger LOGGER = Logger.getLogger(ControllerTest.class.getName());
	private static Handler fileHandler;  
    private static Handler consoleHandler;
    
    @BeforeClass
    public static void startUp() {
        try {
            consoleHandler = new ConsoleHandler();
            fileHandler = new FileHandler("./log/logTests.log", true); 
            
            fileHandler.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(consoleHandler);  
            LOGGER.addHandler(fileHandler);

            LOGGER.log(Level.INFO, "Launching ControllerTest suite...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    private Controller controller;

    private String userEmail, userPassword;
    private String organizerEmail, organizerPassword;
    private EventInfo eventInfo;
    private PostInfo postInfo;
    private TopicInfo topicInfo;

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ControllerTest.class);
    }

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(Server.class);
    }

    @Before
    public void setUpChild() {
        controller = new Controller(target(""));
        
        userEmail = "test---normal@user.com";
        userPassword = "myPassword";
        organizerEmail = "test---organizer@user.com";
        organizerPassword = "myPassword";
        topicInfo = new TopicInfo("testingEventTopic");

        // create normal user
        controller.attemptNormalSignup(
            userEmail, 
            userPassword, 
            "test---John Smith", 
            "test---NYC", 
            "test---USA",
            new ArrayList<>()
            
        );

        // create organizer
        controller.attemptOrganizerSignup(
            organizerEmail, 
            organizerPassword, 
            "test---Johnny Organizer", 
            "test---Save the organizers"
        );

        //create event
        eventInfo = new EventInfo();
        eventInfo.setName("event Test");
        eventInfo.setDescription("description for this event");
        eventInfo.setOrganizerEmail("test---organizer@user.com");
        eventInfo.setTopic(topicInfo);
        eventInfo.setCity("test---NYC");
        eventInfo.setCountry("test---USA");
        //create a post for the event
        postInfo = new PostInfo();
        postInfo.setTitle("titlePost");
        postInfo.setDescription("descriptionPost");
        postInfo.setDate(new Date());
    }

    @Test
    @Required(max = 2000)
    @PerfTest(invocations = 20)
    public void testCreateDeleteEvent() {
        LOGGER.log(Level.INFO, "Launching testCreateDeleteEvent test...");
        assertTrue(controller.createEvent(eventInfo));
        assertTrue(controller.attemptEventDelete(eventInfo));
    }

    @Test
    @Required(throughput = 4)
    @PerfTest(invocations = 100)
    public void testNormalLogin() {
        LOGGER.log(Level.INFO, "Launching testNormalLogin test...");

        boolean success = controller.attemptNormalLogin(userEmail, userPassword);
        assertTrue(success);
    }

    @Test
    @PerfTest(duration = 10000, threads = 10) // test for at least 10 seconds with 10 threads
    @Required(average = 2000)
    public void testOrganizerLogin() {
        LOGGER.log(Level.INFO, "Launching testOrganizerLogin test...");

        boolean success = controller.attemptNormalLoginOrganizer(organizerEmail, organizerPassword);
        assertTrue(success);
    }

    @Test
    @PerfTest(invocations = 100)
    @Required(max = 1000)
    public void testNormalSignupAndDelete() {
        LOGGER.log(Level.INFO, "Launching testNormalSignupAndDelete test...");

        boolean success = controller.attemptNormalSignup(
            "test---signup.test@test.com",
            "myPassword",
            "test---John Doe",
            "test---Paris",
            "test---France",
            new ArrayList<>()
        );

        assertTrue(success);

        controller.attemptUserDelete();
    }

    @Test
    @PerfTest(invocations = 100)
    @Required(max = 1000)
    public void testOrganizerSignupAndDelete() {
        LOGGER.log(Level.INFO, "Launching testOrganizerSignupAndDelete test...");

        boolean success = controller.attemptOrganizerSignup(
            "test---organizer.signup.test@test.com",
            "myPassword",
            "test---John Doe Organizer",
            "test---Organizateurs sans frontiers"
        );

        assertTrue(success);

        controller.attemptOrganizerDelete();
    }

    @Test
    @PerfTest(duration = 10000)
    @Required(median = 2000)
    public void testUserUpdate() {
        LOGGER.log(Level.INFO, "Launching testUserUpdate test...");

        controller.attemptNormalSignup(
            "test---signup.test@test.com",
            "myPassword",
            "test---John Doe",
            "test---Paris",
            "test---France",
            new ArrayList<>()
        );

        controller.getUser().setCity("test---Tokyo");
        // controller.getUser().getSavedEvents().add(eventInfo);

        boolean success = controller.attemptNormalUpdate();

        assertTrue(success && controller.getUser().getCity().equals("test---Tokyo"));

        controller.attemptUserDelete();
    }
    
    @After
    public void tearDownChild() {
        // delete user
        controller.attemptNormalLogin(userEmail, userPassword);
        controller.attemptUserDelete();

        // delete organizer
        controller.attemptNormalLoginOrganizer(organizerEmail, organizerPassword);
        controller.attemptOrganizerDelete();
    }

}