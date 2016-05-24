package parsers;

import politics.PoliticsCard;

public class PlayerPoliticsParser extends Parser 
{
	public PoliticsCard parsePlayerPolitics(String token)
	{
		int n;
		if(token.startsWith("po"))
		{
			n=Integer.parseInt(token.substring(2));
			return model.getPlayerPolitic(0/*model.getCurrentPlayer()*/).get(n-1);
		}
		throw new IllegalArgumentException("Not valid player politics parser command");
	}
}
