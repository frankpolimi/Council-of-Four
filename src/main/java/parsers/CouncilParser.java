package parsers;

import cg2.view.PeekModel;
import council.Council;

public class CouncilParser extends Parser
{
	
	public Council parseCouncil(String token)
	{
		int n=0;
		if(!token.equals("kc"))
		{
			if(token.startsWith("co"))
			{
				n=Integer.parseInt(token.substring(2));
				return model.getRegion().get(n-1).getCouncil();
			}
		}
		else return model.getKingCouncil();
		
		throw new IllegalArgumentException("Not valid council parser command");
	}
}
