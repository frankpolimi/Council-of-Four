package actions;

import cg2.game.Game;
import council.Council;
import council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillor extends MainAction 
{
	private Councillor councillor;
	private Council council;
	
	
	@Override
	public boolean takeAction(Game game)
	{	
		game.addCouncillor(council.electCouncillor(councillor));
		game.getCurrentPlayer().setCoins(game.getCurrentPlayer().getCoins()+4);
		game.decrementMainActionCounter();
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

	public ElectCouncillor(Councillor councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
	}
	
	
}
