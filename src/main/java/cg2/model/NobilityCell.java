/**
 * 
 */
package cg2.model;

import bonus.bonusers.Bonusable;

import java.util.Set;

import bonus.*;

/**
 * @author Francesco Vetrò
 *
 */
public class NobilityCell extends Bonusable {
	
	private final Set<Bonus> bonuses;
	
	public NobilityCell(Set<Bonus> bonuses) {
		this.bonuses = bonuses;
		for(Bonus b: bonuses)
			this.registerBonus(b);
	}

}
