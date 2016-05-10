package topology;

import java.util.*;
import cg2.model.City;
import cg2.model.PermitsDeck;
import council.Council;

/**
 * @author Vitaliy Pakholko
 */
public class Region 
{
	private final String name;
	private ArrayList<City> cities;
	private final Council council;
	private final PermitsDeck permitsDeck;
	
	
	public String getName() 
	{
		return name;
	}
	
	public ArrayList<City> getCities() 
	{
		return cities;
	}

	public Region(String name, ArrayList<City> cities, Council council, PermitsDeck permitsDeck) 
	{
		this.name = name;
		this.cities = cities;
		this.council = council;
		this.permitsDeck= permitsDeck;
	}

	/**
	 * @return the council
	 */
	public Council getCouncil() {
		return council;
	}

	/**
	 * @return the permitsDeck
	 */
	public PermitsDeck getPermitsDeck() {
		return permitsDeck;
	}
	
	
	
}
