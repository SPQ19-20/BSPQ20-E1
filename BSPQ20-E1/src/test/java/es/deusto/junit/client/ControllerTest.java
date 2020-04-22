package es.deusto.junit.client;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.JUnit4TestAdapter;
import es.deusto.client.controller.Controller;
import es.deusto.server.server.Server;

import org.junit.Before;
import org.junit.Test;

public class ControllerTest extends JerseyTest {

    private Controller controller;

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
    }

    @Test
    public void testNormalLogin() {
        controller.attemptNormalLogin("", "");
        assertEquals("Should return status 200", 200, 200);
    }

}