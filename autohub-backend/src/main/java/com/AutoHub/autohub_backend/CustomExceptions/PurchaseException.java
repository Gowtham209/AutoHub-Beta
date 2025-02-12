package com.AutoHub.autohub_backend.CustomExceptions;

public class PurchaseException extends Exception {
	public PurchaseException(String error)
	{
		super(error);
	}
}