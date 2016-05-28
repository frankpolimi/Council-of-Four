package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public class QuickAction extends Action 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 778426757352151461L;

	@Override
	public boolean checkAction(Game game)
	{
		if(game.getQuickActionCounter()>0)
			return true;
		return false;
	}
}
