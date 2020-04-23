package es.deusto.junit.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;
import es.deusto.server.server.AppService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.databene.contiperf.*;
import org.databene.contiperf.junit.ContiPerfRule;

public class FirstTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();
    
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(FirstTest.class);
    }

    @Before
    public void setUp() {
        
    }

    @PerfTest(invocations = 5000)
    @Test
    public void testBagMultiply() {
        System.out.println("I am testing!");
    }

    @Test
    public void NooTest() {
        assertFalse(false);
    }
}