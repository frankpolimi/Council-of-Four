package actions;

import cg2.player.Player;

public class EngageAssistant extends QuickAction 
{
	public boolean takeAction(Player player)
	{
		if(player.checkCoins(3))
		{
			player.getStatus().setAssistants(player.getStatus().getAssistants()+1);
			this.game.decrementQuickActionCounter();
			return true;
		}
		else 
		{
			System.out.println("Not enough coins to engage an assistant. An assistant costs 3 coins");
			return false;
		}
	}
}
