package es.deusto.junit.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;
import es.deusto.server.server.AppService;
import org.junit.Before;
import org.junit.Test;
public class FirstTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(FirstTest.class);
    }
    @Before
    public void setUp() {
        
    }
    @Test
    public void testBagMultiply() {
        assertTrue(true);
    }

    @Test
    public void NooTest() {
        assertFalse(false);
    }
}