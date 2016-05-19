/**
 * 
 */
package cg2.controller;

import java.util.List;

import bonus.Bonus;

/**
 * @author Francesco Vetr�
 *
 */
public class BonusChange extends Change {
	
	 private final List<Bonus> bonusList;
	
	public BonusChange(List<Bonus> bonusList) {
		this.bonusList = bonusList;
	}

	/**
	 * @return the bonusList
	 */
	public List<Bonus> getBonusList() {
		return bonusList;
	}

}
