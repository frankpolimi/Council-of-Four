/**
 * Abstract class to implements bonuses on different
 * tiles of the game.
 */
package bonus.bonusers;

import java.util.ArrayList;
import java.util.List;

import cg2.game.Game;


/**
 * @author Francesco Vetṛ
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
		this.bonuses.add(bonus);
	}
	
	/**
	 * delete a bonus from list
	 * @param bonus
	 */
	public void unregisterBonus(Bonuser bonus){
		this.bonuses.remove(bonus);
	}
	
	public void applyBonus(Game game){
		for(Bonuser b: bonuses)
			b.update(game);
	}	
	//solo d'aiuto.. lo cancelleṛ! by ema
	public void stampBonusList(){
		for(Bonuser b:this.bonuses){
			System.out.println(b.toString());
		}
	}
}
