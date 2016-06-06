package controller;

import controller.Change;

public class ErrorChange extends Change {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2174950327429240069L;
	private RuntimeException e;
	
	public ErrorChange(RuntimeException e1) {
		this.e = e1;
	}
}
