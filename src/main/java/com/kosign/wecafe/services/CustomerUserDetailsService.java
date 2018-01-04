package com.kosign.wecafe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.entities.UserRole;
import com.kosign.wecafe.enums.Status;

@Service("customUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUsername(username);
		System.out.println("User : " + user);
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getStatus().equals(Status.ACTIVE), true, true, true, getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (UserRole userRole : user.getUserRoles()) {
			System.out.println("UserRole : " + userRole);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getType()));
		}
		System.out.print("authorities :" + authorities);
		return authorities;
	}
	
	

}
