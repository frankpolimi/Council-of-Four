package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public class EngageAssistant extends QuickAction 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8167109683946217936L;


	/**
	 * The player pays 3 coins to get an assistant
	 * @throws IllegalStateException if the player has no Quick actions left
	 * @throws IllegalStateException if the player has not enough coins
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Quick actions");
		if(game.getCurrentPlayer().checkCoins(3))
		{
			game.getCurrentPlayer().setAssistants(game.getCurrentPlayer().getAssistants()+1);
			game.decrementQuickActionCounter();
			return true;
		}
		else 
		{
			throw new IllegalStateException("Not enough coins to engage an assistant. An assistant costs 3 coins");
		}
		
		
	}

	
	@Override
	public String toString() 
	{
		return "EngageAssistant: The Player pays 3 coins to get an Assistant from the board ";
	}
}
