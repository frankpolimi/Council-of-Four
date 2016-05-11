package actions;

import cg2.model.PermitsDeck;
import cg2.player.Player;

/**
 * @author Vitaliy Pakholko
 */
public class ChangeFaceUpPermits extends QuickAction 
{
	public boolean takeAction(Player player, PermitsDeck deck)
	{
		if(player.checkCoins(2))
		{
			deck.changeFaceUpPermits();
			this.game.decrementQuickActionCounter();
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
