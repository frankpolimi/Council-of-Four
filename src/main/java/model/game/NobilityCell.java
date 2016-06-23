/**
 * 
 */
package model.game;

import java.util.List;

import model.bonus.*;
import model.bonus.bonusers.Bonusable;

/**
 * @author Francesco Vetro'
 *
 */
public class NobilityCell extends Bonusable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2751737835536189881L;
	private final List<Bonus> bonuses;
	
	/**
	 * This methos is the NobilityCell constructor
	 * @param bonuses is the list of the bonuses contained in that nobility cell
	 * @throws NullPointerException if bonuses list is null
	 */
	public NobilityCell(List<Bonus> bonuses) {
		if(bonuses==null){
			throw new NullPointerException("The bonuses list cannot be null");
		}
		
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
