package com.AutoHub.autohub_backend.CustomExceptions;

public class UserAddressException extends Exception {
	public UserAddressException(String error)
	{
		super(error);
	}
}