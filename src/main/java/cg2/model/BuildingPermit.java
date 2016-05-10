package cg2.model;
import topology.*;
import java.util.*;

import bonus.*;
import bonus.bonusers.*;

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
		while(i.hasNext()){
			Bonus b = (Bonus) i.next();
			if(b.getClass().equals(AssistantBonus.class))
				x.concat(((AssistantBonus)b).toString());
			else if(b.getClass().equals(CoinBonus.class))
				x.concat(((CoinBonus)b).toString());
			else if(b.getClass().equals(MainActionBonus.class))
				x.concat(((MainActionBonus)b).toString());
			else if(b.getClass().equals(NobilityBonus.class))
				x.concat(((NobilityBonus)b).toString());
			else if(b.getClass().equals(PointBonus.class))
				x.concat(((PointBonus)b).toString());
			x.concat(" ");
		}
		return x;
	}
	
	
	//mancano HashCode, toString
}
