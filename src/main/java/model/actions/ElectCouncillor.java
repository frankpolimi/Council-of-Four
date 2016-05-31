package model.actions;

import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillor extends MainAction 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3153051699153428116L;
	private Councillor councillor;
	private Council council;
	
	public ElectCouncillor(Councillor councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
	}
	
	/** 
	 * The player gains 4 coins by choosing a councillor to push in a given council
	 * @throws IllegalStateException if the player has no Main actions left
	 */
	@Override
	public boolean takeAction(Game game)
	{	
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Main actions");
		game.addCouncillor(council.electCouncillor(councillor));
		game.getCurrentPlayer().setCoins(game.getCurrentPlayer().getCoins()+4);
		game.decrementMainActionCounter();
		super.takeAction(game);
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

	public Councillor getCouncillor() {
		return councillor;
	}

	public void setCouncillor(Councillor councillor) {
		this.councillor = councillor;
	}

	public Council getCouncil() {
		return council;
	}

	public void setCouncil(Council council) {
		this.council = council;
	}

}
