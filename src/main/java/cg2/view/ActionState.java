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
	
	private Field[] fields;
	
	public ActionState(Field[] fields) {
		this.fields = fields;
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#doAction(cg2.view.State, java.lang.String)
	 */
	@Override
	public void doAction(State state, String input) {
		state = new StartState();
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	@Override
	public void display() {
		

	}

}
