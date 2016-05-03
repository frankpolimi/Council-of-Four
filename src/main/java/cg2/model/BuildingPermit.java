package cg2.model;
import topology.*;
import java.util.*;

import bonus.TileBonus;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public class BuildingPermit {
	private final Region region;
	private final Set<Character> firstChars;
	private final Set<TileBonus> bonusList;
	private final Set<City> buildingAvaliableCities;
	
	public BuildingPermit(Region region, Set<City> ba, Set<TileBonus> bonus){
		this.region=region;
		buildingAvaliableCities=ba;
		bonusList=bonus;
		firstChars=new HashSet<>();
		for(City c: buildingAvaliableCities){
			firstChars.add(c.getFirstChar());
		}	
	}

	/**
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * @return the firstChars
	 */
	public Set<Character> getFirstChars() {
		return firstChars;
	}

	/**
	 * @return the bonusList
	 */
	public Set<TileBonus> getBonusList() {
		return bonusList;
	}

	/**
	 * @return the buildingAvaliableCities
	 */
	public Set<City> getBuildingAvaliableCities() {
		return buildingAvaliableCities;
	}
	
	//mancano HashCode, toString
}
