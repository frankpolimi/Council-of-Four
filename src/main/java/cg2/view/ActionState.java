/**
 * 
 */
package cg2.view;

import java.lang.reflect.Field;
/**
 * @author Francesco Vetrò
 *
 */
public class ActionState implements State {
	
	/**
	 * the fields of the action
	 */
	private Field[] fields;
	private final PeekModel peeker;
	
	public ActionState(Field[] fields, PeekModel peeker) {
		this.fields = fields;
		this.peeker = peeker;
	}

	/**
	 * method to parse the string and put them in the action field
	 */
	public void doAction(View view, String input) {
		view.setState(new StartState());
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	@Override
	public void display() {
		

	}
	
	/**
	 * insert method private void string tokenizer &
	 * use the string input from the method doAction
	 */

}
