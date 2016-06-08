/**
 * 
 */
package view;


/**
 * @author Francesco Vetr�
 *
 */
public class QuickState implements State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6776522050204079964L;

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
}	
