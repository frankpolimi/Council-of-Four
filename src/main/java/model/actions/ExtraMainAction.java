package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public class ExtraMainAction extends QuickAction 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2852349205622088L;

	/**
	 * The player pays 3 assistants to get an extra main action
	 * @throws IllegalStateException if the player has no Quick actions left
	 * @throws IllegalStateException if the player has not enough assistants
	 */
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Quick actions");
		if(game.getCurrentPlayer().checkAssistants(3))
		{
			game.incrementMainActionCounter();
			return true;
		}
		else
		{
			throw new IllegalStateException("Not enought assistants to get an extra main action. An extra main action costs 3 assistants");
		}
	}

	@Override
	public String toString() 
	{
		return "ExtraMainAction: The player pays 3 Councillors to get an extra MainAction";
	}
	
	
	
}
