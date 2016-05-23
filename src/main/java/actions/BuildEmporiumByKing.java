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
	
	
	/**
	 * The player builds an emporium in the king's position. He can move the king using roads if he wants, to do that he must pay 2 coins for each hop.
	 * The action fails if: the player cannot pay the king's movement fee, the player cannot pay the council, the player has already built an emporium in the king's final position city
	 * the player cannot pay the assistants fee for other player's emporiums.
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			return false;
		if(!payCouncil(game.getCurrentPlayer(),counc,politics))
		{
			System.out.println("Council not satisfied");
			return false;
		}
		for(Emporium e:game.getCurrentPlayer().getEmporium())
			if(e.getCity()==city)
			{
				throw new IllegalStateException("The game.getCurrentPlayer() has already built an emporium in this city");
			}
		
		int distancePayment=2;
		distancePayment*=game.getMap().howManyVertexPassed(game.getKingsPosition(), city);
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
			game.getMap().applyConnectedCitiesBonus(city, game.getCurrentPlayer().getEmporiumsCitiesSet(), game);
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
