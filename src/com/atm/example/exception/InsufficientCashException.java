package com.atm.example.exception;

public class InsufficientCashException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4900900875443417221L;
	
	public InsufficientCashException(String msg){
		super(msg);
	}
}
