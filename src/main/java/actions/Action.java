package actions;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import cg2.game.Game;
import cg2.player.Player;
import council.Council;
import council.Councillor;
import politics.JollyPoliticsCard;
import politics.PoliticsCard;

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
			{
				System.out.println("Not enough coins to pay the council. For 1 missing politics card you pay 4 additional coins, for each additional missing politics card you add 3 more");
				return false;
			}
			else
			{
				player.removeCards(politics);
				return true;
			}
	}

}
