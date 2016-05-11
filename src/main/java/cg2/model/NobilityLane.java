/**
 * 
 */
package cg2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bonus.bonusers.Bonusable;
import cg2.observers.Observer;
import cg2.player.*;

/**
 * @author Francesco Vetr�
 *
 */
public class NobilityLane extends Bonusable implements Observer{
	
	Map<Integer, NobilityCell> lane;
	
	public NobilityLane(){
		lane = new HashMap<>();
	}
	
	/**
	 * the list of bonuses can even be null
	 * @param bonus
	 */
	public void setLane(int pos, NobilityCell bonuses){
		lane.put(pos, bonuses);
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
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NobilityLane [lane=" + lane + "]";
	}
	
	
}
