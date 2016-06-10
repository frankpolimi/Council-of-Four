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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 11445133450526286L;
	private final List<Bonus> bonusList;
	 
	 public BonusRequest(int iD) {
		super(iD);
		bonusList = new ArrayList<Bonus>();
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
