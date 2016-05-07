package actions;

import cg2.model.BuildingPermit;
import cg2.model.City;
import cg2.model.Emporium;
import cg2.player.Player;

public class BuildEmproriumByPermit extends MainAction 
{
	public void takeAction(Player player, BuildingPermit permit, City city)
	{
		if (player.getRemainingEmporiums()<=0)
			throw new IllegalStateException("No avaiable emporiums");
		for(Emporium e:player.getEmporium())
			if(e.getCity()==city)
				throw new IllegalStateException("The player has already built an emporium in this city");
		
		if(!permit.getBuildingAvaliableCities().contains(city))
			throw new IllegalStateException("The city where the player is trying to build is not present on the permit");
		
		int otherEmporiums=city.getEmporiums().size();
		
		if(player.getStatus().getAssistants()-otherEmporiums>=0)
			player.getStatus().setAssistants(player.getStatus().getAssistants()-otherEmporiums);
		else System.out.println("Not enough assistants to build in this city. For each other player's emporium you have to pay 1 assistant");
		
		city.addEmporium(player);
	}
}
