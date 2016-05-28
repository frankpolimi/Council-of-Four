/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import model.bonus.Bonus;

/**
 * @author Francesco Vetrò
 *
 */
public class BonusChange extends Change {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3134678557158320434L;
	private final List<Bonus> bonusList;
	 
	 public BonusChange() {
		bonusList = new ArrayList<Bonus>();
	}
	
	public BonusChange(List<Bonus> bonusList) {
		this.bonusList = bonusList;
	}

	/**
	 * @return the bonusList
	 */
	public List<Bonus> getBonusList() {
		return bonusList;
	}
	
	public void addBonus(Bonus b){
		bonusList.add(b);
	}

}
