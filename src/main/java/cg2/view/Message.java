/**
 * 
 */
package cg2.view;

import actions.*;

/**
 * @author Francesco Vetrò
 *
 */
public class Message {
	
	private final int viewID;
	private final Action action;
	
	public Message(int viewID, Action action) {
		this.viewID = viewID;
		this.action = action;
	}

	/**
	 * @return the viewID
	 */
	public int getViewID() {
		return viewID;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

}
