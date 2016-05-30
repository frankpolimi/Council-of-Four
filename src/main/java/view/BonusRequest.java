/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.List;

import model.bonus.Bonus;
import model.game.Player;

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
	 
	 public BonusRequest(Player player) {
		super(player);
		bonusList = new ArrayList<Bonus>();
	}
	
	public BonusRequest(List<Bonus> bonusList, Player player) {
		super(player);
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
