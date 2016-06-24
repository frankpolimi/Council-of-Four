/**
 * Interface for bonuses.
 * Provides methods for updating:
 * - player status 
 * - how player interacts with the game
 */
package model.bonus.bonusers;

import java.io.Serializable;

import model.game.Game;

/**
 * @author Francesco Vetro'
 * interface representing an application of the pattern
 * observer-observable applied to the bonuses in the game
 * this means that an object implementing this interface
 * will be triggered when a specific object is activated
 */
public interface Bonuser extends Serializable{
	
	public void update();	
	public void update(Game playerOrGame);

}
