package model.actions;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import model.game.Game;
import model.game.Player;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.politics.JollyPoliticsCard;
import model.game.politics.PoliticsCard;

/**
 * @author Vitaliy Pakholko
 */
public class Action implements Act
{
	
	/**
	 * Super class action's take action method.
	 */
	@Override
	public boolean takeAction(Game game)
	{
		return false;
	}
	
	@Override
	public boolean checkAction(Game game) 
	{
		return false;
	}
	
	public boolean payCouncil(Player player, Council counc, ArrayList<PoliticsCard> politics)
	{

			int jollies=0;
			ArrayBlockingQueue<Councillor> councillors=counc.getCouncillors();
			for(PoliticsCard card:politics)
			{
				if(card.getClass()== JollyPoliticsCard.class)
				{
					jollies++;
					continue;
				}
				
				for(Councillor councillor:councillors)
				{
					if(card.payCouncillor(councillor))
					{
						councillors.remove(councillor);
						continue;
					}
				}
			}
			int cost;
			switch (councillors.size()-jollies)
			{
				case 0: cost=0; break;
				case 1: cost=4; break;
				case 2: cost=7; break;
				case 3: cost=10; break;
				default: return false;
			}
			if(!player.checkCoins(cost))
				return false;
			else
			{
				player.removeCards(politics);
				return true;
			}
	}

}
