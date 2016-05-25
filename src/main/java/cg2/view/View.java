/**
 * 
 */
package cg2.view;


import actions.*;
import cg2.observers.*;
import cg2.controller.ActionChange;
import cg2.controller.BonusChange;
import cg2.controller.Change;
import cg2.controller.ModelChange;
import cg2.controller.PermitsChange;
import cg2.controller.StateChange;
import cg2.game.Game;

/**
 * this class will provide the selections that leads to an opportunity
 * for a creation of an action. this will be submitted to the judgment that will
 * validate if is the player's turn and will prepare the action. the view will 
 * prompt the right parameters to set from the right client 
 * and will transfer to the controller a complete action to execute.
 * @author Francesco Vetr�
 */
public class View extends Observable<Change> implements Observer<Change> {
	
	//class to read from the Model only some information
	private final PeekModel peeker;
	private LocalStorage storage;
	/*
	private enum State {
		QUIT, MAIN, QUICK, ACTION, BONUS, PERMITS;
	}
	*/
	private final int playerID;
	private State state;
	private Class<?> action;
	
	public View(Game game, int playerID) {
		super();
		game.registerObserver(this);
		peeker = new PeekModel(game);
		this.playerID = playerID;
		this.state = new StartState();
	}
	
	public void setActionClass(Class<?> action){
		this.action = action;
	}
	/**
	 * retrieve the the input from the client and will start the
	 * selection and the parsing 
	 * @param command the input coming from the client
	 */
	public void input(String command){
		
		switch(command){
			case Commands.QUIT:{
				break;
			}
			case Commands.BACK:
			case Commands.MAIN_ACTION:
			case Commands.QUICK_ACTION:{
				state.doAction(this, command);
				break;
			}
			case Commands.STATISTICS:{
				System.out.println(peeker.getStatsPlayer(this.playerID).toString());
				break;
			}
			case Commands.SKIP:{
				this.update(command);
				break;
			}
		}
		
		/*
		 * is better to include the viewID (playerID) with the action
		 * so the controller can perform the check on the player's turn 
		 */
		if(state.getClass().equals(QuickState.class))
			switch(command){
			case Commands.ENGAGE_ASSISTANTS:{
				this.notifyObservers(new ActionChange(this.playerID, new EngageAssistant()));
				this.state = new StartState();
				break;
			}
			case Commands.EXTRA_MAIN_ACTION:{
				this.notifyObservers(new ActionChange(playerID, new ExtraMainAction()));
				this.state = new StartState();
				break;
			}
			default:
				state.doAction(this, command);
			}
		else if(state.getClass().equals(MainState.class))
			state.doAction(this, command);
		else if(state.getClass().equals(BonusState.class)){
			BonusChange change = new BonusChange();
			int sel = Integer.parseInt(command);
			if(sel <= storage.getBonusLenght() && sel > 0){
				change.addBonus(storage.retrieveBonus(sel));
				this.notifyObservers(change);
				state.doAction(this, command);
			}
			else
				System.out.println("No valid selection");
		}
		else if(state.getClass().equals(PermitsState.class)){
			PermitsChange change = new PermitsChange();
			int sel = Integer.parseInt(command);
			if(sel <= storage.getBonusLenght() && sel > 0){
				change.addPermit(storage.retrievePermit(sel));
				state.doAction(this, command);
				this.notifyObservers(change);
			}
			else
				System.out.println("No valid selection");
		}
		else if(state.getClass().equals(ActionState.class)){
			/*
			 * TODO when Pake's method is online
			 * as a remember:
			 * interface with default methods that parse each field of the action
			 */
		}else if(state.getClass().equals(MarketState.class)){
			switch(command){
			case Commands.ADD_PRODUCT:
				
			}
		}
	}	

	public void update(Change change) {
		storage = new LocalStorage(change);
		if(change.getClass().equals(BonusChange.class)){
			BonusChange c = (BonusChange)change;
			this.state = new BonusState(c.getBonusList());
		}
		else if(change.getClass().equals(PermitsChange.class)){
			PermitsChange p = (PermitsChange)change;
			this.state = new PermitsState(p.getPermits());
		}else if(change instanceof StateChange){
			this.state=((StateChange) change).getStateChanged();
			
		}else if(change instanceof ModelChange){
			System.out.println(((ModelChange)change).toString());
		}
	}

	@Override
	public void update() {
		//not used
	}

	@Override
	public void update(String communication) {
		//not used
		
	}

	public State getState() {
		return state;
	}
	
	public void setState(State state){
		this.state = state;
	}

	public PeekModel getPeeker() {
		return peeker;
	}

	public int getPlayerID() {
		return playerID;
	}

}
