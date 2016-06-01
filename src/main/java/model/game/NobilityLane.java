/**
 * 
 */
package model.game;

import java.util.HashMap;
import java.util.Map;
import controller.Change;
import controller.ModelChange;
import model.bonus.bonusers.Bonusable;
import model.observers.Observer;

/**
 * @author Francesco Vetr�
 *
 */
public class NobilityLane extends Bonusable implements Observer<Change>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9161958522145141177L;
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
	public void update(Change change) {
		ModelChange modelChange = (ModelChange)change;
		int place = modelChange.getGame().getCurrentPlayer().getNobilityPoints();
		lane.get(place).applyBonus(modelChange.getGame());
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NobilityLane [lane=" + lane + "]";
	}
	
}
