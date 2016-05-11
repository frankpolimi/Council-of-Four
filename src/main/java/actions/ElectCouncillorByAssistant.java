package actions;

import cg2.player.Player;
import council.Council;
import council.Councillor;

/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillorByAssistant extends QuickAction
{
	public boolean takeAction(Player player, Council council, Councillor councillor)
	{
		if(player.checkAssistants(1))
		{
			council.electCouncillor(councillor);
			this.game.decrementQuickActionCounter();
			return true;
		}
		else 
		{
			System.out.println("You need at least 1 assistant to elect a councillor by quick action");
			return false;
		}	
	}

	@Override
	public String toString() 
	{
		return "ElectCouncillorByAssistant: The Player pays 1 Assistant and chooses an avaiable Councillor and insersts him in a chosen Council shifting all the councillors already present. The pushed out Councillor returns"
				+ "to the board";
	}
}
