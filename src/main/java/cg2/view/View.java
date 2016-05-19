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
import cg2.game.Game;
import cg2.model.BuildingPermit;


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
	
	private enum State {
		QUIT, NONE,	MAIN, QUICK, ACTION, BONUS, PERMITS;
	}
	
	private final int playerID;
	private State state;
	
	
	public View(Game game, int playerID) {
		super();
		game.registerObserver(this);
		peeker = new PeekModel(game);
		this.playerID = playerID;
		this.state = State.NONE;
	}

	/**
	 * retrieve the the input from the client and will start the
	 * selection and the parsing 
	 * @param command the input coming from the client
	 */
	public void input(String command){
		if(command.equals(Commands.QUIT))
			this.state = State.QUIT;
		else if(command.equals(Commands.BACK)){
			if(state.equals(State.MAIN) || state.equals(State.QUICK))
				this.state = State.NONE;
			else if(state.equals(State.ACTION))
				this.state = State.MAIN;
		}
		else if(command.equals(Commands.STATISTICS))
			peeker.getStatsPlayer(this.playerID);
		else if(command.equals(Commands.MAIN_ACTION))
			this.state = State.MAIN;
		else if(command.equals(Commands.QUICK_ACTION))
			this.state = State.QUICK;
		
		/*
		 * is better to include the viewID (playerID) with the action
		 * so the controller can perform the check on the player's turn 
		 */
		//quick actions
		else if(state.equals(State.QUICK)){
			if(command.equals(Commands.ENGAGE_ASSISTANTS))
				this.update(new ActionChange(this.playerID, new EngageAssistant()));
			else if(command.equals(Commands.CHANGE_FACE_UP_PERMITS))
				this.update(new ActionChange(playerID, new ChangeFaceUpPermits()));
			else if(command.equals(Commands.ELECT_COUNCILLOR_BY_ASSISTANT))
				this.displayRequirements(
						ElectCouncillorByAssistant.class.getDeclaredFields());
			else if(command.equals(Commands.EXTRA_MAIN_ACTION))
				this.update(new ActionChange(playerID, new ExtraMainAction()));
		}
		//main actions
		else if(state.equals(State.MAIN)){
			this.state = State.ACTION;
			if(command.equals(Commands.ACQUIRE_PERMIT))
				this.displayRequirements(AcquirePermit.class.getDeclaredFields());
			else if(command.equals(Commands.BUILD_EMPORIUM_BY_KING))
				this.displayRequirements(BuildEmporiumByKing.class.getDeclaredFields());
			else if(command.equals(Commands.ELECT_COUNCILLOR))
				this.displayRequirements(ElectCouncillor.class.getDeclaredFields());
			else if(command.equals(Commands.BUILD_EMPORIUM_BY_PERMIT))
				this.displayRequirements(BuildEmproriumByPermit.class.getDeclaredFields());
		}
		else if(state.equals(State.BONUS));
		//TODO hoe do I save the bonuses coming from the game???
		
	}	

	public void displayState() {
		switch(state){
			case NONE:{
				this.displayNone();
				System.out.println("- statistics");
				System.out.println("- quit");
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
	 * display to the view the first selection's level
	 * that will lead to perform an action
	 */
	private void displayNone(){
		System.out.println("Insert command");
		System.out.println("- main action");
		System.out.println("- quick action");
		System.out.println("- skip quick action");
	}

	/**
	 * display the name of the actions that will be used as command
	 */
	private void displayQuickAction() {
		System.out.println("Insert the action's name to perform:");
		System.out.println("- "+Commands.ENGAGE_ASSISTANTS);
		System.out.println("- "+Commands.CHANGE_FACE_UP_PERMITS);
		System.out.println("- "+Commands.ELECT_COUNCILLOR_BY_ASSISTANT);
		System.out.println("- "+Commands.EXTRA_MAIN_ACTION);		
	}

	/**
	 * display the name of the actions that will be used as command
	 */
	private void displayMainAction() {
		System.out.println("Insert the action's name to perform:");
		System.out.println("- "+Commands.ACQUIRE_PERMIT);
		System.out.println("- "+Commands.BUILD_EMPORIUM_BY_KING);
		System.out.println("- "+Commands.ELECT_COUNCILLOR);
		System.out.println("- "+Commands.BUILD_EMPORIUM_BY_PERMIT);
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
	private void showFreeBuildingPermits() {
		/*
		System.out.println("Inserisci il numero del permesso che vuoi ricevere");
		List<BuildingPermit> shown = new ArrayList<BuildingPermit>();
		List<Council> council = new ArrayList<Council>();
		for(Region r: game.getRegions())
			council.add(r.getCouncil());
		for(Council c: council)
			shown.addAll(c.getPermitsDeck().getFaceUpPermits());
		for(BuildingPermit b: shown)
			System.out.println(shown.indexOf(b)+" - "+b.toString());
		*/	
	}

	public void update(Change change) {
		if(change.getClass().equals(BonusChange.class)){
			this.state = State.BONUS;
			storage = new LocalStorage(change);
			BonusChange c = (BonusChange)change;
			this.displayBonus(c.getBonusList());
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
