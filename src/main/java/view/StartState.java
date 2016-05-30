/**
 * 
 */
package view;


/**
 * @author Francesco Vetr�
 *
 */
public class StartState implements State {
	
	
	/**
	 * display to the view the first selection's level
	 * that will lead to perform an action
	 */
	@Override
	public void display() {
		System.out.println("Select the action type to perform");
		System.out.println("1. main action");
		System.out.println("2. quick action");
		System.out.println("3. pass to the next player");
		System.out.println("4. quit");
	}

}
