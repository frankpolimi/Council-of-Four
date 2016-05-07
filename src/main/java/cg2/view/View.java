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
	@Override
	public void update(String communication) {
		String selection;
		if(communication.contains("action_phase")){
				selection = this.selectAction();
				this.input(selection);
		}
		else if(communication.contains("main_action")){
				selection = this.selectMainAction(
						communication.substring("main_action".length()));
				this.input(selection);
		}
		else if(communication.contains("quick_action")){
				selection = this.selectQuickAction(
						communication.substring("main_action".length()));
				this.input(selection);
		}
		/*"ReuseTileBonus"{
			selection = Integer.toString(this.numberIn());
			this.input(selection);
		}*/
		else
			System.err.println("FATAL ERROR IN COMMUNICATION!");
	}

	/*
	private int numberIn() {
		Scanner in = new Scanner(System.in);
		in.nextInt();
	}
*/

	/**
	 * selection of the two classes of action a player can perform
	 */
	private String selectAction() {
		Scanner in = new Scanner(System.in);
		boolean condition;
		do{
			condition = false;
			System.out.println("Scegli che tipo di mossa vuoi eseguire "
					+ "(comando)\n"
					+ "1 - Principale (principale)\n"
					+ "2 - Secondaria (secondaria)\n");
			if(game.getMainActionNumber() == 0)
				System.out.println("3 - Salta mossa secondaria (salta)\n");

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

	private String selectQuickAction(String numberOfActions) {
		int ins;
		Scanner in = new Scanner(System.in);
		do{
			ins = in.nextInt();
		}while(ins > Integer.parseInt(numberOfActions));
		return "quick_action"+Integer.toString(ins);
	}

	private String selectMainAction(String numberOfActions) {
		int ins;
		Scanner in = new Scanner(System.in);
		do{
			ins = in.nextInt();
		}while(ins > Integer.parseInt(numberOfActions));
		return "main_action"+Integer.toString(ins);
	}

}
