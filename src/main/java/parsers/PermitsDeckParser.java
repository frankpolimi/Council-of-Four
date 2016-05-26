package parsers;

import model.game.PermitsDeck;

public class PermitsDeckParser extends Parser
{
	public PermitsDeck parsePermitsDeck(String token)
	{
		if(token.startsWith("pd"))
		{
			n=Integer.parseInt(token.substring(2));
			return model.getRegion().get(n-1).getPermitsDeck();
		}
		throw new IllegalArgumentException("Not valid permits deck parser command");
	}
}
