package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public abstract class MainAction extends Action
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2517451926454347130L;

	/**
	 * method that checks if the player can
	 * perform the main action desired
	 * @return true if the player can perform the action
	 * 			false otherwise
	 */
	@Override
	public boolean checkAction(Game game)
	{
		if(game.getMainActionCounter()>0)
			return true;
		return false;
	}
}
