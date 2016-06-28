/**
 * 
 */
package controller;

import model.actions.*;

/**
 * @author Francesco Vetro'
 *
 */
public class ActionChange extends Change {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2693204344322751596L;
	private final int viewID;
	private final Action action;
	
	/**
	 * constructor for an action that is sent to the client
	 * @param viewID the id of the view
	 * @param action the action
	 */
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
