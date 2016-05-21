/**
 * 
 */
package cg2.view;

/**
 * @author Francesco Vetrò
 *
 */
public interface State {

	public void doAction(State state, String input);
	
	public void display();
	
	
}
