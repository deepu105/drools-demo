package com.technorage.demo.web.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.technorage.demo.web.config.DemoWebConfig;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoWebConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class DemoWebConfigTest {
    
    @Test
    public void shouldInjectBeans() {
    }
    
}
