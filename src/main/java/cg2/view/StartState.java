/**
 * 
 */
package cg2.view;

/**
 * @author Francesco Vetrò
 *
 */
public class StartState implements State {

	/**
	 * display to the view the first selection's level
	 * that will lead to perform an action
	 */
	@Override
	public void display() {
		System.out.println("Insert command");
		System.out.println("- "+Commands.MAIN_ACTION);
		System.out.println("- "+Commands.QUICK_ACTION);
		System.out.println("- "+Commands.SKIP+"(this skip only the quick action)");
		System.out.println("- "+Commands.STATISTICS);
		System.out.println("- "+Commands.QUIT);
	}

	@Override
	public void doAction(State state, String input) {
		switch (input) {
		case Commands.MAIN_ACTION:{
			state = new MainState();
			break;
		}
		case Commands.QUICK_ACTION:{
			state = new QuickState();
			break;
		}
		default:
			System.out.println("No valid selection");
		}
	}
}
