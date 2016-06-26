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
	
	/**
	 * constructor for the action that allows the player to acquire
	 * a building permit by selecting the permit to acquire, the council
	 * to pay with some given politics cards
	 * @param council the regional council that the player wants to 
	 * 					corrupt in order to acquire a specific permit
	 * 					the council is forcibly a regional council
	 * 					because only this kind of council holds a permit deck
	 * @param politics the list of cards that the player wants to use
	 * 					to corrupt the council
	 * @param permit the specific permit that a player wants to acquire
	 */
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
	 * @throws IllegalArgumentException if the player indicated a wrong Building permit that is not held by the
	 * 									council given as a field of the action
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

	/**
	 * get the regional council that the player tries 
	 * to corrupt with the politics cards
	 * @return the council that a player tries to corrupt
	 */
	public RegionalCouncil getCouncil() {
		return council;
	}

	/**
	 * set the regional council that the player tries 
	 * to corrupt with the politics cards
	 * @param council the council that a player tries to corrupt
	 */
	public void setCouncil(RegionalCouncil council) {
		this.council = council;
	}

	/**
	 * get the list of cards that a player has decided
	 * to give in to try and corrupt the council
	 * @return the list of cards that a player tried to use
	 */
	public ArrayList<PoliticsCard> getPolitics() {
		return politics;
	}

	/**
	 * set the list of cards that a player has decided
	 * to give in to try and corrupt the council
	 * @param politics the list of cards that a player tried to use
	 */
	public void setPolitics(ArrayList<PoliticsCard> politics) {
		this.politics = politics;
	}

	/**
	 * get the building permit that a player has 
	 * decided to try and acquire. this permit 
	 * will be only a face up permit of a 
	 * permit deck of a specific region
	 * @return the permit chosen by the player
	 */
	public BuildingPermit getPermit() {
		return permit;
	}

	/**
	 * set the building permit that a player has 
	 * decided to try and acquire. this permit 
	 * will be only a face up permit of a 
	 * permit deck of a specific region
	 * @param permit the permit chosen by the player
	 */
	public void setPermit(BuildingPermit permit) {
		this.permit = permit;
	}
}