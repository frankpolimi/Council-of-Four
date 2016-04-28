/**
 * 
 */
package cg2.view;

import cg2.game.Game;
import cg2.observers.Observable;
import cg2.observers.Observer;

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

	public void input(String command){
		this.notifyObservers(command);
	}
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update()
	 */
	@Override
	public void update() {
		//qua le notifiche che arriveranno dalla view senza comandi
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.Object)
	 */
	@Override
	public <C> void update(C change) {
		//qua le notifiche che arriveranno dalla view con comandi specifici
	}

}
