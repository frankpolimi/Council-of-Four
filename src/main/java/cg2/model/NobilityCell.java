/**
 * 
 */
package cg2.model;

import bonus.bonusers.Bonusable;

import java.util.List;

import bonus.*;

/**
 * @author Francesco Vetrò
 *
 */
public class NobilityCell extends Bonusable {
	
	private final List<Bonus> bonuses;
	
	public NobilityCell(List<Bonus> bonuses) {
		this.bonuses = bonuses;
		for(Bonus b: bonuses) {
			System.out.println("Stampo bonus"+b.toString());
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
