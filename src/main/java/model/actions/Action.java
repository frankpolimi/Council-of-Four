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
	
	public boolean buildEmporiumControls(Game game, City city)
	{
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Main actions"); // ste cose comuni a build by king e permit vanno aggregate in un unico metodo
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
