package controller;

import controller.Change;

public class ErrorChange extends Change {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2174950327429240069L;
	private final String message;
	
	public ErrorChange(String message) {
		this.message=message;
	}
	
	public String getMessage() {
		return message;
	}
}
