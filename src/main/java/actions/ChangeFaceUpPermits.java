package actions;

import cg2.game.Game;
import cg2.model.PermitsDeck;

/**
 * @author Vitaliy Pakholko
 */
public class ChangeFaceUpPermits extends QuickAction 
{
	private PermitsDeck deck;
	
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			return false;
		if(game.getCurrentPlayer().checkCoins(2))
		{
			deck.changeFaceUpPermits();
			game.decrementQuickActionCounter();
			return true;
		}
		else 
		{
			System.out.println("Not enough coins to change the Permits");
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChangeFaceUpPermits: The player pays 2 coins to swap the 2 faced up BuildingPermits of a PermitDeck with a new pair of the same deck.";
	}
	
	
}
