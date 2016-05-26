package model.game;

import java.util.*;

import model.bonus.*;
import model.bonus.bonusers.*;
import model.game.topology.City;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */

public class BuildingPermit extends Bonusable{

	private final Set<Character> firstChars;
	private final Set<Bonus> bonusList;
	private final Set<City> buildingAvaliableCities;

	public BuildingPermit(Set<City> ba, Set<Bonus> bonus){
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

	@Override
	public String toString() {
		return "BuildingPermit [buildingAvaliableCities=" + buildingAvaliableCities + "]";
	}

	/**
	 * @author Francesco vetrò
	 * @return the bonuses applied to the permit
	 */
	public String displayBonus() {
		Iterator<Bonus> i = bonusList.iterator();
		String x = "";
		while(i.hasNext())
			x.concat(i.next().toString()+" ");
		return x;
	}
	
	
	//mancano HashCode, toString
}
