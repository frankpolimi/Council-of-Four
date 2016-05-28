/**
 * 
 */
package view;

import java.util.Scanner;

import model.game.Game;
/**
 * @author Francesco Vetr�
 *
 */
public class ActionState implements State {
	
	private final int type;
	private final int select;
	private Game game;
	private Scanner scanner = new Scanner(System.in);
	
	public ActionState(int type, int sel, Game game) {
		this.type = type;
		this.select = sel;
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	/**
	 * 
	 */
	@Override
	public void display() {
		

		//TODO send to server
		//this.notifyObservers(new ActionChange(1, action));

	}
}
