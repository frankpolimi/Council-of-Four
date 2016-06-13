/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import model.bonus.Bonus;

/**
 * @author Francesco Vetr�
 *
 */
public class BonusChange extends Change {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3134678557158320434L;
	private final List<Bonus> bonusList;
	
	/**
	 * default constructor
	 */
	public BonusChange() {
		bonusList = new ArrayList<Bonus>();
	}

	/**
	 * constructor of the message to send to the client
	 * to select a bonus to acquire
	 * @param bonusList the list of bonuses to send to the client
	 */
	public BonusChange(List<Bonus> bonusList){
		this.bonusList = new ArrayList<>(bonusList);
	}

	/**
	 * @return the bonusList
	 */
	public List<Bonus> getBonusList() {
		return bonusList;
	}
	
	/**
	 * method to add a bonus to the list of bonuses available 
	 * for the client
	 * @param b the bonus to add
	 */
	public void addBonus(Bonus b){
		bonusList.add(b);
	}

}
