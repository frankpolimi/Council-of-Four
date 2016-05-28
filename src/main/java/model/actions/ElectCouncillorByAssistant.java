package model.actions;

import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;

/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillorByAssistant extends QuickAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1056666433218005285L;
	private Council council;
	private Councillor councillor;
	/**
	 * The player pays 1 assistant and chooses a councillor to push it in a council
	 * @throws IllegalStateException if the player has no Quick actions left
	 * @throws IllegalStateException if the player has no assistants
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Quick actions");
		if(game.getCurrentPlayer().checkAssistants(1))
		{
			council.electCouncillor(councillor);
			game.decrementQuickActionCounter();
			return true;
		}
		else 
		{
			throw new IllegalStateException("You need at least 1 assistant to elect a councillor by quick action");
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

	public Council getCouncil() {
		return council;
	}

	public void setCouncil(Council council) {
		this.council = council;
	}

	public Councillor getCouncillor() {
		return councillor;
	}

	public void setCouncillor(Councillor councillor) {
		this.councillor = councillor;
	}
	
	
	
	
}
