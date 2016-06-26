package model.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import controller.ModelChange;
import model.game.Emporium;
import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.politics.ColoredPoliticsCard;
import model.game.politics.PoliticsCard;
import model.game.topology.City;

/**
 * @author Vitaliy Pakholko
 */
public class Action implements Act, Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5134853686884005851L;

	/**
	 * Super class action's take action method. 
	 * @param game is the game on which the action will be applied
	 */
	@Override
	public boolean takeAction(Game game)
	{
		game.notifyObservers(new ModelChange(game));
		return false;
	}
	
	@Override
	public boolean checkAction(Game game) 
	{
		
		return false;
	}
	
	/**
	 * method that performs common checks for building an emporium
	 * @param game the game that the action tries to modify
	 * @param city the city where the player is trying to build an emporium
	 * @return true if all checks are passed
	 * @throws IllegalStateException if the player isn't able to perform
	 * 			the action due to not possessing enough emporiums left
	 * 			or not having the possibility to perform an action
	 * @throws IllegalArgumentException if the player has already built
	 * 			an emporium in the city given as parameter
	 */
	public boolean buildEmporiumControls(Game game, City city)
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
		return true;
	}
	
	/**
	 * method that allows an action to calculate the cost of a 
	 * concil's corruption. 
	 * @param game the game which the action is applied
	 * @param counc the council that the player is trying to corrupt
	 * 				with a given list of politics cards 
	 * @param politics the list of politics cards that a player tries
	 * 			to corrupt the council with
	 * @return true if all checks are passed, false otherwise
	 */
	public boolean payCouncil(Game game, Council counc, ArrayList<PoliticsCard> politics)
	{
			int matches=0;
			int jollyCounter=0;
			ArrayBlockingQueue<Councillor> councillors=new ArrayBlockingQueue<>(counc.getCouncillors().size());
			for(Councillor councillor:counc.getCouncillors())
			{
				councillors.offer(councillor);
			}
			for(int i=0; i<politics.size();i++)
				for(Councillor councillor:councillors)
				{
					if(politics.get(i).payCouncillor(councillor))
					{
						matches++;
						if(politics.get(i).getClass()==ColoredPoliticsCard.class)
						{
							councillors.remove(councillor);
						}
						else
						{
							jollyCounter++;
						}
						break;
					}
				}
			
			int cost;
			switch (counc.getCouncillors().size()-matches)
			{
				case 0: cost=0; 
					break;
				case 1: cost=4;
					break;
				case 2: cost=7; 
					break;
				case 3: cost=10; 
					break;
				default: return false;
			}
			cost+=jollyCounter;
			if(!game.getCurrentPlayer().checkCoins(cost))
				return false;
			else
			{
				game.getCurrentPlayer().removeCards(politics);
				return true;
			}
	}

}
