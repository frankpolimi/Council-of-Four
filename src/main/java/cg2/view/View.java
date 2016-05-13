/**
 * 
 */
package cg2.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	private final int playerID;
	
	public View(Game game, int playerID) {
		super();
		game.registerObserver(this);
		this.playerID = playerID;
	}

	/**
	 * gives the input to the controller
	 * @param command the input coming from the client
	 */
	public void input(String command){
		if(command.equals("main action"))
			this.displayMainAction();
		else if(command.equals("quick action"))
			this.displayQuickAction();
	}	
	
	private void displayQuickAction() {
		int index = 0;
		System.out.println(++index+" - "+EngageAssistant.class.getSimpleName());
		System.out.println(++index+" - "+ChangeFaceUpPermits.class.getSimpleName());
		System.out.println(++index+" - "+ElectCouncillorByAssistant.class.getSimpleName());
		System.out.println(++index+" - "+ExtraMainAction.class.getSimpleName());		
	}

	private void displayMainAction() {
		int index = 0;
		System.out.println(++index+" - "+AcquirePermit.class.getSimpleName());
		System.out.println(++index+" - "+BuildEmporiumByKing.class.getSimpleName());
		System.out.println(++index+" - "+ElectCouncillor.class.getSimpleName());
		System.out.println(++index+" - "+BuildEmproriumByPermit.class.getSimpleName());
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
