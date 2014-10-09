package com.technorage.demo.rules;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.technorage.demo.drools.KieBuildException;
import com.technorage.demo.drools.monitoring.TrackingAgendaEventListener;
import com.technorage.demo.drools.monitoring.TrackingWorkingMemoryEventListener;
import com.technorage.demo.drools.spring.DefaultKieSessionBean;
import com.technorage.demo.drools.spring.KieContainerBean;
import com.technorage.demo.drools.spring.KieServicesBean;
import com.technorage.demo.drools.spring.KieSessionBean;
import com.technorage.demo.facts.Fire;
import com.technorage.demo.facts.Room;
import com.technorage.demo.facts.Sprinkler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DemoKieConfig.class }, loader = AnnotationConfigContextLoader.class)
//@ActiveProfiles({ "drools" })
public class DemoRulesTest {

    private static Logger log = LoggerFactory.getLogger(DemoRulesTest.class);

    @Autowired
    @Qualifier("demoKieServices")
    private KieServicesBean kieServices;

    @Autowired
    @Qualifier("demoKieContainer")
    private KieContainerBean kieContainer;

    private KieSessionBean kieSession;

    private TrackingAgendaEventListener agendaEventListener;
    private TrackingWorkingMemoryEventListener workingMemoryEventListener;
    
    private String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};

    private Map<String,Room> name2room = new HashMap<String,Room>();
    
    
    //private FactFinder<Flight> validFlightFinder = new FactFinder<>(Flight.class);

    @Before
    public void initialize() throws KieBuildException {
        if (kieSession != null) {
            kieSession.dispose();
        }
        kieSession = new DefaultKieSessionBean(kieServices, kieContainer);
        
        agendaEventListener = new TrackingAgendaEventListener();
        workingMemoryEventListener = new TrackingWorkingMemoryEventListener();

        kieSession.addEventListener(agendaEventListener);
        kieSession.addEventListener(workingMemoryEventListener);
        
        for( String name: names ){

            Room room = new Room( name );

            name2room.put( name, room );

            kieSession.insert( room );

            Sprinkler sprinkler = new Sprinkler( room );

            kieSession.insert( sprinkler );

        }


        kieSession.fireAllRules();
    }

    /**
     * If this passes, then the Spring components were autowired.
     */
    @Test
    public void shouldConfigureDroolsComponents() {
        assertNotNull(kieServices);
        assertNotNull(kieContainer);
        assertNotNull(kieSession);
        log.info(">> KIE Beans configured success");
    }

	@Test
    public void shouldRaiseFire() throws ParseException {

        Fire kitchenFire = new Fire( name2room.get( "kitchen" ) );

        Fire officeFire = new Fire( name2room.get( "office" ) );
        
        FactHandle kitchenFireHandle = kieSession.insert( kitchenFire );

        FactHandle officeFireHandle = kieSession.insert( officeFire );
			
		kieSession.fireAllRules();
        
    }
    
	@Test
    public void shouldRemFire() throws ParseException {
        
		Fire kitchenFire = new Fire( name2room.get( "kitchen" ) );

        Fire officeFire = new Fire( name2room.get( "office" ) );
        
        FactHandle kitchenFireHandle = kieSession.insert( kitchenFire );

        FactHandle officeFireHandle = kieSession.insert( officeFire );
			
		kieSession.fireAllRules();
		
		kieSession.delete( kitchenFireHandle );

		kieSession.delete( officeFireHandle );


		kieSession.fireAllRules();
    }
   

 
   
    
}
