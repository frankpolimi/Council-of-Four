package model.game.topology;

import java.io.Serializable;
import java.util.*;

import model.game.PermitsDeck;
import model.game.council.RegionalCouncil;

/**
 * @author Vitaliy Pakholko
 */
public class Region implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5260064143011887252L;
	private final String name;
	private ArrayList<City> cities;
	private final RegionalCouncil council;
	private final PermitsDeck permitsDeck;
	private String imagePath;
	private char type;
	
	/**
	 * constructor for a region of the game
	 * @param name the name of the region
	 * @param cities the cities that belongs to the region
	 * @param council the council of the region
	 * @param permitsDeck all the permits available for this region
	 * @param path the path for the image to load
	 * @param type the kind of map that refers to due to configuration
	 */
	public Region(String name, ArrayList<City> cities, RegionalCouncil council, PermitsDeck permitsDeck, String path, char type) 
	{
		this.name = name;
		this.cities = cities;
		this.council = council;
		this.permitsDeck= permitsDeck;
		this.imagePath=path;
		this.type=type;
	}
	
	/**
	 * constructor for a region of the game
	 * @param name the name of the region
	 * @param cities the cities that belongs to the region
	 * @param council the council of the region
	 * @param permitsDeck all the permits available for this region
	 */
	public Region(String name, ArrayList<City> cities, RegionalCouncil council, PermitsDeck permitsDeck) 
	{
		this.name = name;
		this.cities = cities;
		this.council = council;
		this.permitsDeck= permitsDeck;
	}
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @return the name of the region
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * @return the list of cities in the region
	 */
	public ArrayList<City> getCities() 
	{
		return cities;
	}
	
	/**
	 * @return the type of configuration
	 */
	public char getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Region other = (Region) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
		return "Region " + name + "\n cities=" + cities + "\n" + name+" council\n"+
				council.getCouncillors().toString() + "]\n\n";
	}
	
	
	
	
	
}
