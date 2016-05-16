/**
 * 
 */
package cg2.model;

import java.util.HashMap;
import java.util.Map;

import bonus.bonusers.Bonusable;
import cg2.game.Game;
import cg2.observers.Observer;

/**
 * @author Francesco Vetrò
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
		Game x = (Game)change;
		int place = x.getCurrentPlayer().getNobilityPoints();
		lane.get(place).applyBonus(x);
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
