/**
 * 
 */
package cg2.view;

/**
 * @author Francesco Vetrò
 *
 */
public interface State {

	public default void doAction(View view, String input){
		//nothing to do
	}
	
	public void display();
	
	
}
