/**
 * 
 */
package cg2.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import actions.*;
import cg2.observers.*;
import council.Council;
import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.model.City;
import cg2.model.Emporium;
import topology.Region;


/**
 * @author Emanuele
 *
 */
public class View extends Observable implements Observer {
	
	private final Game game;
	
	public View(Game game) {
		super();
		this.game = game;
		game.registerObserver(this);
	}

	/**
	 * gives the input to the controller
	 * @param command
	 */
	public void input(String command){
		this.notifyObservers(command);
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
		if(communication.equals("action_phase"))
			this.selectActionType();
		else if(communication.contains("Bonus"))
			this.selectBonus(communication);
		else
			System.err.println("FATAL ERROR IN COMMUNICATION!");
	}

	/**
	 * selection of the two classes of action a player can perform
	 * @param string 
	 */
	private void selectActionType() {
		Scanner in = new Scanner(System.in);
		
		//as a default move the player will always start with a main action
		String selection = "principale";
		
		boolean condition;
		do{
			condition = false;
			
			System.out.println("Scegli che tipo di mossa vuoi eseguire (comando)");
			if(game.getMainActionCounter() != 0)
				System.out.println("1 - Principale (principale)\n");
			if(game.getQuickActionCounter() != 0)
				System.out.println("2 - Secondaria (secondaria)\n");
			if(game.getMainActionCounter() == 0)
				System.out.println("3 - Salta mossa secondaria (salta)\n");

			selection = in.nextLine();
			switch(selection){
				case "principale":{
					in.close();
					this.selectAction("main_action");
					break;
				}
				case "secondaria":{
					in.close();
					this.selectAction("quick_action");
					break;
				}
				case "salta":{
					in.close();
					break;
				}
				default:{
					System.err.println("Errore nell'inserimento. Riprova");
					condition = true;
				}
			}
		}while(condition);
	}

	/**
	 * select the main action by a number
	 * (1 - firstAction
	 *  2 - secondAction
	 *  and so on)
	 * @param numberOfActions
	 * @return the message main_action and the number of the action to perform
	 */
	private void selectAction(String type) {
		int selection;
		if(type.equals("main_action"))
			selection = this.numberSelection(this.showMainAction());
		else
			selection = this.numberSelection(this.showQuickAction());
		
		//TODO complete with send to the controller
	}
	
	/**
	 * display the main actions that can be performed
	 * @return the number of main actions
	 */
	private int showMainAction(){
		System.out.println("Inserisci il numero dell'azione che vuoi eseguire");
		Iterator<? extends MainAction> x = game.getMainAction().iterator();
		MainAction a;
		for(int i = 1;x.hasNext(); i++){
			a = x.next();
			if(a.getClass().equals(AcquirePermit.class)){
				System.out.println(i+" - "+
						((AcquirePermit)a).toString());
			}
			else if(a.getClass().equals(ElectCouncillor.class))
				System.out.println(i+" - "+
						((ElectCouncillor)a).toString());
			else if(a.getClass().equals(BuildEmproriumByPermit.class))
				System.out.println(i+" - "+
						((BuildEmproriumByPermit)a).toString());
			//TODO complete
		}
		return game.getMainAction().size();
	}
	
	/**
	 * display the quick actions that can be performed
	 * @return the number of quick actions
	 */
	private int showQuickAction(){
		System.out.println("Inserisci il numero dell'azione che vuoi eseguire");
		Iterator<? extends QuickAction> x = game.getQuickAction().iterator();
		int l = game.getQuickAction().size();
		QuickAction a;
		for(int i = 1;x.hasNext(); i++){
			a = x.next();
			if(a.getClass().equals(ElectCouncillorByAssistant.class))
				System.out.println(i+" - "+
						((ElectCouncillorByAssistant)a).toString());
			else if(a.getClass().equals(EngageAssistant.class))
				System.out.println(i+" - "+
						((EngageAssistant)a).toString());
			else if(a.getClass().equals(ChangeFaceUpPermits.class))
				System.out.println(i+" - "+
						((ChangeFaceUpPermits)a).toString());
			else if(a.getClass().equals(ExtraMainAction.class))
				System.out.println(i+" - "+
						((ExtraMainAction)a).toString());
		}
		return l;
	}
	
	/**
	 * displays 
	 * @param type
	 */
	private void selectBonus(String type) {
		int selection;
		int length = 0;
		if(type.equals("ReuseTileBonus"))
			length = this.showBonusPermits();
		else if(type.equals("CityBonus"))
			length = this.showBonusCities();
		else if(type.equals("FreeBuildingLicenseBonus"))
			length = this.showFreeBuildingPermits();
		selection = this.numberSelection(length);
		//TODO complete with the send to the controller
	}

	

	/**
	 * display the bonus on the permits owned by the current player
	 * @return the number of used and unused permits 
	 */
	private int showBonusPermits() {
		System.out.println("Inserisci il numero del bonus che vuoi ricevere");
		List<BuildingPermit> buildingPermits = new ArrayList<BuildingPermit>();
		buildingPermits.addAll(
				game.getPlayers().get(game.getCurrentPlayer()).getStatus().getBuildingPermits());
		buildingPermits.addAll(
				game.getPlayers().get(game.getCurrentPlayer()).getStatus().getUsedBuildingPermits());
		for(BuildingPermit b: buildingPermits)
			System.out.println(buildingPermits.indexOf(b)+" - "+
					((BuildingPermit) b).displayBonus());
		return buildingPermits.size();
	}
	
	/**
	 * display the bonus on the cities in which is built an emporium
	 * owned by the current player
	 * @return the number of cities as specified above 
	 */
	private int showBonusCities() {
		System.out.println("Inserisci il numero del bonus che vuoi ricevere");
		Iterator<Emporium> builtOn = 
				game.getPlayers().get(game.getCurrentPlayer()).getEmporium().iterator();
		List<City> cityWithE = new ArrayList<City>();
		while(builtOn.hasNext())
			cityWithE.add(builtOn.next().getCity());
		for(City c: cityWithE)
			System.out.println(cityWithE.indexOf(c)+" - "+c.displayBonus());
		return cityWithE.size();
	}
	
	/**
	 * display the face up permits for each region
	 * @return the number of face up permits
	 */
	private int showFreeBuildingPermits() {
		System.out.println("Inserisci il numero del permesso che vuoi ricevere");
		List<BuildingPermit> shown = new ArrayList<BuildingPermit>();
		List<Council> council = new ArrayList<Council>();
		for(Region r: game.getRegions())
			council.add(r.getCouncil());
		for(Council c: council)
			shown.addAll(c.getPermitsDeck().getFaceUpPermits());
		for(BuildingPermit b: shown)
			System.out.println(shown.indexOf(b)+" - "+b.toString());
		return shown.size();
	}

	/**
	 * perform the input and check if the number is between 0 (zero)
	 * and the parameter specified by the methods above
	 * @param maxAvailable the maximum number available in the selection menu
	 * @return the desired entry of the menu as an int
	 */
	private int numberSelection(int maxAvailable){
		Scanner in = new Scanner(System.in);
		int ins;
		do{
			ins = in.nextInt();
		}while(ins > maxAvailable || ins < 0);
		in.close();
		return ins;
	}
}
