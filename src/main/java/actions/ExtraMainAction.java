package actions;

import cg2.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public class ExtraMainAction extends QuickAction 
{
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			return false;
		if(game.getCurrentPlayer().checkAssistants(3))
		{
			game.incrementMainActionCounter();
			return true;
		}
		else
		{
			System.out.println("Not enought assistants to get an extra main action. An extra main action costs 3 assistants");
			return false;
		}
	}

	@Override
	public String toString() 
	{
		return "ExtraMainAction: The player pays 3 Councillors to get an extra MainAction";
	}
	
	
	
}
