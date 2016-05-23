/**
 * 
 */
package cg2.view;


import actions.*;
import cg2.observers.*;
import cg2.controller.ActionChange;
import cg2.controller.BonusChange;
import cg2.controller.Change;
import cg2.controller.PermitsChange;
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
	
	
	public View(Game game, int playerID) {
		super();
		game.registerObserver(this);
		peeker = new PeekModel(game);
		this.playerID = playerID;
		this.state = new StartState();
	}

	/**
	 * retrieve the the input from the client and will start the
	 * selection and the parsing 
	 * @param command the input coming from the client
	 */
	public void input(String command){
		/*
		 * is better to include the viewID (playerID) with the action
		 * so the controller can perform the check on the player's turn 
		 */
		if(state.equals(QuickState.class))
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
		else if(state.equals(MainState.class) || state.equals(ActionState.class))
			state.doAction(this, command);
		else if(state.equals(BonusState.class)){
			BonusChange change = new BonusChange();
			change.addBonus(storage.retrieveBonus(Integer.parseInt(command)));
			this.notifyObservers(change);
		}
		else if(state.equals(PermitsState.class)){
			PermitsChange change = new PermitsChange();
			change.addPermit(storage.retrievePermit(Integer.parseInt(command)));
			this.notifyObservers(change);
		}
		else if(state.equals(ActionState.class));
			/*
			 * TODO when Pake's method is online
			 * as a remember:
			 * interface with default methods that parse each field of the action
			 */
		
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
			default: 
				System.out.println("Command not existing! Retry");
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
