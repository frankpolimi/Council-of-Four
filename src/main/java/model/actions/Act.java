package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 * 
 * interface used for a generic action
 */
public interface Act 
{
	/**
	 * method that contains the logic of the action
	 * and the operations that modifies the game
	 * @param game the game which the action is applied
	 */
	public boolean takeAction(Game game);
	
	/**
	 * method that contains the logic of the controls that
	 * must be satisfied in order to perform the action
	 * @param game the game which the status will
	 * 				be controlled prior the execution
	 * 				of the action
	 */
	public boolean checkAction(Game game);
}
