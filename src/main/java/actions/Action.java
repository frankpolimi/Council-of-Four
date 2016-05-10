package actions;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import cg2.game.Game;
import cg2.player.Player;
import council.Council;
import council.Councillor;
import politics.JollyPoliticsCard;
import politics.PoliticsCard;

public class Action implements Act
{
	Game game;
	
	public boolean takeAction(Player player)
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
			
			int diff=councillors.size()-jollies;
			int coins=player.getStatus().getCoins();
			switch (diff)
			{
				case 0:return true;
				case 1: if(coins>=4){player.getStatus().setCoins(player.getStatus().getCoins()-4); return true; }return false; //Manca tutta la parte di gestione del permesso che deve essere preso
				case 2: if(coins>=7){player.getStatus().setCoins(player.getStatus().getCoins()-7); return true; }return false;// e tolto dal mazzetto ed aggiunto il prossimo
				case 3: if(coins>=10){player.getStatus().setCoins(player.getStatus().getCoins()-10); return true;}return false;
				default: return false;
			}
			
				
	}
}
