package com.technorage.demo.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {
	 

	 	@Override
	    public void onStartup(ServletContext container) {
	        // Create the 'root' Spring application context
	        //AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	        //rootContext.register(OccsWebConfig.class,OccsServicesConfig.class);
	 
	        // Manage the lifecycle of the root application context
	        //container.addListener(new ContextLoaderListener());
	 
	        // Create the dispatcher servlet's Spring application context
	        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
	        dispatcherServlet.register(DemoWebConfig.class);
	             
	        // Register and map the dispatcher servlet
	        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
	        dispatcher.setLoadOnStartup(1);
	        dispatcher.addMapping("/");
	         
	    }
 
}
