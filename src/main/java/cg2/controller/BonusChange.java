/**
 * 
 */
package cg2.controller;

import java.util.List;

import bonus.Bonus;

/**
 * @author Francesco Vetrò
 *
 */
public class BonusChange extends Change {
	
	 private final List<Bonus> bonusList;
	
	public BonusChange(List<Bonus> bonusList) {
		this.bonusList = bonusList;
	}

}
