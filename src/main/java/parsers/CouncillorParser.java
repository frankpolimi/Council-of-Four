package parsers;

import council.Councillor;

public class CouncillorParser extends Parser
{
	public Councillor parseCouncillor(String token)
	{
		int n;
		if(token.startsWith("cg"))
		{
			n=Integer.parseInt(token.substring(2));
			return model.getAvailableCouncillor().get(n-1);
		}
		throw new IllegalArgumentException("Not valid councillor parser command");
	}
}
