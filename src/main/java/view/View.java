/**
 * 
 */
package view;


import controller.Change;
import model.observers.*;

/**
 * this class will provide the selections that leads to an opportunity
 * for a creation of an action. this will be submitted to the judgment that will
 * validate if is the player's turn and will prepare the action. the view will 
 * prompt the right parameters to set from the right client 
 * and will transfer to the controller a complete action to execute.
 * @author Francesco Vetr�
 */
public class View extends Observable<Request> implements Observer<Change> {

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Change change) {
		// TODO Auto-generated method stub
		
	}

	public void setID(int serialID){
		//nothig to do, must be overridden
	}


}
