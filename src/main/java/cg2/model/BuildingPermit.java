package cg2.model;
import topology.*;
import java.util.*;

import bonus.Bonus;
import bonus.TileBonus;
import bonus.bonusers.Bonusable;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */

public class BuildingPermit extends Bonusable{

	private final Region region;
	private final Set<Character> firstChars;
	private final Set<Bonus> bonusList;
	private final Set<City> buildingAvaliableCities;

	public BuildingPermit(Region region, Set<City> ba, Set<Bonus> bonus){
		this.region=region;
		buildingAvaliableCities=ba;
		firstChars=new HashSet<>();
		this.bonusList = bonus;
		for(City c: buildingAvaliableCities){
			firstChars.add(c.getFirstChar());
		}	
		for(Bonus b: bonus)
			this.registerBonus(b);
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
	public Set<Bonus> getBonusList() {
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
