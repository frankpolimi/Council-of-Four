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
 * this class will provide the selections that leads to an opportunity
 * for a creation of an action. this will be submitted to the judgment that will
 * validate if is the player's turn and will prepare the action. the view will 
 * prompt the right parameters to set from the right client 
 * and will transfer to the controller a complete action to execute.
 * @author Francesco Vetrò
 */
public class View extends Observable implements Observer {
	
	private final Game game;
	/*
	 * possible client a which is connected the view
	 * 1:1 mapping client-view
	 */
	
	public View(Game game) {
		super();
		this.game = game;
		game.registerObserver(this);
	}

	/**
	 * gives the input to the controller
	 * @param command the input coming from the client
	 */
	public void input(String command){
		if(command.equals("main action"))
			this.showMainAction();
		else if(command.equals("quick action"))
			this.showQuickAction();
			
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
		int selection = 1;
		
		boolean condition;
		do{
			condition = false;
			
			System.out.println("Scegli che tipo di mossa vuoi eseguire");
			if(game.getMainActionCounter() != 0)
				System.out.println("1 - Principale");
			if(game.getQuickActionCounter() != 0)
				System.out.println("2 - Secondaria");
			if(game.getMainActionCounter() == 0)
				System.out.println("3 - Salta mossa secondaria");
			
			selection = this.numberSelection(3);

			switch(selection){
				case 1:{
					in.close();
					this.selectAction("main_action");
					break;
				}
				case 2:{
					in.close();
					this.selectAction("quick_action");
					break;
				}
				case 3:{
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
		//int selection;
		//if(type.equals("main_action"))
			//selection = this.numberSelection(this.showMainAction());
		//else
			//selection = this.numberSelection(this.showQuickAction());
		
		//TODO complete with send to the controller
	}
	
	/**
	 * display the main actions that can be performed
	 * @return the number of main actions
	 */
	private void showMainAction(){
		System.out.println("Inserisci il numero dell'azione che vuoi eseguire");
		Iterator<? extends MainAction> x = game.getMainAction().iterator();
		for(int i = 1;x.hasNext(); i++){
			System.out.println(i+" - "+x.next().toString());
		}
		//return game.getMainAction().size();
	}
	
	/**
	 * display the quick actions that can be performed
	 * @return the number of quick actions
	 */
	private void showQuickAction(){
		System.out.println("Inserisci il numero dell'azione che vuoi eseguire");
		Iterator<? extends QuickAction> x = game.getQuickAction().iterator();
		for(int i = 1;x.hasNext(); i++)
				System.out.println(i+" - "+x.toString());
		//return l;
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
		System.out.println("Inserisci il numero del bonus che vuoi ricevere");
		List<BuildingPermit> buildingPermits = new ArrayList<BuildingPermit>();
		buildingPermits.addAll(
				game.getCurrentPlayer().getStatus().getBuildingPermits());
		buildingPermits.addAll(
				game.getCurrentPlayer().getStatus().getUsedBuildingPermits());
		for(BuildingPermit b: buildingPermits)
			System.out.println(buildingPermits.indexOf(b)+" - "+
					((BuildingPermit) b).displayBonus());
		//int x = this.numberSelection(buildingPermits.size());
		//this.input(buildingPermits.get(x));
	}
	
	/**
	 * display the bonus on the cities in which is built an emporium
	 * owned by the current player
	 * gives the controller the desired city 
	 */
	private void showBonusCities() {
		System.out.println("Inserisci il numero del bonus che vuoi ricevere");
		Iterator<Emporium> builtOn = 
				game.getCurrentPlayer().getEmporium().iterator();
		List<City> cityWithE = new ArrayList<City>();
		while(builtOn.hasNext())
			cityWithE.add(builtOn.next().getCity());
		for(City c: cityWithE)
			System.out.println(cityWithE.indexOf(c)+" - "+c.displayBonus());
		//int x = this.numberSelection(cityWithE.size());
		//this.input(cityWithE.get(x));
	}
	
	/**
	 * display the face up permits for each region
	 * gives the controller the desired building permits to acquire
	 */
	private void showFreeBuildingPermits() {
		System.out.println("Inserisci il numero del permesso che vuoi ricevere");
		List<BuildingPermit> shown = new ArrayList<BuildingPermit>();
		List<Council> council = new ArrayList<Council>();
		for(Region r: game.getRegions())
			council.add(r.getCouncil());
		for(Council c: council)
			shown.addAll(c.getPermitsDeck().getFaceUpPermits());
		for(BuildingPermit b: shown)
			System.out.println(shown.indexOf(b)+" - "+b.toString());
		//int x = this.numberSelection(shown.size());
		//this.input(x);
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
