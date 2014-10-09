package com.technorage.demo.web.controllers;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.technorage.demo.facts.Alarm;
import com.technorage.demo.facts.Sprinkler;
import com.technorage.demo.forms.DemoForm;
import com.technorage.demo.services.DemoRuleService;
import com.technorage.demo.utils.DateUtils;

@Controller
public class HomeControllerImpl implements HomeController {

    private static Logger logger = LoggerFactory.getLogger(HomeControllerImpl.class);

    @Autowired
    private DemoRuleService<?> ruleService;

    private Map<String, String> rooms = new LinkedHashMap<String,String>();
	/**
	 * Simply selects the home view to render by returning its name.
	 */
    @Override
	public String index(Locale locale, Model model) {
    	return getIndex(locale, model);
	}
	@Override
	public String addRoom(DemoForm demoForm, Locale locale, Model model) {
		logger.info("Adding room: " + demoForm.getRoomName());
		if(demoForm.getRoomName()!=null && !demoForm.getRoomName().isEmpty()){
			if(rooms.containsKey(demoForm.getRoomName())){
				model.addAttribute("isError", true);
				model.addAttribute("error", "Room already exists");
			}else{
				ruleService.addRoom(demoForm.getRoomName());
				rooms.put(demoForm.getRoomName(), demoForm.getRoomName());
				model.addAttribute("isError", false);
			}
		}else{
			model.addAttribute("isError", true);
			model.addAttribute("error", "Room name is empty");
		}
		return getIndex(locale, model);
	}
	@Override
	public String addFire(DemoForm demoForm,Locale locale, Model model) {
		if(demoForm.getFireRoomName()!=null && !demoForm.getFireRoomName().isEmpty()){
			logger.info("Adding fire: " + demoForm.getFireRoomName());
			String[] fires = new String[]{demoForm.getFireRoomName()};
			ruleService.addFire(fires);
		}
		return getIndex(locale, model);
	}
	@Override
	public String remFire(DemoForm demoForm,Locale locale, Model model) {
		if(demoForm.getFireRoomName()!=null && !demoForm.getFireRoomName().isEmpty()){
			logger.info("Removing fire: " + demoForm.getFireRoomName());
			String[] fires = new String[]{demoForm.getFireRoomName()};
			ruleService.remFire(fires);
		}
		return getIndex(locale, model);
	}
	
	private String getIndex(Locale locale, Model model){
		
		logger.info("Welcome to rules demo! The client locale is {}.", locale);
		
		model.addAttribute("serverTime", DateUtils.getFormattedDate(locale) );
		
		Collection<Alarm> alarms=ruleService.checkForFire();
		
		Collection<Sprinkler> sprinklers=ruleService.checkSprinklers();
			
		model.addAttribute("alarmsFound", alarms!=null && alarms.size()!=0? true:false );
		
		model.addAttribute("noOfAlarms", alarms.size());
		
		model.addAttribute("alarms", alarms);
		model.addAttribute("sprinklers", sprinklers);
		model.addAttribute("rooms", rooms);
		model.addAttribute("demoForm", new DemoForm());
		
		return "index";
	}
	
}
