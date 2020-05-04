package es.deusto.junit.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.AfterClass;

import junit.framework.JUnit4TestAdapter;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.LoginAttempt;
import es.deusto.serialization.OrganizerInfo;
import es.deusto.serialization.PostInfo;
import es.deusto.serialization.SignupAttempt;
import es.deusto.serialization.TopicInfo;
import es.deusto.serialization.UserInfo;
import es.deusto.server.dao.DAOFactory;
import es.deusto.server.data.Event;
import es.deusto.server.data.Organizer;
import es.deusto.server.data.Post;
import es.deusto.server.data.User;
import es.deusto.server.server.Server;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerTest {

    private static Server server;

    private static SignupAttempt signup;
    private static SignupAttempt organizerSignup;

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ServerTest.class);
    }

    @BeforeClass
    public static void setUp() {
        server = new Server();

        signup = new SignupAttempt();
        signup.setName("test---John Doe");
        signup.setEmail("test---john.doe@doe.com");
        signup.setPassword("testPassword");
        signup.setCity("test---NYC");

        // this user will be the one used for everything except for signup
        server.attemptNormalSignup(signup);

        organizerSignup = new SignupAttempt();
        organizerSignup.setName("test---Jack Ryan");
        organizerSignup.setEmail("test---jack.ryan@ryan.com");
        organizerSignup.setPassword("testPassword");
        organizerSignup.setOrganization("test---Save the organizers");

        // this organizer will be the one used for everything except for signup
        server.attemptOrganizerSignup(organizerSignup);
    }

    @Test
    public void testNormalSignup() {
        SignupAttempt signup2 = new SignupAttempt();
        signup2.setName("test---John Doe");
        signup2.setEmail("test---john.doe2@doe.com");
        signup2.setPassword("testPassword");
        signup2.setCity("test---NYC");
        server.attemptNormalSignup(signup2);

        // check that the user has been created in the DB
        User u = DAOFactory.getInstance().createUserDAO().getUser(signup.getEmail());
        assertTrue(
            u != null &&
            u.toString().equals("User [name= " + u.getName() +" city=" + u.getCity() +", Saved Events= " +u.getSavedEvents().toString() + " interests= " + u.getInterests().toString()+ "]")
        );
        DAOFactory.getInstance().createUserDAO().deleteUser(signup2.getEmail());
    }

    @Test
    public void testNormalUpdate() {
        SignupAttempt signup2 = new SignupAttempt();
        signup2.setName("test---John Smith");
        signup2.setEmail("test---john.smith@smith.com");
        signup2.setPassword("testPassword");
        signup2.setCity("test---NYC");
        server.attemptNormalSignup(signup2);

        signup2.setName("test---John Smith 007");
        server.attemptNormalUpdate(signup2);

        User u = DAOFactory.getInstance().createUserDAO().getUser(signup2.getEmail());
        assertTrue(u.getName().equals("test---John Smith 007"));

        DAOFactory.getInstance().createUserDAO().deleteUser(signup2.getEmail());
    }

    // @Test
    // public void testNormalLogin() {
    //     LoginAttempt login = new LoginAttempt(signup.getEmail(), signup.getPassword(), false);
    //     UserInfo user = (UserInfo) server.attemptNormalLogin(login).getEntity();
    //     assertTrue(
    //         user.getEmail().equals(signup.getEmail()) &&
    //         user.getName().equals(signup.getName()) &&
    //         user.getCity().equals(signup.getCity())
    //     );
    // }

    // @Test 
    // public void testRecoverPassword() {
    //     LoginAttempt login = new LoginAttempt(signup.getEmail(), signup.getPassword(), false);
    //     server.recoverPassword(login);
    //     User u = DAOFactory.getInstance().createUserDAO().getUser(login.getEmail());
    //     // make sure the password has changed
    //     assertTrue(!signup.getPassword().equals(u.getPassword()));
    // }

    @Test
    public void testOrganizerSignup() {
        SignupAttempt organizerSignup2 = new SignupAttempt();
        organizerSignup2.setName("test---Jack Ryan");
        organizerSignup2.setEmail("test---jack.ryan2@ryan.com");
        organizerSignup2.setPassword("testPassword");
        organizerSignup2.setOrganization("test---Save the organizers");
        server.attemptOrganizerSignup(organizerSignup2);

        // check that the user has been created in the DB
        Organizer u = DAOFactory.getInstance().createOrganizerDAO().getOrganizer(organizerSignup2.getEmail());
        assertTrue(u != null);
        DAOFactory.getInstance().createOrganizerDAO().deleteOrganizer(organizerSignup2.getEmail());
    }

    @Test
    public void testOrganizerLogin() {
        LoginAttempt login = new LoginAttempt(organizerSignup.getEmail(), organizerSignup.getPassword(), true);
        OrganizerInfo organizer = (OrganizerInfo) server.attemptOrganizerLogin(login).getEntity();
        assertTrue(
            organizer.getEmail().equals(organizerSignup.getEmail()) &&
            organizer.getName().equals(organizerSignup.getName()) &&
            organizer.getOrganization().equals(organizerSignup.getOrganization())
        );
    }

    @Test
    public void testOrganizerUpdate() {
        SignupAttempt organizerSignup2 = new SignupAttempt();
        organizerSignup2.setName("test---Jack Ryan");
        organizerSignup2.setEmail("test---jack.ryan2@ryan.com");
        organizerSignup2.setPassword("testPassword");
        organizerSignup2.setOrganization("test---Save the organizers");        
        server.attemptOrganizerSignup(organizerSignup2);

        organizerSignup2.setName("test---John Smith 007");
        server.attemptOrganizerUpdate(organizerSignup2);

        Organizer o = DAOFactory.getInstance().createOrganizerDAO().getOrganizer(organizerSignup2.getEmail());
        assertTrue(o.getName().equals("test---John Smith 007"));

        DAOFactory.getInstance().createOrganizerDAO().deleteOrganizer(organizerSignup2.getEmail());
    }

    @Test
    public void testCreateEvent() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setName("test---My event");
        eventInfo.setDescription("test---My event description");
        eventInfo.setOrganizerEmail(organizerSignup.getEmail());
        eventInfo.setTopic(new TopicInfo("test---My super own topic"));
        server.createEvent(eventInfo);

        Event e = DAOFactory.getInstance().createEventDAO().getEvents("test---My event").get(0);
        assertTrue(
            e.getName().equals("test---My event") &&
            e.getDescription().equals("test---My event description") &&
            e.getOrganizer().getEmail().equals(organizerSignup.getEmail())
        );

        DAOFactory.getInstance().createEventDAO().deleteEvent(e);
        DAOFactory.getInstance().createTopicDAO().deleteTopic("test---My super own topic");
    }

    @Test 
    public void testCreatePost() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setName("test---My event");
        eventInfo.setDescription("test---My event description");
        eventInfo.setOrganizerEmail(organizerSignup.getEmail());
        eventInfo.setTopic(new TopicInfo("test---My super own topic"));
        server.createEvent(eventInfo);

        PostInfo postInfo = new PostInfo();
        postInfo.setDate(new Date());
        postInfo.setTitle("test---Post title");
        postInfo.setDescription("test---Post description");
        postInfo.setEventName(eventInfo.getName());
        postInfo.setOrganizerEmail(eventInfo.getOrganizerEmail());
        server.createPost(postInfo);

        Event e = DAOFactory.getInstance().createEventDAO().getEvents("test---My event").get(0);
        Post post = null;

        for (Post p: e.getPosts()) {
            if (p.getTitle().equals(postInfo.getTitle())) {
                post = p;
                break;
            }
        }

        if (post != null) {
            assertTrue(true);
            DAOFactory.getInstance().createPostDAO().deletePost(post);
        } else {
            fail();
        }

        DAOFactory.getInstance().createEventDAO().deleteEvent(e);
        DAOFactory.getInstance().createTopicDAO().deleteTopic("test---My super own topic");
    }

    @AfterClass
    public static void tearDown() {
        // delete all the stuff created in the DB during the tests
        DAOFactory.getInstance().createUserDAO().deleteUser(signup.getEmail());
        DAOFactory.getInstance().createOrganizerDAO().deleteOrganizer(organizerSignup.getEmail());
    }

}