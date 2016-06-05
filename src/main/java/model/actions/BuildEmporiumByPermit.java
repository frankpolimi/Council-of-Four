package model.actions;

import model.game.BuildingPermit;
import model.game.Emporium;
import model.game.Game;
import model.game.topology.City;

/**
 * @author Vitaliy Pakholko
 */
public class BuildEmporiumByPermit extends MainAction 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7577677514844901289L;
	private BuildingPermit permit;
	private City city;
	
	public BuildEmporiumByPermit(BuildingPermit permit, City city) {
		this.permit = permit;
		this.city = city;
	}
	
	/**
	 * The player builds an emporium in the city indicated by the building permit he is using. If the action succeeds that building permit will be moved in his used permits deck.
	 * @throws IllegalStateException if the player has no Main actions left
	 * @throws IllegalStateException if the player has not enough coins to pay the council
	 * @throws IllegalStateException if the player has not enough assistants to pay the building costs
	 * @throws IllegalStateException if the player has no emporiums left
	 * @throws IllegalArgumentException if the player has already built in the city he is indicating
	 * @throws IllegalArgumentException if the city where the player is trying to build is not present on the permit
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Main actions");
		if (game.getCurrentPlayer().getRemainingEmporiums()<=0)
		{
			throw new IllegalStateException("No avaiable emporiums");
		}
		for(Emporium e:game.getCurrentPlayer().getEmporium())
			if(e.getCity().equals(city))
			{
				throw new IllegalArgumentException("The player has already built an emporium in this city");
			}
		
		if(!permit.getBuildingAvaliableCities().contains(city))
		{
			throw new IllegalArgumentException("The city where the player is trying to build is not present on the permit");
		}
		
		int otherEmporiums=city.getEmporiums().size();
		
		if(game.getCurrentPlayer().checkAssistants(otherEmporiums))
		{
			game.decrementMainActionCounter();
			game.getCurrentPlayer().usePermit(permit);
			for(City c:game.getAllCities())
			{
				if(c.equals(city))
				{
					city.addEmporium(game.getCurrentPlayer());
					city.applyBonus(game);
					break;
				}
			}
			super.takeAction(game);
			return true;
		}
		else 
		{
			throw new IllegalStateException("Not enough assistants to build in this city. For each other player's emporium you have to pay 1 assistant");
		}	
	}

	@Override
	public String toString() {
		return "BuildEmproriumByPermit: The player tries to build an Emporium in a City using the corresponding BuildingPermit he/she possesses.";
	}

	
	public BuildingPermit getPermit() {
		return permit;
	}

	public void setPermit(BuildingPermit permit) {
		this.permit = permit;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	
	
}
