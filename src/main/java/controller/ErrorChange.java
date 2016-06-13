package controller;

import controller.Change;

public class ErrorChange extends Change {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2174950327429240069L;
	private final String message;
	
	/**
	 * Constructor for the message to be sent to the client 
	 * if something has not performed as expected regarding inputs
	 * or the execution of actions
	 * @param message the message of the error
	 */
	public ErrorChange(String message) {
		this.message=message;
	}
	
	/**
	 * @return message the message that will be showed to the client
	 */
	public String getMessage() {
		return message;
	}
}
