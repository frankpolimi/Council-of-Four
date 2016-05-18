package actions;

import cg2.game.Game;
import council.Council;
import council.Councillor;

/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillorByAssistant extends QuickAction
{
	private Council council;
	private Councillor councillor;
	
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			return false;
		if(game.getCurrentPlayer().checkAssistants(1))
		{
			council.electCouncillor(councillor);
			game.decrementQuickActionCounter();
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

	public ElectCouncillorByAssistant(Council council, Councillor councillor) 
	{
		this.council = council;
		this.councillor = councillor;
	}
	
	
}
