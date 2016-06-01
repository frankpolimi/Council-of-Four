/**
 * 
 */
package model.game;

import java.util.List;

import model.bonus.*;
import model.bonus.bonusers.Bonusable;

/**
 * @author Francesco Vetr�
 *
 */
public class NobilityCell extends Bonusable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2751737835536189881L;
	private final List<Bonus> bonuses;
	
	public NobilityCell(List<Bonus> bonuses) {
		this.bonuses = bonuses;
		for(Bonus b: bonuses) {
			this.registerBonus(b);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NobilityCell [bonuses=" + bonuses + "]";
	}

	
}
