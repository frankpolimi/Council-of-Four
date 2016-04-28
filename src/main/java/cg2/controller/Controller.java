/**
 * 
 */
package cg2.controller;

import cg2.game.Game;
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
		//qua invece update arrivati dalla view e che portano con sè i comandi per il gioco, da smistare
		//con relativi input e Strategy
	}

}
