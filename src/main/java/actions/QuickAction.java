package actions;

import cg2.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public class QuickAction extends Action 
{
	@Override
	public boolean checkAction(Game game)
	{
		if(game.getQuickActionCounter()>0)
			return true;
		return false;
	}
}
