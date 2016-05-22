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
	
	
	
	
	/**
	 * The player acquires a face up building permit if he can pay the council. 
	 * The action fails if:
	 * the player  cannot pay the council, 
	 * the player want to acquire a not face up permit. 
	 * the player is trying to do an action type that he had already used.
	 */
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