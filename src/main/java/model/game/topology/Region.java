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
