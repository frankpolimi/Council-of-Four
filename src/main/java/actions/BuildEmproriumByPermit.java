package actions;

import cg2.model.BuildingPermit;
import cg2.model.City;
import cg2.model.Emporium;
import cg2.player.Player;

public class BuildEmproriumByPermit extends MainAction 
{
	public boolean takeAction(Player player, BuildingPermit permit, City city)
	{
		if (player.getRemainingEmporiums()<=0)
		{
			System.out.println("No avaiable emporiums");
			return false;
		}
		for(Emporium e:player.getEmporium())
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
		
		if(player.checkAssistants(otherEmporiums))
		{
			this.game.decrementMainActionCounter();
			city.addEmporium(player);
			return true;
		}
		else 
		{
			System.out.println("Not enough assistants to build in this city. For each other player's emporium you have to pay 1 assistant");
			return false;
		}
		
		
	}
}
