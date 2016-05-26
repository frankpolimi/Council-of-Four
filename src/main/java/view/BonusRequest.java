/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.List;

import model.bonus.Bonus;

/**
 * @author francesco
 *
 */
public class BonusRequest extends Request {
	
	private final List<Bonus> bonusList;
	 
	 public BonusRequest() {
		bonusList = new ArrayList<Bonus>();
	}
	
	public BonusRequest(List<Bonus> bonusList) {
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
