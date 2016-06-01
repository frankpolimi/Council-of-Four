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
 * @author Francesco Vetr�
 */
public interface Bonuser extends Serializable{
	
	public void update();	
	public void update(Game playerOrGame);

}
