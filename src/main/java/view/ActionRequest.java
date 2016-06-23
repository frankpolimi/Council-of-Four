/**
 * 
 */
package view;

import model.actions.*;

/**
 * @author Francesco Vetro'
 *
 */
public class ActionRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1157755599802000343L;
	private Action action;
	
	/**
	 * constructor for an action sent to the serverView and used in the controller
	 * @param action the action to perform
	 */
	public ActionRequest(Action action, int iD) {
		super(iD);
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

}
