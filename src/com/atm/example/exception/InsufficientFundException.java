package com.atm.example.exception;

public class InsufficientFundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 691991912000099316L;

	public InsufficientFundException(String msg){
		super(msg);
	}
}
