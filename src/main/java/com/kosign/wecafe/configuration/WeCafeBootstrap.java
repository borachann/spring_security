package com.kosign.wecafe.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.stereotype.Controller;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Controller
public class WeCafeBootstrap implements WebApplicationInitializer{
	@Override
	public void onStartup(ServletContext container)
			throws ServletException {
	
		 /*container.getServletRegistration("default").addMapping("/resources/*", "*.css", "*.js", "*.png", "*.gif", "*.jpg");*/
		 
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		
		 rootContext.register(RootContextConfiguration.class);
		 
		 container.addListener(new ContextLoaderListener(rootContext));
		 
		 AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
		 
		 servletContext.register(ServletContextConfiguration.class);
		 
		 ServletRegistration.Dynamic dispatcher = container.addServlet("springDispatcher", 
				 new DispatcherServlet(servletContext));
		 			
		 //dispatcher.setMultipartConfig(getMultipartConfigElement());
		 dispatcher.addMapping("/");
		
/*		container.getServletRegistration("default").addMapping("/resource/*");
		
		XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
		rootContext.setConfigLocation("/WEB-INF/rootContext.xml");		
		container.addListener(new ContextLoaderListener(rootContext));
		
		XmlWebApplicationContext servletContext = new XmlWebApplicationContext();
		servletContext.setConfigLocation("/WEB-INF/servletContext.xml");
		ServletRegistration.Dynamic dispatcher = container.addServlet("springDispatcher",
				new DispatcherServlet(servletContext));
		dispatcher.addMapping("/");*/
	}
	 
    /*private MultipartConfigElement getMultipartConfigElement(){
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }*/
     
    /*Set these variables for your project needs*/
   
/*    private static final String LOCATION = "C:/mytemp/";
    //private static final String LOCATION = "Users/kreysophannatt/Desktop/mytemp/";
 
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 25;//25MB
     
    private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 30;//30MB
 
    private static final int FILE_SIZE_THRESHOLD = 0;*/
	
}
