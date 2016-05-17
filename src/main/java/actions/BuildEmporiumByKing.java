package actions;

import java.util.ArrayList;

import cg2.game.Game;
import cg2.model.City;
import cg2.model.Emporium;
import council.Council;
import politics.PoliticsCard;

/**
 * @author Vitaliy Pakholko
 */
public class BuildEmporiumByKing extends MainAction 
{
	Council counc;
	ArrayList<PoliticsCard> politics; 
	City city;
	
	@Override
	public boolean takeAction(Game game)
	{
		if(!payCouncil(game.getCurrentPlayer(),counc,politics))
		{
			System.out.println("Council not satisfied");
			return false;
		}
		for(Emporium e:game.getCurrentPlayer().getEmporium())
			if(e.getCity()==city)
			{
				System.out.println("The game.getCurrentPlayer() has already built an emporium in this city");
				return false;
			}
		
		int distancePayment=2;/*(city--O quel che e--.getDistance(this.game.getKingsPosition()));*/
		if(!game.getCurrentPlayer().checkCoins(distancePayment))
		{
			System.out.println("Not enough coins to move king. The Player needs:" +distancePayment+" coins to pay, 2 for each step");
			return false;
		}
		if(game.getCurrentPlayer().checkAssistants(city.getEmporiums().size()))
		{
			game.decrementMainActionCounter();
			game.setKingsPosition(city);
			city.addEmporium(game.getCurrentPlayer());
			//Applicare i bonus
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

	public BuildEmporiumByKing(Council counc, ArrayList<PoliticsCard> politics, City city) 
	{
		this.counc = counc;
		this.politics = politics;
		this.city = city;
	}
	
	
	
}
