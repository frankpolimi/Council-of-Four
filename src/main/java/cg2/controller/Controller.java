/**
 * 
 */
package cg2.controller;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.observers.Observer;
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
		if(change.getClass().equals(BuildingPermit.class))
			((BuildingPermit)change).applyBonus(
					game.getPlayers().get(game.getCurrentPlayer()));
	}

	@Override
	public void update(String communication) {
		if(communication.equals("salta")){
			game.decrementQuickAction(); //Messo decrement perche' non sara' mai maggiore di 1 e quindi fare set 0 e' la stessa cosa
			System.out.println(game.getPlayers().get(game.getCurrentPlayer()).getName()
					+" non puoi più eseguire azioni secondarie per questo turno");
		}
		else if(communication.contains("main_action"));
			/*
			 * TODO 
			 * skim through the MainAction
			 * pick right one (iterator and for combined)
			 * execute the action (how the I/O)
			 */
		else if(communication.contains("quick_action"));
			/*
			 * TODO 
			 * skim through the MainAction
			 * pick right one (iterator and for combined)
			 * execute the action (how the I/O)
			 */
	}
}
