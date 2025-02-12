package com.AutoHub.autohub_backend.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@ControllerAdvice
@RestController 
@CrossOrigin(origins="http://localhost:5173")
public class GlobalExceptionHandler 
{    
	@ExceptionHandler(Exception.class)    
	@ResponseStatus(HttpStatus.NOT_FOUND)    
	public String handleNotFound(Exception ex) 
	{       
		return "Invalid endpoint. The URL you entered is incorrect or does not exist. Please check and try again.";    
	}
	
	    @ExceptionHandler(BadCredentialsException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    public String handleAuthenticationFailed(BadCredentialsException ex) {
	        return "Invalid username or password. Please try again";
	    }
}