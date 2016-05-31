package model.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import controller.ModelChange;
import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.politics.ColoredPoliticsCard;
import model.game.politics.PoliticsCard;

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
	
	public boolean payCouncil(Game game, Council counc, ArrayList<PoliticsCard> politics)
	{
			int matches=0;
			ArrayBlockingQueue<Councillor> councillors=counc.getCouncillors();
			for(PoliticsCard card:politics)
				for(Councillor councillor:councillors)
					if(card.payCouncillor(councillor))
					{
						matches++;
						if(card.getClass()==ColoredPoliticsCard.class)
							councillors.remove(councillor);
						continue;
					}
			int cost;
			switch (councillors.size()-matches)
			{
				case 0: cost=0; break;
				case 1: cost=4; break;
				case 2: cost=7; break;
				case 3: cost=10; break;
				default: return false;
			}
			if(!game.getCurrentPlayer().checkCoins(cost))
				return false;
			else
			{
				game.getCurrentPlayer().removeCards(politics);
				return true;
			}
	}

}
