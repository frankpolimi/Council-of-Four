package model.actions;

import java.util.Iterator;

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
	private Councillor councillor;
	private Council council;
	
	/**
	 * constructor for an action that allows the player to elect
	 * a councillor in a council (given both as parameters) 
	 * by paying an assistants
	 * @param council the council where the councillor will be elected
	 * @param councillor the councillor that will be elected
	 */
	public ElectCouncillorByAssistant(Council council, Councillor councillor) 
	{
		this.council = council;
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
		if(game.getCurrentPlayer().checkAssistants(1)){
			Iterator<Council> i = game.getAllCouncils().iterator();
			while(i.hasNext()){
				Council c = i.next();
				if(c.equals(council)){
					game.addCouncillor(c.electCouncillor(councillor));
					game.decrementQuickActionCounter();
					super.takeAction(game);
					return true;	
				}
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

	
	/**
	 * get the councillor given as parameter
	 * @return the councillor to be elected
	 */
	public Councillor getCouncillor() {
		return councillor;
	}

	/**
	 * set the councillor that will be elected
	 * by this action
	 * @param councillor the councillor to be elected
	 */
	public void setCouncillor(Councillor councillor) {
		this.councillor = councillor;
	}
	
	
	
	
}
