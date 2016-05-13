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
public abstract class Bonusable {

	List<Bonuser> bonuses;
	
	public Bonusable(){
		bonuses=new ArrayList<>();
	}
	
	/**
	 * add a bonus
	 * @param bonus
	 */
	public void registerBonus(Bonuser bonus){
		System.out.println(bonus.toString());
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
