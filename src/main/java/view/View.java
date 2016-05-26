/**
 * 
 */
package view;


import controller.ActionChange;
import controller.BonusChange;
import controller.Change;
import controller.ModelChange;
import controller.PermitsChange;
import controller.StateChange;
import model.actions.*;
import model.game.Game;
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

	@Override
	public void update(String communication) {
		// TODO Auto-generated method stub
		
	}
	
}
