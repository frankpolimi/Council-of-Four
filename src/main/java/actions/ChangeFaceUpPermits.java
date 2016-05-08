package actions;

import cg2.model.PermitsDeck;
import cg2.player.Player;

public class ChangeFaceUpPermits extends QuickAction 
{
	public void takeAction(Player player, PermitsDeck deck)
	{
		if(player.getStatus().getCoins()>=2)
		{
			player.getStatus().setCoins(player.getStatus().getCoins()-2);
			deck.changeFaceUpPermits();
		}
		else System.out.println("Not enough coins to change the Permits");
	}
}
