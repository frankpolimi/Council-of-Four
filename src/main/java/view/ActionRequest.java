/**
 * 
 */
package view;

import model.actions.*;

/**
 * @author francesco
 *
 */
public class ActionRequest extends Request {
	
	private Action action;
	
	/**
	 * constructor for an action sent to the serverView and used in the controller
	 * @param action the action to perform
	 */
	public ActionRequest(Action action) {
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

}
