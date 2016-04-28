/**
 * 
 */
package cg2.model;

import java.util.ArrayList;
import java.util.List;

import bonus.Bonus;
import bonus.bonusers.Bonusable;

/**
 * @author Francesco Vetrò
 *
 */
public class NobilityLane extends Bonusable{
	
	List<ArrayList<Bonus>> lane;
	
	public NobilityLane(){
		lane = new ArrayList<ArrayList<Bonus>>();
	}
	
	/**
	 * the list of bonuses can even be null
	 * @param bonus
	 */
	public void setLane(ArrayList<Bonus> bonus){
		lane.add(bonus);
	}

}
