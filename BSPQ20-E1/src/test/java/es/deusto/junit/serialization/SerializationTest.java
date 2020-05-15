package es.deusto.junit.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;

import junit.framework.JUnit4TestAdapter;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.OrganizerInfo;
import es.deusto.serialization.PostInfo;
import es.deusto.serialization.SignupAttempt;
import es.deusto.serialization.TopicInfo;
import es.deusto.serialization.UserInfo;
import es.deusto.server.data.Event;
import es.deusto.server.data.Organizer;
import es.deusto.server.data.Post;
import es.deusto.server.data.Topic;
import es.deusto.server.data.User;
import es.deusto.server.server.AppService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.*;

/**
 * This classes is the one that tests funcionalities such as creating
 * an event, creating a user, orginzer, crea ting posts and topics.
 * Is the class that checks that all the functionalities work as intended.	 
 */

public class SerializationTest {

    private Event event1, event2;
    private Organizer organizer;
    private Topic topic;
    private Post post1, post2;
    private User user;

    private final static Logger LOGGER = Logger.getLogger(SerializationTest.class.getName());
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

            LOGGER.log(Level.INFO, "Launching SerializationTest suite...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SerializationTest.class);
    }

    @Before
    public void beforeClass() {
        // initialize organizer
        organizer = new Organizer();
        organizer.setName("My organizer");
        organizer.setEmail("john@doe.com");
        organizer.setOrganization("Save the cows");
        organizer.setPassword("thisisapassword");
        organizer.setCreatedEvents(new ArrayList<>());

        // initialize topic
        topic = new Topic();
        topic.setName("Music");

        // initialize event1 -> will be included in organizer's created events list
        event1 = new Event();
        event1.setName("My event");
        event1.setDescription("My event description");
        event1.setOrganizer(organizer);
        organizer.addCreatedEvent(event1);
        event1.setTopic(topic);

        // initialize event2 -> won't be included in organizer's created events list
        event2 = new Event();
        event2.setName("My second event");
        event2.setDescription("My second event description");
        event2.setOrganizer(organizer);
        event2.setTopic(topic);

        // initialize post1
        post1 = new Post();
        post1.setTitle("My first post!");
        post1.setDescription("This is the description of the first post");
        post1.setDate(new Date());
        post1.setOrganizerEmail(organizer.getEmail());
        post1.setEventName(event1.getName());

        // initialize post2
        post2 = new Post();
        post2.setTitle("My second post!");
        post2.setDescription("This is the description of the second post");
        post2.setDate(new Date());
        post2.setOrganizerEmail(organizer.getEmail());
        post2.setEventName(event2.getName());

        // add posts to events
        event1.getPosts().add(post1);
        event1.getPosts().add(post2);

        // initialize user
        user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@doe.com");
        user.setPassword("myPassword");
        user.setCity("NYC");
        ArrayList<Topic> topics = new ArrayList<>();
        topics.add(topic);
        user.setInterests(topics);
        user.setSavedEvents(new ArrayList<>());
        user.getSavedEvents().add(event2);
    }

    @Test
    public void testEventInfoFromEvent() {
        LOGGER.log(Level.INFO, "Launching testEventInfoFromEvent test...");
        EventInfo info = new EventInfo(event1);
        boolean ok = info.getName().equals(event1.getName());
        ok = ok && info.getDescription().equals(event1.getDescription());
        ok = ok && info.getOrganizerEmail().equals(event1.getOrganizer().getEmail());
        ok = ok && info.getTopic().getName().equals(event1.getTopic().getName());
        ok = ok && info.getPosts().size() == event1.getPosts().size();
        assertTrue(ok);
    }

    @Test
    public void testEventInfoSetters() {
        LOGGER.log(Level.INFO, "Launching testEventInfoSetters test...");
        EventInfo info = new EventInfo();
        info.setName("My inner event");
        info.setDescription("My inner description");
        info.setOrganizerEmail(organizer.getEmail());
        info.setTopic(new TopicInfo("toopic"));
        info.setPosts(new ArrayList<>());
        
        assertTrue(
            info.getName().equals("My inner event") &&
            info.getDescription().equals("My inner description") &&
            info.getOrganizerEmail().equals(organizer.getEmail()) && 
            info.getTopic().getName().equals("toopic") &&
            info.getPosts().size() == 0
        );
    }

    @Test
    public void testOrganizerInfoFromOrganizer() {
        LOGGER.log(Level.INFO, "Launching testOrganizerInfoFromOrganizer test...");
        OrganizerInfo info = new OrganizerInfo(organizer);
        assertTrue(
            info.getName().equals(organizer.getName()) &&
            info.getEmail().equals(organizer.getEmail()) &&
            info.getOrganization().equals(organizer.getOrganization()) &&
            info.getCreatedEvents().size() == 0
        );
    }

    @Test
    public void testOrganizerInfoSetters() {
        LOGGER.log(Level.INFO, "Launching testOrganizerInfoSetters test...");
        OrganizerInfo info = new OrganizerInfo(organizer);
        info.setName("John Smith");
        info.setEmail("john.smith@gmail.com");
        info.setOrganization("Save the cows");
        info.setCreatedEvents(new ArrayList<>());

        assertTrue(
            info.getName().equals("John Smith") &&
            info.getEmail().equals("john.smith@gmail.com") &&
            info.getOrganization().equals("Save the cows") &&
            info.getCreatedEvents().size() == 0
        );
    }

    @Test
    public void testPostInfoFromPost() {
        LOGGER.log(Level.INFO, "Launching testPostInfoFromPost test...");
        PostInfo info = new PostInfo(post1);
        assertTrue(
            info.getTitle().equals(post1.getTitle()) &&
            info.getDescription().equals(post1.getDescription()) &&
            info.getDate().equals(post1.getDate()) &&
            info.getEventName().equals(post1.getEventName()) &&
            info.getOrganizerEmail().equals(post1.getOrganizerEmail())
        );
    }

    @Test
    public void testPostInfoSetters() {
        LOGGER.log(Level.INFO, "Launching testPostInfoSetters test...");
        PostInfo info = new PostInfo();
        Date date = new Date();
        info.setTitle("Post title");
        info.setDescription("Post description");
        info.setDate(date);
        info.setEventName("My event name");
        info.setOrganizerEmail("john@smith.com");

        assertTrue(
            info.getTitle().equals("Post title") &&
            info.getDescription().equals("Post description") &&
            info.getDate().equals(date) &&
            info.getEventName().equals("My event name") &&
            info.getOrganizerEmail().equals("john@smith.com")
        );
    }

    @Test
    public void testUserInfoFromUser() {
        LOGGER.log(Level.INFO, "Launching testUserInfoFromUser test...");
        UserInfo info = new UserInfo(user);
        assertTrue(
            info.getName().equals(user.getName()) &&
            info.getEmail().equals(user.getEmail()) &&
            info.getCity().equals(user.getCity()) && 
            info.getInterests().size() == 1 &&
            info.getSavedEvents().size() == 1
        );
    }

    @Test
    public void testUserInfoSetters() {
        LOGGER.log(Level.INFO, "Launching testUserInfoSetters test...");
        UserInfo info = new UserInfo();
        info.setName("John Doe");
        info.setEmail("john.doe@doe.com");
        info.setCity("NYC");
        info.setSavedEvents(new ArrayList<>());
        info.setInterests(new ArrayList<>());
        info.addInterest(new TopicInfo("spooorts"));

        assertTrue(
            info.getName().equals("John Doe") &&
            info.getEmail().equals("john.doe@doe.com") &&
            info.getCity().equals("NYC") &&
            info.getSavedEvents().size() == 0 &&
            info.getInterests().size() == 1
        );
    }

    @Test
    public void testUserInfoStringConstructor() {
        LOGGER.log(Level.INFO, "Launching testUserInfoStringConstructor test...");
        UserInfo info = new UserInfo("John Doe", "john.doe@doe.com", "NYC", "USA");
        assertTrue(
            info.getName().equals("John Doe") &&
            info.getEmail().equals("john.doe@doe.com") &&
            info.getCity().equals("NYC") &&
            info.getCountry().equals("USA")
        );
    }

    @Test
    public void testSignupAttemptUser() {
        LOGGER.log(Level.INFO, "Launching testSignupAttemptUser test...");
        SignupAttempt s = new SignupAttempt();
        s.setName("John Doe");
        s.setEmail("john.doe@doe.com");
        s.setPassword("myPassword");
        s.setCity("NYC");
        s.setInterests(new ArrayList<>());

        User u = s.buildUser();
        
        assertTrue(
            s.getName().equals("John Doe") &&
            s.getEmail().equals("john.doe@doe.com") &&
            s.getPassword().equals("myPassword") &&
            s.getCity().equals("NYC") &&
            s.getInterests().size() == 0 &&
            s.toString().equals(
                "SignupAttempt[email=" + s.getEmail() + ", password=" + s.getPassword() + "]"
            ) &&
            u.getName().equals("John Doe") &&
            u.getEmail().equals("john.doe@doe.com") &&
            u.getPassword().equals("myPassword") &&
            u.getCity().equals("NYC") &&
            u.getInterests().size() == 0
        );
    }

    @Test
    public void testSignupAttemptOrganizer() {
        LOGGER.log(Level.INFO, "Launching testSignupAttemptOrganizer test...");
        SignupAttempt s = new SignupAttempt();
        s.setOrganization("Save the cows");
        
        Organizer o = s.buildOrganizer();

        assertTrue(
            s.getOrganization().equals("Save the cows") &&
            o.getOrganization().equals("Save the cows")
        );
    }

}