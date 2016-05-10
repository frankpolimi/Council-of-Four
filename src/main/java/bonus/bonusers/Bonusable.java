/**
 * Abstract class to implements bonuses on different
 * tiles of the game.
 */
package bonus.bonusers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Vetrò
 * 
 */
public class Bonusable {

	List<Bonuser> bonuses;
	
	
	/**
	 * create the list of bonuses
	 */
	public Bonusable(){
		bonuses = new ArrayList<Bonuser>();
	}
	
	/**
	 * add a bonus
	 * @param bonus
	 */
	public void registerBonus(Bonuser bonus){
		this.bonuses.add(bonus);
	}
	
	/**
	 * delete a bonus from list
	 * @param bonus
	 */
	public void unregisterBonus(Bonuser bonus){
		this.bonuses.remove(bonus);
	}
	
	public <T> void applyBonus(T playerorgame){
		for(Bonuser b: bonuses)
			b.update(playerorgame);
	}
}
