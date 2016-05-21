/**
 * 
 */
package cg2.view;

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
		case Commands.BUILD_EMPORIUM_BY_KING:
		case Commands.ELECT_COUNCILLOR:
		case Commands.BUILD_EMPORIUM_BY_PERMIT:{
			state = new ActionState();
			break;
		}
		default:
			System.out.println("No valid selection");
		}
	}

}
