package model.actions;

import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.topology.Region;

/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillorByAssistant extends QuickAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1056666433218005285L;
	private Region region;
	private Councillor councillor;
	
	public ElectCouncillorByAssistant(Region region, Councillor councillor) 
	{
		this.region = region;
		this.councillor = councillor;
	}
	
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
			for(Region r:game.getRegions())
				if(r.equals(region))
				{
					this.region.getCouncil().electCouncillor(councillor);
					game.decrementQuickActionCounter();
					super.takeAction(game);
					return true;
				}
			return false;
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

	

	public Councillor getCouncillor() {
		return councillor;
	}

	public void setCouncillor(Councillor councillor) {
		this.councillor = councillor;
	}
	
	
	
	
}
