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
		{
			council.getPermitsDeck().givePermit(player, permit); //E se non ci fosse il permit?
		}
	}


}