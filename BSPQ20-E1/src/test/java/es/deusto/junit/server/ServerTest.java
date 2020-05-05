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
import es.deusto.server.dao.EventDAO;
import es.deusto.server.dao.OrganizerDAO;
import es.deusto.server.dao.PostDAO;
import es.deusto.server.dao.TopicDAO;
import es.deusto.server.dao.UserDAO;
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
        UserDAO udao = DAOFactory.getInstance().createUserDAO();
        User u = udao.getUser(signup.getEmail());
        assertTrue(
            u != null &&
            u.toString().equals("User [name= " + u.getName() +" city=" + u.getCity() +", Saved Events= " +u.getSavedEvents().toString() + " interests= " + u.getInterests().toString()+ "]")
        );
        udao.deleteUser(signup2.getEmail());

        DAOFactory.getInstance().closeDAO(udao);
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

        UserDAO udao = DAOFactory.getInstance().createUserDAO();
        User u = udao.getUser(signup2.getEmail());
        assertTrue(u.getName().equals("test---John Smith 007"));

        udao.deleteUser(signup2.getEmail());

        DAOFactory.getInstance().closeDAO(udao);
    }

    @Test
    public void testOrganizerSignup() {
        SignupAttempt organizerSignup2 = new SignupAttempt();
        organizerSignup2.setName("test---Jack Ryan");
        organizerSignup2.setEmail("test---jack.ryan2@ryan.com");
        organizerSignup2.setPassword("testPassword");
        organizerSignup2.setOrganization("test---Save the organizers");
        server.attemptOrganizerSignup(organizerSignup2);

        // check that the user has been created in the DB
        OrganizerDAO odao = DAOFactory.getInstance().createOrganizerDAO();
        Organizer u = odao.getOrganizer(organizerSignup2.getEmail());
        assertTrue(u != null);
        odao.deleteOrganizer(organizerSignup2.getEmail());
        DAOFactory.getInstance().closeDAO(odao);
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

        OrganizerDAO odao = DAOFactory.getInstance().createOrganizerDAO();
        Organizer o = odao.getOrganizer(organizerSignup2.getEmail());
        assertTrue(o.getName().equals("test---John Smith 007"));

        odao.deleteOrganizer(organizerSignup2.getEmail());

        DAOFactory.getInstance().closeDAO(odao);
    }

    @Test
    public void testCreateEvent() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setName("test---My event");
        eventInfo.setDescription("test---My event description");
        eventInfo.setOrganizerEmail(organizerSignup.getEmail());
        eventInfo.setTopic(new TopicInfo("test---My super own topic"));
        server.createEvent(eventInfo);

        EventDAO edao = DAOFactory.getInstance().createEventDAO();
        Event e = edao.getEvents("test---My event").get(0);
        assertTrue(
            e.getName().equals("test---My event") &&
            e.getDescription().equals("test---My event description") &&
            e.getOrganizer().getEmail().equals(organizerSignup.getEmail())
        );

        edao.deleteEvent(e);
        TopicDAO tdao = DAOFactory.getInstance().createTopicDAO();
        tdao.deleteTopic("test---My super own topic");

        DAOFactory.getInstance().closeDAO(edao);
        DAOFactory.getInstance().closeDAO(tdao);
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

        EventDAO edao = DAOFactory.getInstance().createEventDAO();
        Event e = edao.getEvents("test---My event").get(0);
        Post post = null;

        for (Post p: e.getPosts()) {
            if (p.getTitle().equals(postInfo.getTitle())) {
                post = p;
                break;
            }
        }

        if (post != null) {
            assertTrue(true);
            PostDAO pdao = DAOFactory.getInstance().createPostDAO();
            pdao.deletePost(post);
            DAOFactory.getInstance().closeDAO(pdao);
        } else {
            fail();
        }

        edao.deleteEvent(e);
        TopicDAO tdao = DAOFactory.getInstance().createTopicDAO();
        tdao.deleteTopic("test---My super own topic");

        DAOFactory.getInstance().closeDAO(edao);
        DAOFactory.getInstance().closeDAO(tdao);
    }

    @AfterClass
    public static void tearDown() {
        // delete all the stuff created in the DB during the tests
        UserDAO udao = DAOFactory.getInstance().createUserDAO();
        OrganizerDAO odao = DAOFactory.getInstance().createOrganizerDAO();
        udao.deleteUser(signup.getEmail());
        odao.deleteOrganizer(organizerSignup.getEmail());

        DAOFactory.getInstance().closeDAO(udao);
        DAOFactory.getInstance().closeDAO(odao);
    }

}