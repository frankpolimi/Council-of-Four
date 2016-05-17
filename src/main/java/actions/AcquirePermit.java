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
	public void takeAction(Game game, Council council, ArrayList<PoliticsCard> politics, BuildingPermit permit)
	{
		if(payCouncil(game.getCurrentPlayer(),council,politics))
			if(council.getPermitsDeck().givePermit(game, permit))
				this.game.decrementMainActionCounter();
	}

	@Override
	public String toString() {
		return "AcquirePermit: The player tries to pay a council, using a nunmber a cards equals or less the number of councillors in a council,"
				+ "with the intent to acquire a BuildingPermit situated in the council's corresponding deck.";
	}
	
	

}