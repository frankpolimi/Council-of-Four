package model.actions;

import java.util.ArrayList;

import model.game.Emporium;
import model.game.Game;
import model.game.council.Council;
import model.game.politics.PoliticsCard;
import model.game.topology.City;

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
	 * @throws IllegalStateException if the player has no Main actions left
	 * @throws IllegalStateException if the player has not enough coins to pay the council
	 * @throws IllegalStateException if the player has not enough coins to pay the king's movement
	 * @throws IllegalStateException if the player has not enough assistants to pay the building costs
	 * @throws IllegalStateException if the player has no emporiums left
	 * @throws IllegalArgumentException if the player has already built in the city he is indicating
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
		if(!payCouncil(game.getCurrentPlayer(),counc,politics))
		{
			throw new IllegalStateException("Not enough coins or Cards to pay the council."
					+ " For 1 missing politics card you pay 4 additional coins, for each additional missing politics card you add 3 more");
			//System.out.println("Council not satisfied");

		}
		for(Emporium e:game.getCurrentPlayer().getEmporium())
			if(e.getCity()==city)
			{
				throw new IllegalArgumentException("The game.getCurrentPlayer() has already built an emporium in this city");
			}
		
		int distancePayment=2;
		distancePayment*=game.getMap().howManyVertexPassed(game.getKingsPosition(), city);
		if(!game.getCurrentPlayer().checkCoins(distancePayment))
		{
			throw new IllegalStateException("Not enough coins to move king. The Player needs:" +distancePayment+" coins to pay, 2 for each step");
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
			throw new IllegalStateException("Not enough assistants to build in this city. For each other player's emporium you have to pay 1 assistant");
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

	public Council getCounc() {
		return counc;
	}

	public void setCounc(Council counc) {
		this.counc = counc;
	}

	public ArrayList<PoliticsCard> getPolitics() {
		return politics;
	}

	public void setPolitics(ArrayList<PoliticsCard> politics) {
		this.politics = politics;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
}
