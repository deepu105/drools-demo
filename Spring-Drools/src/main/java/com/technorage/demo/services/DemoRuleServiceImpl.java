package com.technorage.demo.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.technorage.demo.drools.FactFinder;
import com.technorage.demo.drools.monitoring.TrackingAgendaEventListener;
import com.technorage.demo.drools.monitoring.TrackingWorkingMemoryEventListener;
import com.technorage.demo.drools.spring.DefaultKieSessionBean;
import com.technorage.demo.drools.spring.KieContainerBean;
import com.technorage.demo.drools.spring.KieServicesBean;
import com.technorage.demo.drools.spring.KieSessionBean;
import com.technorage.demo.facts.Alarm;
import com.technorage.demo.facts.Fire;
import com.technorage.demo.facts.Room;
import com.technorage.demo.facts.Sprinkler;

@Service
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode=ScopedProxyMode.INTERFACES)
public class DemoRuleServiceImpl<T> implements DemoRuleService<T>, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4279066046268640811L;

	private static Logger log = LoggerFactory.getLogger(DemoRuleServiceImpl.class);
            
    public KieSessionBean kieSession;
    
    private TrackingAgendaEventListener agendaEventListener;
    private TrackingWorkingMemoryEventListener workingMemoryEventListener;

    private Map<String,Room> name2room = new HashMap<String,Room>();
    private Map<String,FactHandle> fact2fire = new HashMap<String,FactHandle>();
    
    private FactFinder<Alarm> findAlarms=new FactFinder<>(Alarm.class);
    private FactFinder<Sprinkler> findSprinklers=new FactFinder<>(Sprinkler.class);
    
    @Autowired
    public DemoRuleServiceImpl(
            @Qualifier("demoKieContainer") KieContainerBean kieContainer,@Qualifier("demoKieServices") KieServicesBean kieServices) {
        
        kieSession = new DefaultKieSessionBean(kieServices, kieContainer);
        
        agendaEventListener = new TrackingAgendaEventListener();
        workingMemoryEventListener = new TrackingWorkingMemoryEventListener();

        kieSession.addEventListener(agendaEventListener);
        kieSession.addEventListener(workingMemoryEventListener);

    }
    
    @Override
    public Collection<Alarm> addFire(String[]  fires) {
    	
    	for( String fire: fires ){
    		
    		if(!fact2fire.containsKey(fire)){
	    		Fire roomFire = new Fire( name2room.get(fire) );
	    		FactHandle roomFireHandle = kieSession.insert( roomFire );
	    		fact2fire.put( fire, roomFireHandle );
    		}
    		
    	}
			
		kieSession.fireAllRules();
        //  Collection<T> result = findFact.findFacts(kieSession, new BeanPropertyFilter("key", ""));     
		
		Collection<Alarm> result = findAlarms.findFacts(kieSession);
		
		return result;
    }

	@Override
	public Collection<Alarm> remFire(String[]  fires) {
		
		for( String fire: fires ){
			if(fact2fire.containsKey(fire)){
				kieSession.delete( fact2fire.get(fire) );
				fact2fire.remove( fire );
			}
		}
		

		kieSession.fireAllRules();
		
		Collection<Alarm> result = findAlarms.findFacts(kieSession);
		
		return result;
	}

	@Override
	public void addRooms(String[] names) {
		
		for( String name: names ){

            Room room = new Room( name );

            name2room.put( name, room );

            kieSession.insert( room );

            Sprinkler sprinkler = new Sprinkler( room );

            kieSession.insert( sprinkler );

        }
		
        kieSession.fireAllRules();
        
		
	}
	@Override
	public void addRoom(String name) {
		

        Room room = new Room( name );

        name2room.put( name, room );

        kieSession.insert( room );

        Sprinkler sprinkler = new Sprinkler( room );

        kieSession.insert( sprinkler );

		
        kieSession.fireAllRules();
        
		
	}

	@Override
	public Collection<Alarm> checkForFire() {
		
		Collection<Alarm> result = findAlarms.findFacts(kieSession);
		
		return result;
	}
	
	@Override
	public Collection<Sprinkler> checkSprinklers() {
		
		Collection<Sprinkler> result = findSprinklers.findFacts(kieSession);
		
		return result;
	}

	@Override
	public Room getRoom(String name) {
		
		return name2room.get(name);
	}

    
}
