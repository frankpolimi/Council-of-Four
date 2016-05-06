/**
 * 
 */
package cg2.controller;

import java.util.ArrayList;
import java.util.List;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.observers.Observer;
import cg2.player.Player;
import cg2.view.View;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Controller implements Observer {
	
	private final Game game;

	public Controller(View view, Game game) {
		super();
		this.game = game;
		game.registerObserver(this);
		view.registerObserver(this);
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update()
	 */
	@Override
	public void update() {
		//qua update notificati senza comandi
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.Object)
	 */
	@Override
	public <C> void update(C change) {
		//qua invece update arrivati dalla view e che portano con s� i comandi per il gioco, da smistare
		//con relativi input e Strategy
	}

	@Override
	public void update(String communication) {
		switch(communication){
			case "principale":{
				//this.showAction(game.getMainAction());
				this.update("main_action");
				break;
			}
			case "secondaria":{
				//this.showAction(game.getQuickAction());
				this.update("quick_action");
				break;
			}
			case "salta":{
				game.setQuickActionNumber(0);
				System.out.println(game.getPlayers().get(game.getCurrentPlayer()).getName()
						+" non puoi pi� eseguire azioni secondarie per questo turno");
				break;
			}
			case "ReuseTileBonus":{
				this.pickPermitsBonus();
				break;
			}
			default:
				System.err.println("FATAL ERROR IN COMMUNICATION!");
		}
	}

	private void pickPermitsBonus() {
		List<BuildingPermit> total = new ArrayList<BuildingPermit>();
		total.addAll(
				(game.getPlayers().get(game.getCurrentPlayer()).getStatus().getBuildingPermits()));
		total.addAll(
				(game.getPlayers().get(game.getCurrentPlayer()).getStatus().getUsedBuildingPermits()));
		System.out.println("Inserisci il numero del bonus che vuoi ottenere");
		for(BuildingPermit x : total)
			System.out.println(total.indexOf(x) + " - " + x.displayBonus());
	}
	
	/*
	private void showAction(Set<Action> action){
		
	}
	*/
}
