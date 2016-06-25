package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public abstract class QuickAction extends Action 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 778426757352151461L;

	/**
	 * method that checks if the player can
	 * perform the quick action desired
	 * @return true if the player can perform the action
	 * 			false otherwise
	 */
	@Override
	public boolean checkAction(Game game)
	{
		if(game.getQuickActionCounter()>0)
			return true;
		return false;
	}
}
