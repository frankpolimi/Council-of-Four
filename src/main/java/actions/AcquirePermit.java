package actions;

import java.util.ArrayList;

import cg2.model.BuildingPermit;
import cg2.player.Player;
import council.Council;

import politics.PoliticsCard;

public class AcquirePermit extends MainAction 
{
	public void takeAction(Player player, Council council, ArrayList<PoliticsCard> politics, BuildingPermit permit)
	{
		if(payCouncil(player,council,politics))
			if(council.getPermitsDeck().givePermit(player, permit))
				this.game.decrementMainActionCounter();
	}

	@Override
	public String toString() {
		return "AcquirePermit: The player tries to pay a council, using a nunmber a cards equals or less the number of councillors in a council,"
				+ "with the intent to acquire a BuildingPermit situated in the council's corresponding deck";
	}
	
	

}