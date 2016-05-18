/**
 * 
 */
package cg2.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import actions.*;
import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.model.City;
import cg2.observers.Observer;
import cg2.view.View;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Controller implements Observer<Change>{
	
	private final Game game;
	/*
	 * private final Set<MainAction> mainActions;
	 * private final Set<QuickAction> quickActions;
	 */

	/**
	 * constructor of the controller. will initialize the list of actions
	 * with an instance of the specific actions.
	 * @param view a client's view that want to interact with the game
	 * @param game the instance of one specific game 
	 */
	public Controller(View view, Game game) {
		super();
		this.game = game;
		view.registerObserver(this);
		/*
		this.mainActions = new HashSet<MainAction>();
		this.quickActions = new HashSet<QuickAction>();
		this.initMainAction();
		this.initQuickAction();
		*/
	}

	/*
	private void initMainAction(){
		this.mainActions.add(new AcquirePermit());
		this.mainActions.add(new BuildEmporiumByKing());
		this.mainActions.add(new ElectCouncillor());
		this.mainActions.add(new BuildEmproriumByPermit());
	}
	*/
	
	/*
	private void initQuickAction() {
		this.quickActions.add(new EngageAssistant());
		this.quickActions.add(new ChangeFaceUpPermits());
		this.quickActions.add(new ElectCouncillorByAssistant());
		this.quickActions.add(new ExtraMainAction());
	}
	*/
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update()
	 */
	@Override
	public void update() {
		//qua update notificati senza comandi
	}
	
	@Override
	public void update(Change change){
		ActionChange action = (ActionChange)change;
		boolean log=action.getAction().takeAction(game);
		if(!log){
			throw new IllegalStateException("The action is not valid");
		}else{
			//game.notifyObservers(game.getSta);
		}
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.Object)
	 */
	/*
	@Override
	public void update(C change) {
		if(change.getClass().equals(BuildingPermit.class)){
			BuildingPermit p = ((BuildingPermit)change);
			if(this.checkInPlayer(p))
				p.applyBonus(game);
			else
				game.getCurrentPlayer().addBuildingPermit(p);
		}
		else if(change.getClass().equals(City.class))
			((City)change).applyBonus(game);
	}*/

	private boolean checkInPlayer(BuildingPermit p) {
		List<BuildingPermit> buildingPermits = new ArrayList<BuildingPermit>();
		buildingPermits.addAll(
				game.getCurrentPlayer().getBuildingPermits());
		buildingPermits.addAll(
				game.getCurrentPlayer().getUsedBuildingPermits());
		return buildingPermits.contains(p);
	}

	@Override
	public void update(String communication) {
		if(communication.equals("salta")){
			game.decrementQuickActionCounter(); //Messo decrement perche' non sara' mai maggiore di 1 e quindi fare set 0 e' la stessa cosa
			System.out.println(game.getCurrentPlayer().getName()
					+" non puoi più eseguire azioni secondarie per questo turno");
		}
		else if(communication.equals("main action")){
			
		}
		else if(communication.contains("quick action")){
			
		}
	}
}
