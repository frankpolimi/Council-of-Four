package actions;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.model.City;
import cg2.model.Emporium;

/**
 * @author Vitaliy Pakholko
 */
public class BuildEmproriumByPermit extends MainAction 
{
	private BuildingPermit permit;
	private City city;
	
	
	/**
	 * The player builds an emporium in the city indicated by the building permit he is using. If the action succeeds that building permit will be moved in his used permits deck.
	 * The action fails if: the player cannot pay the council, the player has already built an emporium in the king's final position city
	 * the player cannot pay the assistants fee for other player's emporiums, the player indicates a city that is not present on the building permit, the player has no emporiums to use.
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			return false;
		if (game.getCurrentPlayer().getRemainingEmporiums()<=0)
		{
			System.out.println("No avaiable emporiums");
			return false;
		}
		for(Emporium e:game.getCurrentPlayer().getEmporium())
			if(e.getCity()==city)
			{
				System.out.println("The player has already built an emporium in this city");
				return false;
			}
		
		if(!permit.getBuildingAvaliableCities().contains(city))
		{
			System.out.println("The city where the player is trying to build is not present on the permit");
			return false;
		}
		
		int otherEmporiums=city.getEmporiums().size();
		
		if(game.getCurrentPlayer().checkAssistants(otherEmporiums))
		{
			game.decrementMainActionCounter();
			game.getCurrentPlayer().usePermit(permit);
			city.addEmporium(game.getCurrentPlayer());
			city.applyBonus(game);
			return true;
		}
		else 
		{
			System.out.println("Not enough assistants to build in this city. For each other player's emporium you have to pay 1 assistant");
			return false;
		}	
	}

	@Override
	public String toString() {
		return "BuildEmproriumByPermit: The player tries to build an Emporium in a City using the corresponding BuildingPermit he/she possesses.";
	}

	public BuildEmproriumByPermit(BuildingPermit permit, City city) {
		this.permit = permit;
		this.city = city;
	}
	
	
	
}
