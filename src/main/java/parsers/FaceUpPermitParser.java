package parsers;

import java.util.Iterator;
import cg2.model.BuildingPermit;

public class FaceUpPermitParser extends Parser 
{
	public BuildingPermit faceUpParser(String token)
	{
		if(token.startsWith("bp"))
		{
			int i=0;
			n=Integer.parseInt(token.substring(2));
			int region=n/(model.getRegion().get(0).getPermitsDeck().getFaceUpPermits().size());
			int permit=n%(model.getRegion().get(0).getPermitsDeck().getFaceUpPermits().size());
			Iterator<BuildingPermit> iterator=model.getRegion().get(region).getPermitsDeck().getFaceUpPermits().iterator();
			while(iterator.hasNext())
			{
				if(i==permit)
					return iterator.next();
			}
			
		}
		throw new IllegalArgumentException("Not valid face up permits parser command");
	}
}
