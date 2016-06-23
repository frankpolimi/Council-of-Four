/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.List;

import model.bonus.Bonus;

/**
 * @author Francesco Vetro'
 *
 */
public class BonusRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 11445133450526286L;
	private final List<Bonus> bonusList;
	 
	/**
	 * constructor for the class that will be sent to the
	 * controller. the class's field is the list of bonuses
	 * that the player has decided to acquire when he receives
	 * a bonus change.
	 * @param iD the ID of the player
	 */
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
	
	/**
	 * add the bonus selected by the player to the list
	 * as field of this class
	 * @param b the bonus to add
	 */
	public void addBonus(Bonus b){
		bonusList.add(b);
	}


}
