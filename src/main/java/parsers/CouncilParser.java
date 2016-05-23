package parsers;

import cg2.view.PeekModel;
import council.Council;

public class CouncilParser 
{
	private PeekModel model;
	
	public Council parseCouncil(String inputToken)
	{
		int n=0;
		if(!inputToken.equals("kc"))
		{
			if(inputToken.startsWith("c"))
			{
				n=Integer.parseInt(inputToken.substring(1));
				inputToken="c";
			}
		}
		switch(inputToken)
		{
			case "kc": return model.getKingCouncil();
			case "c": return model.getRegion().get(n).getCouncil();
		}
		
		throw new IllegalArgumentException("Not valid parser command");
	}
}
