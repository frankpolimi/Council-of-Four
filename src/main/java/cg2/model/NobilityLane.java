/**
 * 
 */
package cg2.model;

import java.util.ArrayList;
import java.util.List;

import bonus.bonusers.Bonusable;
import cg2.observers.Observer;
import cg2.player.*;

/**
 * @author Francesco Vetr�
 *
 */
public class NobilityLane extends Bonusable implements Observer{
	
	List<NobilityCell> lane;
	
	public NobilityLane(){
		lane = new ArrayList<NobilityCell>();
	}
	
	/**
	 * the list of bonuses can even be null
	 * @param bonus
	 */
	public void setLane(NobilityCell bonuses){
		lane.add(bonuses);
	}

	@Override
	public void update() {
	}

	/**
	 * @param the player (generic)
	 * get the amounts of nobility points of the player
	 * and apply the bonuses on the specific place
	 * the bonuses can be null
	 */
	@Override
	public <C> void update(C change) {
		int place = ((Player)change).getStatus().getNobilityPoints();
		lane.get(place).applyBonus(change);
	}

	@Override
	public void update(String communication) {
		// TODO Auto-generated method stub
		
	}
}
