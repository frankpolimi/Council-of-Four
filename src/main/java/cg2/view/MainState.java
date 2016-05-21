/**
 * 
 */
package cg2.view;

import actions.AcquirePermit;
import actions.BuildEmporiumByKing;
import actions.BuildEmproriumByPermit;
import actions.ElectCouncillor;

/**
 * @author Francesco Vetrò
 *
 */
public class MainState implements State {

	/* (non-Javadoc)
	 * @see cg2.view.State#doAction(cg2.view.View)
	 */
	/**
	 * display the name of the actions that will be used as command
	 * plus a set of generic commands 
	 */
	@Override
	public void display() {
		System.out.println("Insert the action's name to perform:");
		System.out.println("- "+Commands.ACQUIRE_PERMIT);
		System.out.println("- "+Commands.BUILD_EMPORIUM_BY_KING);
		System.out.println("- "+Commands.ELECT_COUNCILLOR);
		System.out.println("- "+Commands.BUILD_EMPORIUM_BY_PERMIT);
		System.out.println("Or insert this command");
		System.out.println("- "+Commands.STATISTICS);
		System.out.println("- "+Commands.BACK);
		System.out.println("- "+Commands.QUIT);
	}

	@Override
	public void doAction(State state, String input) {
		switch (input) {
		case Commands.BACK:{
			state = new StartState();
			break;
		}
		case Commands.ACQUIRE_PERMIT:
			state = new ActionState(AcquirePermit.class.getDeclaredFields());
			break;
		case Commands.BUILD_EMPORIUM_BY_KING:
			state = new ActionState(BuildEmporiumByKing.class.getDeclaredFields());
			break;
		case Commands.ELECT_COUNCILLOR:
			state = new ActionState(ElectCouncillor.class.getDeclaredFields());
			break;
		case Commands.BUILD_EMPORIUM_BY_PERMIT:{
			state = new ActionState(BuildEmproriumByPermit.class.getDeclaredFields());
			break;
		}
		default:
			System.out.println("No valid selection");
		}
	}

}
