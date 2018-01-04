package com.kosign.wecafe.controller.admin;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.Product;

@Controller
public class AdminController {

	@RequestMapping(value="/admin/access_denied")
	public String accessDeniedPage(ModelMap model, Principal principal){
		model.addAttribute("user", principal.getName());
		return "admin/access_denied";
	}
	
	@RequestMapping(value="/admin/session_expired")
	public String sessionExpired(ModelMap model, Principal principal){
		model.addAttribute("user", principal.getName());
		return "admin/session_expired";
	}
	
	@RequestMapping(value="/admin/login", method = RequestMethod.GET)
    public ModelAndView login(
    		@RequestParam(value = "error",required = false) String error,
    	    @RequestParam(value = "logout", required = false) String logout
    	    ){
		System.out.println("LOGGING");
		ModelAndView model = new ModelAndView();
		if (error != null) {
			System.out.println("INVALID Credentials provoide");
            model.addObject("error", "Invalid username and password!");
        }
 
        if (logout != null) {
        	System.out.println("Logged Out");
        	model.addObject("logout", "You have been logged out successfully.");
        }
        model.setViewName("admin/login");

		return model;
    }
	
	@RequestMapping(value="/admin/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if (auth != null){    
          new SecurityContextLogoutHandler().logout(request, response, auth);
       }
       return "redirect:/admin/login?logout";
    }
	
}
