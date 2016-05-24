/**
 * 
 */
package cg2.view;

import actions.AcquirePermit;
import actions.BuildEmporiumByKing;
import actions.BuildEmproriumByPermit;
import actions.ElectCouncillor;

/**
 * @author Francesco Vetr�
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
	public void doAction(View view, String input) {
		switch (input) {
		case Commands.BACK:{
			view.setState(new StartState());
			break;
		}
		case Commands.ACQUIRE_PERMIT:{
			view.setActionClass(AcquirePermit.class);
			view.setState(
					new ActionState(AcquirePermit.class, view));
			break;
		}
		case Commands.BUILD_EMPORIUM_BY_KING:
			view.setActionClass(BuildEmporiumByKing.class);
			view.setState(
					new ActionState(BuildEmporiumByKing.class, view));
			break;
		case Commands.ELECT_COUNCILLOR:
			view.setActionClass(ElectCouncillor.class);
			view.setState(
					new ActionState(ElectCouncillor.class, view));
			break;
		case Commands.BUILD_EMPORIUM_BY_PERMIT:{
			view.setActionClass(BuildEmproriumByPermit.class);
			view.setState(
					new ActionState(BuildEmproriumByPermit.class, view));
			break;
		}
		case Commands.STATISTICS:
		case Commands.QUIT:
			break;
		}
	}

}
