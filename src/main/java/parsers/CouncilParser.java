package parsers;

import cg2.view.PeekModel;
import council.Council;

public class CouncilParser 
{
	private PeekModel model;
	
	public Council parseCouncil(String inputToken)
	{
		if(!inputToken.equals("kc"))
		{
			int n=Integer.parseInt(inputToken.substring(1));
		}
		switch(inputToken)
		{
			case "kc": return model.getKingCouncil();
			case "c"+Integer.parseInt(inputToken): break;
		}
	}
}
