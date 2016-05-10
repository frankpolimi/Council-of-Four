package actions;

import cg2.model.PermitsDeck;
import cg2.player.Player;

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
}
