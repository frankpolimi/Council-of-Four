package actions;

import java.util.ArrayList;

import cg2.game.Game;
import cg2.player.Player;
import council.Council;
import council.Councillor;
import council.RegionalCouncil;
import politics.JollyPoliticsCard;
import politics.PoliticsCard;

public class Action implements Act
{
	Game game;
	
	public void takeAction(Player player){}
	
	public boolean payCouncil(Player player, Council counc, ArrayList<PoliticsCard> politics)
	{

			int jollies=0;
			Council council=new RegionalCouncil();
			council.setCouncillors(counc.getCouncillors()); //Oppure Council council=(Council)permitsDeck.getCouncil().clone(); ma e' brutto
			for(PoliticsCard card:politics) //Tra l'altro sta roba non posso manco farla
			{
				if(card instanceof JollyPoliticsCard)
				{
					jollies++;
					continue;
				}
				
				for(Councillor councillor:council.getCouncillors())
				{
					if(card.payCouncillor(councillor))
					{
						council.getCouncillors().remove(councillor);
						continue;
					}
				}
			}
			
			int diff=council.getCouncillors().size()-jollies;
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
