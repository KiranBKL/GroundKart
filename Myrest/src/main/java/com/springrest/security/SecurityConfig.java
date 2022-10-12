package com.springrest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
	
    auth.inMemoryAuthentication().withUser("kiran").password(passwordEncoder().encode("Yadavagiri")).roles("admin") 
     .and().withUser("user1").password(passwordEncoder().encode("userpass")).roles("user");
	}
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception
	{ 
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("admin")
		.antMatchers("/product/**").hasAnyRole("user","admin")
		.antMatchers("/order/**").hasAnyRole("user","admin")
		.antMatchers("/login/**").hasAnyRole("user","admin")
		.antMatchers("/customer/**").hasAnyRole("user")
		.antMatchers("/cart/**").hasAnyRole("user")
		.antMatchers("/buy/**").hasAnyRole("user")
//		.antMatchers("/customer/removeuser/**").hasAnyRole("admin")
//		.antMatchers("/customer/getcustomers/**").hasAnyRole("admin")
//		.antMatchers("/cart/**").hasAnyRole("user")
//		.antMatchers("/buy/**").hasAnyRole("user")
//		.antMatchers("/orders/**").hasAnyRole("user")
//		.antMatchers("/customer/**").hasAnyRole("user")
//		.antMatchers("/login/**").hasAnyRole("user")
//		.antMatchers("/product/**").hasAnyRole("user")
		
		.anyRequest().authenticated() 
		.and().httpBasic();
		http.csrf().disable();
	}
	
	
	@Bean 
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


}