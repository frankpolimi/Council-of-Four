package topology;

import java.util.*;
import cg2.model.City;
import cg2.model.PermitsDeck;
import council.RegionalCouncil;

/**
 * @author Vitaliy Pakholko
 */
public class Region 
{
	private final String name;
	private ArrayList<City> cities;
	private final RegionalCouncil council;
	private final PermitsDeck permitsDeck;
	
	
	public String getName() 
	{
		return name;
	}
	
	public ArrayList<City> getCities() 
	{
		return cities;
	}

	public Region(String name, ArrayList<City> cities, RegionalCouncil council, PermitsDeck permitsDeck) 
	{
		this.name = name;
		this.cities = cities;
		this.council = council;
		this.permitsDeck= permitsDeck;
	}

	/**
	 * @return the council
	 */
	public RegionalCouncil getCouncil() {
		return council;
	}

	/**
	 * @return the permitsDeck
	 */
	public PermitsDeck getPermitsDeck() {
		return permitsDeck;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Region [name=" + name + ", cities=" + cities + "]\n";
	}
	
	
	
	
	
}
