/**
 * 
 */
package cg2.view;

import java.util.List;

import bonus.Bonus;
import cg2.controller.BonusChange;
import cg2.controller.Change;
import cg2.controller.PermitsChange;
import cg2.model.BuildingPermit;

/**
 * @author Francesco Vetrò
 *
 */
public class LocalStorage {
	
	private List<Bonus> bonus;
	private List<BuildingPermit> permits;
	
	public LocalStorage(Change list) {
		if(Change.class.equals(BonusChange.class))
			bonus = ((BonusChange)list).getBonusList();
		else if(Change.class.equals(PermitsChange.class))
			permits = ((PermitsChange)list).getPermits();
	}
	
	public Bonus retrieveBonus(int place){
		return bonus.get(place);
	}
	
	public BuildingPermit retrievePermit(int place){
		return permits.get(place);
	}
	
	public int getBonusLenght(){
		return bonus.size();
	}
	
	public int getPermitsLenght(){
		return permits.size();
	}

}
