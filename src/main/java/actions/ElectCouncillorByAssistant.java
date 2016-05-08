package actions;

import cg2.player.Player;
import council.Council;
import council.Councillor;

public class ElectCouncillorByAssistant extends QuickAction
{
	public void takeAction(Player player, Council council, Councillor councillor)
	{
		if(player.getStatus().getAssistants()>0)
		{
			player.getStatus().setAssistants(player.getStatus().getAssistants()-1);
			council.electCouncillor(councillor);
		}
	}
}
