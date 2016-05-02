package actions;

import cg2.player.Player;
import council.Council;
import council.Councillor;

public class ElectCouncillor extends MainAction 
{
	public void takeAction(Player player, Councillor councillor, Council council)
	{	
		this.game.addCouncillor(council.electCouncillor(councillor));
	}
}
