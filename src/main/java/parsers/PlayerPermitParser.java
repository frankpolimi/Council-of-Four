package parsers;

import cg2.model.BuildingPermit;

public class PlayerPermitParser extends Parser
{
	public BuildingPermit parsePlayerPermit(String token)
	{
		if(token.startsWith("pe"))
		{
			n=Integer.parseInt(token.substring(2));
			return model.getPlayerPermit(0/*model.getCurrentPlayer*/).get(n-1);
		}
		throw new IllegalArgumentException("Not valid player permit parser command");
	}

}
