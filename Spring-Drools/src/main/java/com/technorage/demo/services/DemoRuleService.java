package com.technorage.demo.services;

import java.util.Collection;

import com.technorage.demo.facts.Alarm;
import com.technorage.demo.facts.Room;
import com.technorage.demo.facts.Sprinkler;


public interface DemoRuleService<T> {

    Collection<Alarm>  addFire(String[] fires) ;
    
    Collection<Alarm>  remFire(String[]  fires) ;

	void addRooms(String[] names);
	
	Collection<Alarm> checkForFire();
    
	Room getRoom(String name);

	void addRoom(String name);

	Collection<Sprinkler> checkSprinklers();
    
}
