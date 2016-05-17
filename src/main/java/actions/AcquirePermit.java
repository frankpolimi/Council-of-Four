package actions;

import java.util.ArrayList;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import council.Council;

import politics.PoliticsCard;

/**
 * @author Vitaliy Pakholko
 */
public class AcquirePermit extends MainAction 
{
	
	private Council council;
	private ArrayList<PoliticsCard> politics;
	BuildingPermit permit;
	
	@Override
	public boolean takeAction(Game game)
	{
		if(payCouncil(game.getCurrentPlayer(),council,politics))
			if(council.getPermitsDeck().givePermit(game, permit))
			{
				game.decrementMainActionCounter();
				return true;
			}else return false;
		return false;
	}

	@Override
	public String toString() {
		return "AcquirePermit: The player tries to pay a council, using a nunmber a cards equals or less the number of councillors in a council,"
				+ "with the intent to acquire a BuildingPermit situated in the council's corresponding deck.";
	}

	public AcquirePermit(Council council, ArrayList<PoliticsCard> politics, BuildingPermit permit) 
	{
		this.council = council;
		this.politics = politics;
		this.permit = permit;
	}
	

	
	
	

}