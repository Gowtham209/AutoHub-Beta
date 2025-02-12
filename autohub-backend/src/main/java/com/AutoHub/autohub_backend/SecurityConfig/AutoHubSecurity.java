package com.AutoHub.autohub_backend.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AutoHubSecurity {

	@Autowired 
	private UserDetailsService userDetailsServiceObject;
	@Autowired
	private JwtFilter jwtFilterobj;
	@Bean
	public AuthenticationProvider authProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsServiceObject);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}
	// FOR JWT Filter Verification
	/*
	*     AuthenticationManager -> AuthenticationProvider
	*  AuthenticationManager By Default Communicate with AuthenticationProvider to Verification
	* */
	@Bean
	public AuthenticationManager authenticateManger(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	public final String[] SWAGGER_URL = {
			"/api/v1/auth/**",
	        "/v2/api-docs",
	        "/v3/api-docs",
	        "/v3/api-docs/**",
	        "/swagger-resources",
	        "/swagger-resources/**",
	        "/configuration/ui",
	        "/configuration/security",
	        "/swagger-ui/**",
	        "/webjars/**",
	        "/swagger-ui.html",
	        "/api/auth/**",
	        "/api/test/**",
	        "/authenticate" };
	@Bean
	public SecurityFilterChain primarySecurityFilterPoint(HttpSecurity http) throws Exception
	{
		http.cors();
		http.csrf(customizer->customizer.disable());
		http.authorizeHttpRequests(request->request
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
				.permitAll()
				.requestMatchers("api/v1/autohub/signup","api/v1/autohub/login","api/v1/autohub/cars","api/v1/autohub/car/{carId}","api/v1/autohub/categories")
				.permitAll()
				.requestMatchers("api/v1/autohub/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated());
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtFilterobj, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();	
	}
}

