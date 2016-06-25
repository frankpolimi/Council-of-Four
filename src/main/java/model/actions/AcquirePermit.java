package model.actions;

import java.util.ArrayList;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.council.RegionalCouncil;
import model.game.politics.PoliticsCard;

/**
 * @author Vitaliy Pakholko
 */
public class AcquirePermit extends MainAction 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4581355540805367240L;
	private RegionalCouncil council;
	private ArrayList<PoliticsCard> politics;
	private BuildingPermit permit;
	
	
	public AcquirePermit(RegionalCouncil council, ArrayList<PoliticsCard> politics, BuildingPermit permit) 
	{
		this.council = council;
		this.politics = politics;
		this.permit = permit;
	}
	
	/**
	 * The player acquires a face up building permit if he can pay the council. 
	 * @throws IllegalStateException if the player has no Main actions left
	 * @throws IllegalStateException if the player has not enough coins to pay the council
	 * @throws IllegalArgumentException if the player indicated a wrong Building permit for the city he is trying to build in
	 */
	@Override
	public boolean takeAction(Game game)
	{
		boolean permitAcquired=false;
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Main actions");
		RegionalCouncil council=game.getRegions().stream().map(e->e.getCouncil()).filter(e->e.equals(this.council)).findFirst().get();
		if(payCouncil(game,council,politics))
		{
			try
			{
				permitAcquired=council.getPermitsDeck().givePermit(game, permit);
			}catch(IllegalStateException e)
			{
				permitAcquired=true;
			}
			if(permitAcquired)
			{
				game.decrementMainActionCounter();
				super.takeAction(game);
				return true;
			}else return false;
		}
		else throw new IllegalStateException("Not enough coins or Cards to pay the council. For 1 missing politics card you pay 4 additional coins,"
				+ " for each additional missing politics card you add 3 more");
	}

	@Override
	public String toString() {
		return "AcquirePermit: The player tries to pay a council, using a nunmber a cards equals or less the number of councillors in a council,"
				+ "with the intent to acquire a BuildingPermit situated in the council's corresponding deck.";
	}

	
	public RegionalCouncil getCouncil() {
		return council;
	}

	public void setCouncil(RegionalCouncil council) {
		this.council = council;
	}

	public ArrayList<PoliticsCard> getPolitics() {
		return politics;
	}

	public void setPolitics(ArrayList<PoliticsCard> politics) {
		this.politics = politics;
	}

	public BuildingPermit getPermit() {
		return permit;
	}

	public void setPermit(BuildingPermit permit) {
		this.permit = permit;
	}
}