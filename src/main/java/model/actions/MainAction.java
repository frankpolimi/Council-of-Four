package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public class MainAction extends Action
{
	@Override
	public boolean checkAction(Game game)
	{
		if(game.getMainActionCounter()>0)
			return true;
		return false;
	}
}