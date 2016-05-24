package parsers;

import cg2.model.City;

public class CityParser extends Parser
{
	public City parseCity(String token)
	{
		token=token.toUpperCase();
		for(City c:model.getCities())
		{
			if(token.equals(c.getFirstChar()))
				return c;
		}
		
		throw new IllegalArgumentException("Not valid city parser command");
	}
}
