/**
 * 
 */
package cg2.view;

import java.lang.reflect.Method;

import actions.*;
import cg2.observers.*;
import council.Council;
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
public class View extends Observable implements Observer {
	
	//class to read from the Model only some information
	private final PeekModel peeker;
	private final int playerID;
	private State state;
	private enum State{
		QUIT, NONE, MAIN, QUICK, ACTION, BONUS, PERMITS;
	}
	
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
		if(state.equals(State.QUICK))
			if(command.equals(Commands.ENGAGE_ASSISTANTS))
				this.update(new Message(this.playerID, new EngageAssistant()));
			else if(command.equals(Commands.CHANGE_FACE_UP_PERMITS))
				this.update(new Message(playerID, new ChangeFaceUpPermits()));
			else if(command.equals(Commands.ELECT_COUNCILLOR_BY_ASSISTANT))
				this.update(new Message(playerID, new ElectCouncillorByAssistant()));
			else if(command.equals(Commands.EXTRA_MAIN_ACTION))
				this.update(new Message(playerID, new ExtraMainAction()));
		if(state.equals(State.MAIN))
			this.state = State.ACTION;
			if(command.equals(Commands.ACQUIRE_PERMIT))
				this.displayRequirements(AcquirePermit.class.getMethods()[0]);
			else if(command.equals(Commands.BUILD_EMPORIUM_BY_KING));
			else if(command.equals(Commands.ELECT_COUNCILLOR));
			else if(command.equals(Commands.BUILD_EMPORIUM_BY_PERMIT));
				
				
	}	

	public void displayState() {
		switch(state){
			case NONE:{
				this.displayNone();
				System.out.println("- quit");
				break;
			}
			case MAIN:{
				this.displayMainAction();
				System.out.println("- back");
				System.out.println("- quit");
				break;
			}
			case QUICK:{
				this.displayQuickAction();
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
	 */
	private void displayRequirements(Method method) {
		System.out.println("For the action the required input is: ");
		for(Class<?> param : method.getParameterTypes()){
			if(param.getClass().equals(Council.class))
				System.out.println("- region");
			if(param.getClass().equals(BuildingPermit.class))
				System.out.println("- a number: 1 or 2");
		}
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update()
	 */
	@Override
	public void update() {
		//not used at the moment
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.Object)
	 */
	@Override
	public <C> void update(C change) {
	}
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.String)
	 */
	/**
	 * communication between the view and the controller
	 * with different cases
	 * @param the string with a communication
	 */
	@Override
	public void update(String communication) {
		if(communication.contains("Bonus"))
			this.selectBonus(communication);
		else
			System.err.println("FATAL ERROR IN COMMUNICATION!");
	}
	
	/**
	 * displays the different bonuses that require an input from the user
	 * @param type of the bonus
	 */
	private void selectBonus(String type) {
		if(type.equals("ReuseTileBonus"))
			this.showBonusPermits();
		else if(type.equals("CityBonus"))
			this.showBonusCities();
		else if(type.equals("FreeBuildingLicenseBonus"))
			this.showFreeBuildingPermits();
	}

	

	/**
	 * display the bonus on the permits owned by the current player
	 * gives the controller the desired permit to reuse the bonus
	 */
	private void showBonusPermits() {
		/*
		System.out.println("Inserisci il numero del bonus che vuoi ricevere");
		List<BuildingPermit> buildingPermits = new ArrayList<BuildingPermit>();
		buildingPermits.addAll(
				game.getCurrentPlayer().getStatus().getBuildingPermits());
		buildingPermits.addAll(
				game.getCurrentPlayer().getStatus().getUsedBuildingPermits());
		for(BuildingPermit b: buildingPermits)
			System.out.println(buildingPermits.indexOf(b)+" - "+b.displayBonus());
		*/
	}
	
	/**
	 * display the bonus on the cities in which is built an emporium
	 * owned by the current player
	 * gives the controller the desired city 
	 */
	private void showBonusCities() {
		/*
		System.out.println("Inserisci il numero del bonus che vuoi ricevere");
		Iterator<Emporium> builtOn = 
				game.getCurrentPlayer().getEmporium().iterator();
		List<City> cityWithE = new ArrayList<City>();
		while(builtOn.hasNext())
			cityWithE.add(builtOn.next().getCity());
		for(City c: cityWithE)
			System.out.println(cityWithE.indexOf(c)+" - "+c.displayBonus());
		*/
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
}
