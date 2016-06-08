/**
 * 
 */
package view;


/**
 * @author Francesco Vetr�
 *
 */
public class MainState implements State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8768897841650922464L;

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

}
