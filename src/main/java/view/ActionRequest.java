/**
 * 
 */
package view;

import model.actions.*;
import model.game.Player;

/**
 * @author francesco
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
	public ActionRequest(Action action, Player player) {
		super(player);
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

}
