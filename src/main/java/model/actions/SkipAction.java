package model.actions;

import model.game.Game;

public class SkipAction extends QuickAction 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7504135483550600131L;

	/**
	 * This action allows the player to skip his Quick action this turn.
	 * @throws IllegalStateException if the player has no Quick actions avaiable
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			throw new IllegalStateException("You already have 0 quick actions");
		else
		{
			game.decrementQuickActionCounter();
			return true;
		}
		
	}
}
