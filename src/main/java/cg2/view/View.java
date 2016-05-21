/**
 * 
 */
package cg2.view;

import java.lang.reflect.Field;
import java.util.List;

import actions.*;
import bonus.Bonus;
import cg2.observers.*;
import council.Council;
import cg2.controller.ActionChange;
import cg2.controller.BonusChange;
import cg2.controller.Change;
import cg2.controller.PermitsChange;
import cg2.game.Game;
import cg2.model.BuildingPermit;


/**
 * this class will provide the selections that leads to an opportunity
 * for a creation of an action. this will be submitted to the judgment that will
 * validate if is the player's turn and will prepare the action. the view will 
 * prompt the right parameters to set from the right client 
 * and will transfer to the controller a complete action to execute.
 * @author Francesco Vetrò
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
		switch (state) {
			case QUICK:{
				switch(command){
					case Commands.ENGAGE_ASSISTANTS:{
						this.notifyObservers(new ActionChange(this.playerID, new EngageAssistant()));
						break;
					}
					case Commands.CHANGE_FACE_UP_PERMITS:{
						this.notifyObservers(new ActionChange(playerID, new ChangeFaceUpPermits()));
						break;
					}
					case Commands.ELECT_COUNCILLOR_BY_ASSISTANT:{
						this.displayRequirements(
								ElectCouncillorByAssistant.class.getDeclaredFields());
						break;
					}
					case Commands.EXTRA_MAIN_ACTION:{
						this.notifyObservers(new ActionChange(playerID, new ExtraMainAction()));
						break;
					}
					default:
						System.out.println("Command not existing! Retry");
				}
				break;
			}
			case MAIN:{
				this.state = State.ACTION;
				switch(command){
					case Commands.ACQUIRE_PERMIT:{
						this.displayRequirements(AcquirePermit.class.getDeclaredFields());
						break;
					}
					case Commands.BUILD_EMPORIUM_BY_KING:{
						this.displayRequirements(BuildEmporiumByKing.class.getDeclaredFields());
						break;
					}
					case Commands.ELECT_COUNCILLOR:{
						this.displayRequirements(ElectCouncillor.class.getDeclaredFields());
						break;
					}
					case Commands.BUILD_EMPORIUM_BY_PERMIT:{
						this.displayRequirements(BuildEmproriumByPermit.class.getDeclaredFields());
						break;
					}
					default:
						System.out.println("Command not existing! Retry");
				}
				break;
			}
			case ACTION:{
				break;
			}
			case BONUS:{
				BonusChange change = new BonusChange();
				change.addBonus(storage.retrieveBonus(Integer.parseInt(command)));
				this.notifyObservers(change);
				break;
			}
			case PERMITS:{
				
			}
			default:
				break;
		}
		
		switch(command){
			case Commands.QUIT:{
				break;
			}
			case Commands.BACK:{
				if(state.equals(MainState.class) || state.equals(QuickState.class))
					this.state = State.NONE;
				else if(state.equals(State.ACTION))
					this.state = State.MAIN;
				break;
			}
			case Commands.STATISTICS:{
				peeker.getStatsPlayer(this.playerID);
				break;
			}
			case Commands.MAIN_ACTION:
			case Commands.QUICK_ACTION:{
				state.doAction(state, command);
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

	public void displayState() {
		switch(state){
			case NONE:{
				break;
			}
			case MAIN:{
				this.displayMainAction();
				System.out.println("- statistics");
				System.out.println("- back");
				System.out.println("- quit");
				break;
			}
			case QUICK:{
				this.displayQuickAction();
				System.out.println("- statistics");
				System.out.println("- back");
				System.out.println("- quit");
				break;
			}
			case ACTION:
				break;
			case BONUS:
				break;
			case PERMITS:
				break;
			case QUIT:
			default:
				break;
		}
	}

	/**
	 * display the required parameters
	 * the input should be a string separated by a blank
	 * TODO should display the status of the various parameters in game
	 * e.g. 
	 * required input is a council
	 * this method will display the status of all 4 councils
	 * @param fields 
	 */
	private void displayRequirements(Field[] fields) {
		System.out.println("For the action the required input is: ");
		for(int i = 0; i < fields.length; i++){
			Class<?> field = fields[i].getType();
			if(field.getClass().equals(Council.class))
				peeker.getCouncils();
			else if(field.getClass().equals(BuildingPermit.class))
				System.out.println("- a number: 1 or 2");
		}
	}

	private void displayBonus(List<Bonus> bonusList) {

		System.out.println("Insert the bonus's number you desire to acquire");
		for(Bonus b : bonusList)
			System.out.println(bonusList.indexOf(b)+" - "+b.toString());
	}
	
	/**
	 * display the face up permits for each region
	 * gives the controller the desired building permits to acquire
	 */
	private void displayPermits(List<BuildingPermit> permitList) {
		System.out.println("Insert the permit's number ");	
	}

	public void update(Change change) {
		storage = new LocalStorage(change);
		if(change.getClass().equals(BonusChange.class)){
			this.state = State.BONUS;
			BonusChange c = (BonusChange)change;
			this.displayBonus(c.getBonusList());
		}
		else if(change.getClass().equals(PermitsChange.class)){
			this.state = State.PERMITS;
			PermitsChange p = (PermitsChange)change;
			this.displayPermits(p.getPermits());
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

}
