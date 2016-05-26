/**
 * 
 */
package view;

import java.util.List;

import controller.BonusChange;
import controller.Change;
import controller.PermitsChange;
import model.bonus.Bonus;
import model.game.BuildingPermit;

/**
 * @author Francesco Vetr�
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