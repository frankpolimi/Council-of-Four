package actions;

import cg2.player.Player;
import council.Council;
import council.Councillor;

public class ElectCouncillorByAssistant extends QuickAction
{
	public void takeAction(Player player, Council council, Councillor councillor)
	{
		if(player.checkAssistants(1))
		{
			council.electCouncillor(councillor);
			this.game.decrementQuickActionCounter();
		}
		else System.out.println("You need at least 1 assistant to elect a councillor by quick action");
	}
}
