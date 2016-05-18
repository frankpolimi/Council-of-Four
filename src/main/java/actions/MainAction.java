package actions;

import cg2.game.Game;

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
		System.out.println("The current player has already used all the avaiable main actions this turn");
		return false;
	}
}
