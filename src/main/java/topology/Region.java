package topology;

import java.util.*;
import cg2.model.City;
import council.Council;

/**
 * @author Vitaliy Pakholko
 */
public class Region 
{
	private final String name;
	private ArrayList<City> cities;
	private final Council council;
	
	
	public String getName() 
	{
		return name;
	}
	
	public ArrayList<City> getCities() 
	{
		return cities;
	}

	public Region(String name, ArrayList<City> cities, Council council) 
	{
		this.name = name;
		this.cities = cities;
		this.council = council;
	}

	/**
	 * @return the council
	 */
	public Council getCouncil() {
		return council;
	}
	
}
