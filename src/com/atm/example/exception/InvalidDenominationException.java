package com.atm.example.exception;

public class InvalidDenominationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 691991912000099316L;

	public InvalidDenominationException(String msg){
		super(msg);
	}
}
