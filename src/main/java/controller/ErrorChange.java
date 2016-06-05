package controller;

import controller.Change;

public class ErrorChange extends Change {
	
	private Exception e;

	public ErrorChange(RuntimeException e1) {
		this.e = e1;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2174950327429240069L;

}
