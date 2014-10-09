package com.technorage.demo.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.technorage.demo.facts.Alarm;
import com.technorage.demo.facts.Sprinkler;
import com.technorage.demo.web.config.DemoServicesConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "drools" })
@WebAppConfiguration
@ContextConfiguration(classes = { DemoServicesConfig.class }, loader = AnnotationConfigWebContextLoader.class)
public class DemoRuleServiceTest {

	private static Logger log = LoggerFactory.getLogger(DemoRuleServiceTest.class);
    @Autowired
    private DemoRuleService<?> occsRuleService;
    
    @Test
    public void shouldInjectBeans() {
        assertNotNull(occsRuleService);
        //log.info("Bean Injected"+occsRuleService.addFire(null));
    }
    @Test
    public void shouldAddRooms() throws ParseException {
    	System.out.println("-------------->>> Adding rooms only");
        String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
    	occsRuleService.addRooms(names);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void shouldAddFire() throws ParseException {
    	
    	System.out.println("-------------->>> Adding rooms and creating fire");
    	
    	String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
    	System.out.println("-------------->>> Adding rooms: ");
    	occsRuleService.addRooms(names);
    	String[] fires = new String[]{"kitchen", "bedroom"};
    	System.out.println("-------------->>> Adding Fire: ");
    	Collection<Alarm> res=occsRuleService.addFire(fires);
    	System.out.println("-------------->>> Alarms: "+res.toString());

    	assertEquals("Alarm should be found",fires.length, res.size());
    	
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void shouldRemFire() throws ParseException {
    	System.out.println("Adding rooms, then create and remove fire");
    	
    	String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
    	System.out.println("-------------->>> Adding rooms: ");
    	occsRuleService.addRooms(names);
    	String[] fires = new String[]{"kitchen", "office"};
    	System.out.println("-------------->>> Adding Fire: ");
    	occsRuleService.addFire(fires);
 
    	System.out.println("-------------->>> Removing Fire: ");
    	Collection<Alarm> res=occsRuleService.remFire(fires);
    	System.out.println("-------------->>> Alarms: "+res.toString());
    	//assertEquals("Alarm should not be found",0, res.size());
    	
    }
    
    @Test
    public void checkForFire() throws ParseException {
		
    	System.out.println("-------------->>> Adding rooms and creating fire");
    	
    	String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
    	occsRuleService.addRooms(names);
    	String[] fires = new String[]{"kitchen", "bedroom"};
    	occsRuleService.addFire(fires);
    	
    	Collection<Alarm> res=occsRuleService.checkForFire();
    	System.out.println("-------------->>> Alarms: "+res.toString());
    	
    	assertEquals("Alarm should be found",fires.length, res.size());

	}
    
    @Test
    public void checkSprinklers() throws ParseException {
		
    	System.out.println("-------------->>> Adding rooms and creating fire");
    	
    	String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
    	occsRuleService.addRooms(names);
    	String[] fires = new String[]{"kitchen", "bedroom"};
    	occsRuleService.addFire(fires);
    	
    	Collection<Sprinkler> res=occsRuleService.checkSprinklers();
    	System.out.println("-------------->>> Alarms: "+res.toString());
    	
    	assertNotNull(res);

	}
    
    
}
