package cg2.model;
import java.util.*;

import bonus.TileBonus;
import topology.Region;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public class PermessoCostruzione {
	private final Region region;
	private final Set<Character> firstChars;
	private final Set<TileBonus> bonusList;
	private final Set<Citta> buildingAvaliableCities;
	
	public PermessoCostruzione(Region region, Set<Citta> ba, Set<TileBonus> bonus){
		this.region=region;
		buildingAvaliableCities=ba;
		bonusList=bonus;
		firstChars=new HashSet<Character>();
		for(Citta c: buildingAvaliableCities){
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
	public Set<Citta> getBuildingAvaliableCities() {
		return buildingAvaliableCities;
	}
	
	//mancano HashCode, toString
}
