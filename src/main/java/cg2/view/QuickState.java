/**
 * 
 */
package cg2.view;

import actions.ElectCouncillorByAssistant;
import actions.QuickAction;

/**
 * @author Francesco Vetrò
 *
 */
public class QuickState implements State {

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	/**
	 * display the name of the actions that will be used as command
	 * plus a set of generic commands 
	 */
	@Override
	public void display() {
		System.out.println("Insert the action's name to perform:");
		System.out.println("- "+Commands.ENGAGE_ASSISTANTS);
		System.out.println("- "+Commands.CHANGE_FACE_UP_PERMITS);
		System.out.println("- "+Commands.ELECT_COUNCILLOR_BY_ASSISTANT);
		System.out.println("- "+Commands.EXTRA_MAIN_ACTION);
		System.out.println("Or insert this command");
		System.out.println("- "+Commands.STATISTICS);
		System.out.println("- "+Commands.BACK);
		System.out.println("- "+Commands.QUIT);
	}
	
	/* (non-Javadoc)
	 * @see cg2.view.State#doAction()
	 */
	@Override
	public void doAction(View view, String input) {
		switch (input) {
		case Commands.BACK:{
			view.setState(new StartState());
			break;
		}
		case Commands.ENGAGE_ASSISTANTS:
		case Commands.CHANGE_FACE_UP_PERMITS:
		case Commands.EXTRA_MAIN_ACTION:{
			break;
		}
		case Commands.ELECT_COUNCILLOR_BY_ASSISTANT:{
			view.setState(
					new ActionState(ElectCouncillorByAssistant.class, view.getPeeker()));
			break;
		}
		default:
			System.out.println("No valid selection");
		}
	}
}	
