/**
 * 
 */
package cg2.controller;


import java.util.Iterator;
import java.util.Set;

import actions.*;
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

	@Override
	public void update(String communication) {
		switch(communication){
			case "principale":{
				this.showMainAction(game.getMainAction());
				break;
			}
			case "secondaria":{
				this.showQuickAction(game.getQuickAction());
				break;
			}
			case "salta":{
				game.setQuickActionNumber(0);
				System.out.println(game.getPlayers().get(game.getCurrentPlayer()).getName()
						+" non puoi più eseguire azioni secondarie per questo turno");
				break;
			}
			default:
				System.err.println("FATAL ERROR IN COMMUNICATION!");
		}
	}
	
	private void showMainAction(Set<? extends MainAction> action){
		System.out.println("Inserisci il numero dell'azione che vuoi eseguire");
		Iterator<? extends MainAction> x = action.iterator();
		MainAction a;
		for(int i = 1;x.hasNext(); i++){
			a = x.next();
			if(a.getClass().equals(AcquirePermit.class)){
				System.out.println(Integer.toString(i)+" - "+
						((AcquirePermit)a).toString());
			}
			else if(a.getClass().equals(ElectCouncillor.class))
				System.out.println(Integer.toString(i)+" - "+
						((ElectCouncillor)a).toString());
			//TODO complete with others
		}
		this.update("main_action" + action.size());
	}
	
	private void showQuickAction(Set<? extends QuickAction> action){
		Iterator<? extends QuickAction> x = action.iterator();
		QuickAction a;
		while(x.hasNext()){
			a = x.next();
			//TODO complete with others
		}
		this.update("quick_action" + action.size());
	}
}
