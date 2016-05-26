package parsers;

import model.game.politics.PoliticsCard;

public class PlayerPoliticsParser extends Parser 
{
	public PoliticsCard parsePlayerPolitics(String token)
	{
		if(token.startsWith("po"))
		{
			n=Integer.parseInt(token.substring(2));
			return model.getPlayerPolitic(model.getCurrentPlayer().getPlayerID()).get(n-1);
		}
		throw new IllegalArgumentException("Not valid player politics parser command");
	}
}
