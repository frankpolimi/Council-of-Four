/**
 * Abstract class to implements bonuses on different
 * tiles of the game.
 */
package model.bonus.bonusers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.game.Game;


/**
 * @author Francesco Vetr�
 * 
 */
public abstract class Bonusable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2029635307372167240L;
	private List<Bonuser> bonuses;
	
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
	//solo d'aiuto.. lo canceller�! by ema
	public void stampBonusList(){
		for(Bonuser b:this.bonuses){
			System.out.println(b.toString());
		}
	}
}
