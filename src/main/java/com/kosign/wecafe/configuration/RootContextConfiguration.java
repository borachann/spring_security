package com.kosign.wecafe.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan(basePackages = "com.kosign.wecafe",
			   excludeFilters = @ComponentScan.Filter({Controller.class,RestController.class}))
//@Import({SecurityConfiguration.class})
public class RootContextConfiguration {

	
	
	 
	
	
}