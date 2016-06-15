package model.game;

import java.io.Serializable;
import java.util.*;

import model.bonus.*;
import model.bonus.bonusers.*;
import model.game.topology.City;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */

public class BuildingPermit extends Bonusable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8442720264371098415L;
	private final Set<Character> firstChars;
	private final Set<Bonus> bonusList;
	private final Set<City> buildingAvaliableCities;
	private String imagePath;

	
	/**
	 * This method is the BuildingPermit constructor
	 * @param ba is a set of cities in which the owner of this permit could build an emporium
	 * @param bonus is a set of bonus applied on the owner.
	 * @throws NullPointerException if ba or bonus is null
	 * @throws IllegalArgumentException if ba or bonus is empty.
	 */
	public BuildingPermit(Set<City> ba, Set<Bonus> bonus, String imagePath){
		if(ba==null||bonus==null){
			throw new NullPointerException("One either buildingAvaliableCities or bonusList is null");
		}
		
		if(ba.isEmpty()||bonus.isEmpty()){
			throw new IllegalArgumentException("The parameters cannot be empty");
		}
		
		this.imagePath=imagePath;
		buildingAvaliableCities=ba;
		firstChars=new HashSet<>();
		this.bonusList = bonus;
		for(City c: buildingAvaliableCities){
			firstChars.add(c.getFirstChar());
		}	
		for(Bonus b: bonus)
			this.registerBonus(b);
	}
	
	public BuildingPermit(Set<City> ba, Set<Bonus> bonus){
		if(ba==null||bonus==null){
			throw new NullPointerException("One either buildingAvaliableCities or bonusList is null");
		}
		
		if(ba.isEmpty()||bonus.isEmpty()){
			throw new IllegalArgumentException("The parameters cannot be empty");
		}

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
		String message="Building Permit --> "
				+ "buildingAvaliableCities: ";
		for(City c:this.getBuildingAvaliableCities()){
			message=message.concat(c.getName()+", ");
		}
		return message.concat("\nBonus\n"+this.getBonusList()+"\n");
	}

	/**
	 * @author Francesco vetr�
	 * @return the bonuses applied to the permit
	 */
	public String displayBonus() {
		Iterator<Bonus> i = bonusList.iterator();
		String x = "";
		while(i.hasNext())
			x.concat(i.next().toString()+" ");
		return x;
	}

}
