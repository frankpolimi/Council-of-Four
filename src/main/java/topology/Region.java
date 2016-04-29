package topology;

import java.util.*;
import cg2.model.City;

/**
 * @author Vitaliy Pakholko
 */
public class Region 
{
	private final String name;
	private ArrayList<City> cities;
	
	
	public String getName() 
	{
		return name;
	}
	
	public ArrayList<City> getCities() 
	{
		return cities;
	}

	public Region(String name, ArrayList<City> cities) 
	{
		this.name = name;
		this.cities = cities;
	}
	
}
