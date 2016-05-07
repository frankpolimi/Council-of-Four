package actions;

import cg2.player.Player;

public class EnageAssistant extends QuickAction 
{
	public void takeAction(Player player)
	{
		if(player.getStatus().getCoins()>=3)
		{
			player.getStatus().setAssistants(player.getStatus().getAssistants()+1);
			player.getStatus().setCoins(player.getStatus().getCoins()-3);
		}
		else System.out.println("Not enough coins to engage an assistant. An assistant costs 3 coins");
	}
}
