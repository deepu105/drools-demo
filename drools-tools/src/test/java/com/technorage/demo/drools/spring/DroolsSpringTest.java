package com.technorage.demo.drools.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.technorage.demo.drools.KieBuildException;
import com.technorage.demo.drools.monitoring.TrackingAgendaEventListener;
import com.technorage.demo.drools.monitoring.TrackingWorkingMemoryEventListener;
import com.technorage.demo.drools.spring.KieContainerBean;
import com.technorage.demo.drools.spring.KieServicesBean;
import com.technorage.demo.drools.spring.KieSessionBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DroolsSpringTestConfig.class}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles({ "drools" })
public class DroolsSpringTest {

    @Autowired
    @Qualifier("myKieServicesBean")
    private KieServicesBean kieServices;
    @Autowired
    @Qualifier("myKieContainerBean")
    private KieContainerBean kieContainer;
    @Autowired
    private KieSessionBean kieSession;
    
    private TrackingAgendaEventListener agendaEventListener;
    private TrackingWorkingMemoryEventListener workingMemoryEventListener;
    
    @Before
    public void initialize() throws KieBuildException {
        agendaEventListener = new TrackingAgendaEventListener();
        workingMemoryEventListener = new TrackingWorkingMemoryEventListener();
        
        kieSession.addEventListener(agendaEventListener);
        kieSession.addEventListener(workingMemoryEventListener);
    }
    
    @Test
    public void shouldConfigureDroolsComponents() {
        assertNotNull(kieServices);
        assertNotNull(kieContainer);
        assertNotNull(kieSession);
        
        assertEquals(0, agendaEventListener.getActivationList().size());
        assertEquals(0, workingMemoryEventListener.getAllEvents().size());
        assertEquals(0, workingMemoryEventListener.getInsertions().size());
        assertEquals(0, workingMemoryEventListener.getUpdates().size());
        assertEquals(0, workingMemoryEventListener.getRetractions().size());
    }

}
