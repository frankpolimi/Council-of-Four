package actions;

import java.util.ArrayList;
import cg2.model.City;
import cg2.model.Emporium;
import cg2.player.Player;
import council.Council;
import politics.PoliticsCard;

/**
 * @author Vitaliy Pakholko
 */
public class BuildEmporiumByKing extends MainAction 
{
	public boolean takeAction(Player player, Council counc, ArrayList<PoliticsCard> politics, City city)
	{
		if(!payCouncil(player,counc,politics))
		{
			System.out.println("Council not satisfied");
			return false;
		}
		for(Emporium e:player.getEmporium())
			if(e.getCity()==city)
			{
				System.out.println("The player has already built an emporium in this city");
				return false;
			}
		
		int distancePayment=2;/*(city--O quel che e--.getDistance(this.game.getKingsPosition()));*/
		if(!player.checkCoins(distancePayment))
		{
			System.out.println("Not enough coins to move king. The player needs:" +distancePayment+" coins to pay, 2 for each step");
			return false;
		}
		if(player.checkAssistants(city.getEmporiums().size()))
		{
			this.game.decrementMainActionCounter();
			this.game.setKingsPosition(city);
			city.addEmporium(player);
			//Qua ci va la parte di attivare i bonus
			return true;
		}
		else
		{
			System.out.println("Not enough assistants to build in this city. For each other player's emporium you have to pay 1 assistant");
			return false;
		}

		
	}

	@Override
	public String toString() 
	{
		return "BuildEmporiumByKing: The player moves the king in a city reachable by it's current position. Then he/she proceds to pay the king 2 coins for each city he passed by. If the king doesn't "
				+ "the player doesn't have to pay. Then the player proceds to build an emporium in the king's current city";
	}
	
	
}
