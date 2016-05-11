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
		for(Bonus b: bonuses)
			this.registerBonus(b);
	}

}
