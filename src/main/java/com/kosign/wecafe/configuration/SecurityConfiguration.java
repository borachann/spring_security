package com.kosign.wecafe.configuration;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	CustomSuccessConfiguration customSuccessConfirguation;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception{
		
		/*
		auth.inMemoryAuthentication().withUser("customer")
									 .password("customer")
									 .roles("CUSTOMER");
		auth.inMemoryAuthentication().withUser("seller")
									 .password("seller")
									 .roles("SELLER");
		auth.inMemoryAuthentication().withUser("admin")
		                             .password("admin")
		                             .roles("SELLER" ,"ADMIN");
		*/
		
		//auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());		
		
	}
	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/resources/**");
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }	

	@Bean
	public DaoAuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//.antMatchers("/","/home","/order/**").permitAll()
				.antMatchers("/admin/**").access("hasRole('ADMIN')")
				.antMatchers("/seller/**").access("hasRole('SELLER') or hasRole('ADMIN')")
				//.antMatchers("/seller/**").hasAnyRole("SELLER","ADMIN")
				.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/admin/login")
					.failureUrl("/admin/login?error")
					//.defaultSuccessUrl("/admin/")
					.successHandler(customSuccessConfirguation)
					.usernameParameter("username")
					.passwordParameter("password")
					.permitAll()
				.and()
					.exceptionHandling()
					.accessDeniedPage("/admin/access_denied")
				.and()					
					.sessionManagement()
					.sessionAuthenticationErrorUrl("/admin/login")
					//.invalidSessionUrl("/admin/session_expired")
					.sessionFixation()
					.changeSessionId()
					.maximumSessions(1)
					.maxSessionsPreventsLogin(true)
					.expiredUrl("/admin/login?maxSessions")
					.sessionRegistry(sessionRegistryImpl())
				.and()
				.and().csrf().disable()
					.rememberMe()
					.rememberMeParameter("remember-me")
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(86400)
					.key("SpringSecurityWeCafe");
		
		http.logout()
			.logoutUrl("/admin/logout")
			.logoutSuccessUrl("/admin/login?logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll();
	}
	
	@Bean
    protected SessionRegistry sessionRegistryImpl()
    {
        return new SessionRegistryImpl();
    }
}