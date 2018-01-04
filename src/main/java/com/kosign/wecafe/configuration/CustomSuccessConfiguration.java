package com.kosign.wecafe.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessConfiguration extends SimpleUrlAuthenticationSuccessHandler{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String targetURL = determineTargetURL(authentication);
		if(response.isCommitted()){
			System.out.println("CANNOT REDIRECT");
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetURL);
	}
	
	protected String determineTargetURL(Authentication authentication){
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		List<String> roles = new ArrayList<String>();
		
		for(GrantedAuthority authority : authorities){
			System.out.println(authority.getAuthority());
			roles.add(authority.getAuthority());
		}
		if(roles.contains("ROLE_CUSTOMER")){
			return "/order/";
		}else if(roles.contains("ROLE_SELLER")){
			return "/seller/";
		}else if(roles.contains("ROLE_ADMIN")){
			return "/admin/";
		}else{
			return "/admin/access_denied";
		}
		
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	
}
