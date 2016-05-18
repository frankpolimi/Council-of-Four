package actions;

import cg2.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public class EngageAssistant extends QuickAction 
{
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			return false;
		if(game.getCurrentPlayer().checkCoins(3))
		{
			game.getCurrentPlayer().setAssistants(game.getCurrentPlayer().getAssistants()+1);
			game.decrementQuickActionCounter();
			return true;
		}
		else 
		{
			System.out.println("Not enough coins to engage an assistant. An assistant costs 3 coins");
			return false;
		}
		
		
	}

	
	@Override
	public String toString() 
	{
		return "EngageAssistant: The Player pays 3 coins to get an Assistant from the board ";
	}
}
