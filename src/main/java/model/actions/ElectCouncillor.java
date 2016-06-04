package model.actions;

import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.topology.Region;
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
	//private Council council;
	private Region region;
	
	public ElectCouncillor(Councillor councillor,/* Council council*/Region region) {
		this.councillor = councillor;
		//this.council = council;
		this.region=region;
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
		for(Region r:game.getRegions())
		{
			if(r.equals(this.region))
			{
				game.addCouncillor(region.getCouncil().electCouncillor(councillor));
				game.getCurrentPlayer().setCoins(game.getCurrentPlayer().getCoins()+4);
				game.decrementMainActionCounter();
				super.takeAction(game);
				return true;			
			}
		}
		return false;
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
