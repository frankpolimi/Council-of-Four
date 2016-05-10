package actions;

import cg2.player.Player;

public class ExtraMainAction extends QuickAction 
{
	public boolean takeAction(Player player)
	{
		if(player.checkAssistants(3))
		{
			this.game.incrementMainActionCounter();
			return true;
		}
		else
		{
			System.out.println("Not enought assistants to get an extra main action. An extra main action costs 3 assistants");
			return false;
		}
	}
	
}
