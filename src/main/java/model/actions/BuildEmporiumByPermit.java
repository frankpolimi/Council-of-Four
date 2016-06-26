package model.actions;

import model.game.BuildingPermit;
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
	
	/**
	 * constructor for the action that allows the player to build an
	 * emporium on a city specified by the building permit that the player
	 * wants to use
	 * @param permit the permit that a player possess and is trying to use to
	 * 				build an emporium on a specified city
	 * @param city one of the cities that are stated on the building permit
	 * 				on which the player is trying to build an emporium 
	 */
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
		this.buildEmporiumControls(game, city);
		
		boolean contained=false;
		for(City c:permit.getBuildingAvaliableCities())
		{
			if(city.equals(c))
			{
				contained=true;
				break;
			}
		}
		if(!contained)
			throw new IllegalArgumentException("The city where the player is trying to build is not present on the permit");
		
		/*if(!permit.getBuildingAvaliableCities().contains(city))
		{
			throw new IllegalArgumentException("The city where the player is trying to build is not present on the permit");
		}*/
		
		int otherEmporiums=city.getEmporiums().size();
		
		if(game.getCurrentPlayer().checkAssistants(otherEmporiums))
		{
			game.decrementMainActionCounter();
			game.getCurrentPlayer().usePermit(permit);
			for(City c:game.getAllCities())
			{
				if(c.equals(city))
				{
					c.addEmporium(game.getCurrentPlayer());
					game.getMap().applyConnectedCitiesBonus(c, 
							game.getCurrentPlayer().getEmporiumsCitiesSet(), game);
					game.giveTiles(game.getCurrentPlayer(), c);
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

	/**
	 * get the permit which the player has decided
	 * to use to build an emporium 
	 * @return the permit used by the player
	 */
	public BuildingPermit getPermit() {
		return permit;
	}

	/**
	 * set the permit which the player has decided
	 * to use to build an emporium 
	 * @param the permit used by the player
	 */
	public void setPermit(BuildingPermit permit) {
		this.permit = permit;
	}

	/**
	 * get the city on which the player has decided
	 * to build an emporium 
	 * @return the city where the player wants to build
	 */
	public City getCity() {
		return city;
	}

	/**
	 * set the permit which the player has decided
	 * to an emporium 
	 * @param the city where the player wants to build
	 */
	public void setCity(City city) {
		this.city = city;
	}
	
	
	
}
