/**
 * 
 */
package cg2.view;

/**
 * @author Francesco Vetr�
 *
 */
public interface State {

	public void doAction(State state, String input);
	
	public void display();
	
	
}
