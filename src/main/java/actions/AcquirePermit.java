package actions;

import java.util.ArrayList;
import politics.*;

import cg2.model.BuildingPermit;
import cg2.model.PermitsDeck;
import cg2.player.Player;
import council.Council;
import council.Councillor;
import council.RegionalCouncil;
import politics.ColoredPoliticsCard;
import politics.PoliticsCard;

public class AcquirePermit extends MainAction 
{


	/*public void takeAction(Player player, PermitsDeck permitsDeck, BuildingPermit buildingPermit, ArrayList<PoliticsCard> politics)
	{
		if(permitsDeck.getFaceUpPermits().contains(buildingPermit))
		{
			int jollies=0;
			Council council=new RegionalCouncil();
			council.setCouncillors(permitsDeck.getCouncil().getCouncillors()); //Oppure Council council=(Council)permitsDeck.getCouncil().clone(); ma e' brutto
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
				case 0:return;
				case 1: if(coins>=4)player.getStatus().setCoins(player.getStatus().getCoins()-4); break; //Manca tutta la parte di gestione del permesso che deve essere preso
				case 2: if(coins>=7)player.getStatus().setCoins(player.getStatus().getCoins()-7); break; // e tolto dal mazzetto ed aggiunto il prossimo
				case 3: if(coins>=10)player.getStatus().setCoins(player.getStatus().getCoins()-10); break;
			}
			
				
		}
	}*/
}