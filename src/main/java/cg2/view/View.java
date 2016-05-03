/**
 * 
 */
package cg2.view;

import java.util.Scanner;

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
	}
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.String)
	 */
	@Override
	public void update(String communication) {
		String selection;
		if(communication.equals("action phase")){
			selection = this.selectAction();
			this.input(selection);
		}
		if(communication.equals("main action")){
			selection = this.selectMainAction();
			this.input(selection);
		}
		
	}

	/**
	 * selection of the two classes of action a player can perform
	 */
	private String selectAction() {
		Scanner in = new Scanner(System.in);
		boolean condition;
		do{
			condition = false;
			System.out.println("Scegli che tipo di mossa vuoi eseguire "
					+ "(scrivi il comando)\n"
					+ "1 - Principale (principale)\n"
					+ "2 - Secondaria (secondaria)\n"
					+ "3 - Salta mossa secondaria (salta)\n");

			String selection = in.nextLine();
			switch(selection){
				case "principale":
				/*{
					in.close();
					return selection;
				}*/
				case "secondaria":
				/*{
					in.close();
					return selection;
				}*/
				case "salta":{
					
					/*
					 * game.setQuickActionNumber(0);
					 * TODO this method must be moved to the controller 
					 * for the modification to the game
					 */
					
					break;
				}
				default:{
					System.err.println("Errore nell'inserimento. Riprova");
					condition = true;
				}
			}
		}while(condition);
		
		in.close();
		
		//as a default move the player will always start with a main action
		return "principale";
	}

	private String selectQuickAction() {
		// TODO Auto-generated method stub
		return null;
	}

	private String selectMainAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
