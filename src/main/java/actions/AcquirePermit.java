package actions;

import java.util.ArrayList;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import council.RegionalCouncil;
import politics.PoliticsCard;

/**
 * @author Vitaliy Pakholko
 */
public class AcquirePermit extends MainAction 
{
	
	private RegionalCouncil council;
	private ArrayList<PoliticsCard> politics;
	private BuildingPermit permit;
	
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			return false;
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

	public AcquirePermit(RegionalCouncil council, ArrayList<PoliticsCard> politics, BuildingPermit permit) 
	{
		this.council = council;
		this.politics = politics;
		this.permit = permit;
	}
	

	
	
	

}