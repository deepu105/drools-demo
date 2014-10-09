package com.technorage.demo.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.technorage.demo.rules.DemoKieConfig;

@Configuration
@Import(DemoKieConfig.class)
@ComponentScan("com.technorage.demo.services")
public class DemoServicesConfig {

}
