package actions;

import java.util.ArrayList;

import cg2.model.BuildingLicense;
import cg2.model.LicenseDeck;
import council.Council;
import council.Councillor;
import politics.ColoredPoliticsCard;
import politics.PoliticsCard;

public class AcquirePermit extends MainAction 
{
	public BuildingLicense /*BuildingPermit*/ takeAction(LicenseDeck/*PermitsDeck*/ permitsDeck, BuildingLicense/*BuildingPermit*/ buildingPermit, ArrayList<PoliticsCard> politics)
	{
		if(permitsDeck.faceUpPermits.contains(buildingPermit))
		{
			Council council=new Council();
			council.setCouncillors(permitsDeck.getCouncil().getCouncillors());
			for(ColoredPoliticsCard card:politics) //Tra l'altro sta roba non posso manco farla
			{
				for(Councillor councillor:council.getCouncillors())
				{
					if(card.payCouncillor(councillor)
					{
						council.getCouncillors().remove(councillor);
						break;
					}
				}
			}
			for(JollyPoliticsCard j:politics)
		}
	}
}
