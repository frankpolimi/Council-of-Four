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
 * @author Francesco Vetro'
 * 
 */
public abstract class Bonusable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2029635307372167240L;
	private List<Bonuser> bonuses;
	
	/**
	 * constructor for an object that possess some bonuses 
	 */
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
	
	/**
	 * due to different bonuses that are on
	 * an object in the game, this method 
	 * allows to apply all bonuses of the object
	 * to the game
	 * @param game the game which the bonus is applied
	 */
	public void applyBonus(Game game){
		for(Bonuser b: bonuses)
			b.update(game);
	}	
	//solo d'aiuto.. lo cancellero'! by ema
	public void stampBonusList(){
		for(Bonuser b:this.bonuses){
			System.out.println(b.toString());
		}
	}
}
