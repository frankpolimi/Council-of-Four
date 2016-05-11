package actions;

import cg2.player.Player;

import council.Council;
import council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillor extends MainAction 
{
	public boolean takeAction(Player player, Councillor councillor, Council council)
	{	
		this.game.addCouncillor(council.electCouncillor(councillor));
		player.getStatus().setCoins(player.getStatus().getCoins()+4);
		this.game.decrementMainActionCounter();
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ElectCouncillor: The Player chooses an avaiable Councillor and insersts him in a chosen Council shifting all the councillors already present. The pushed out Councillor returns"
				+ "to the board and the Player gets 4 Coins";
	}
	
	
}
