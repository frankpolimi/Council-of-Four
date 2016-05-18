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
		System.out.println("The current player has already used all the avaiable quick actions this turn");
		return false;
	}
}
