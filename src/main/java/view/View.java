/**
 * 
 */
package view;


import controller.Change;
import model.observers.*;

/**
 * @author Francesco Vetro'
 * 
 * this class is the representation of a generic view. 
 * the view is supposed to be able to read changes
 */
public abstract class View extends Observable<Request> implements Observer<Change> {

	/**
	 * a generic update
	 */
	@Override
	public void update() {
	}

	/**
	 * update with a change
	 * @param change the change that will be presented 
	 * to the receiver of the change
	 */
	@Override
	public void update(Change change) {
	}

	/**
	 * the id of the player that the view refers to
	 * @param serialID
	 */
	public void setID(int serialID){
		//nothig to do, must be overridden
	}
	
	public abstract String getName();

}
