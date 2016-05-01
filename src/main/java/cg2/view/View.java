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
		if(change.getClass() == String.class)
			if(change.equals("action phase"))
				this.selectAction();
	}

	/**
	 * selection of the two classes of action a player can perform
	 */
	private void selectAction() {
		Scanner in = new Scanner(System.in);
		boolean condition;
		do{
			condition = false;
			System.out.println("Scegli che tipo di mossa vuoi eseguire "
					+ "(scrivi il comando)\n"
					+ "1 - Principale (principale)"
					+ "2 - Secondaria (secondaria)"
					+ "3 - Salta mossa secondaria (salta)");

			String selection = in.nextLine();
			switch(selection){
				case "principale":{
					this.selectMainAction();
					in.close();
					break;
				}
				case "secondaria":{
					this.selectQuickAction();
					in.close();
					break;
				}
				case "salta":{
					game.setQuickActionNumber(0);
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

	private void selectQuickAction() {
		// TODO Auto-generated method stub
		
	}

	private void selectMainAction() {
		// TODO Auto-generated method stub
		
	}

}
