/**
 * 
 */
package cg2.controller;

import actions.*;

/**
 * @author Francesco Vetr�
 *
 */
public class ActionChange extends Change {
	
	private final int viewID;
	private final Action action;
	
	public ActionChange(int viewID, Action action) {
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
