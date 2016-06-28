/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import model.game.BuildingPermit;
import model.game.topology.City;

/**
 * @author Francesco Vetro'
 * 
 * this class represents the message that a client
 * receive only when a specific bonus on the nobility track 
 * is triggered. 
 */
public class BonusChange extends Change {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3134678557158320434L;
	private final List<Object> bonusList;
	private final int repeat;
	
	/**
	 * default constructor for a bonus change
	 * allows the player to select a repeat amount of times 
	 * a bonus that is stored in the bonusList. each repetition the bonus chosen before
	 * is removed from the list
	 * @param repeat the amount of times that a bonus can be selected
	 */
	public BonusChange(int repeat) {
		bonusList = new ArrayList<>();
		this.repeat=repeat;
	}

	/**
	 * constructor of the message to send to the client
	 * to select a bonus to acquire
	 * @param bonusList the list of bonuses to send to the client
	 */
	public BonusChange(List<Object> bonusList, int repeat){
		this.bonusList = new ArrayList<>(bonusList);
		this.repeat=repeat;
	}

	
	
	/**
	 * @return the amount of times the bonus can be repeated
	 */
	public int getRepeat() {
		return repeat;
	}

	/**
	 * @return the bonusList
	 */
	public List<Object> getBonusList() {
		return bonusList;
	}
	
	/**
	 * method to add a bonus to the list of bonuses available 
	 * for the client
	 * @param b the bonus to add
	 */
	public void addPermitBonus(BuildingPermit b){
		bonusList.add(b);
	}
	
	/**
	 * method to add a bonus to the list of bonuses available 
	 * for the client
	 * @param c the bonus to add
	 */
	public void addCityBonus(City c){
		bonusList.add(c);
	}

}
