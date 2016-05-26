/**
 * Interface for bonuses.
 * Provides methods for updating:
 * - player status 
 * - how player interacts with the game
 */
package model.bonus.bonusers;

import model.game.Game;

/**
 * @author Francesco Vetrò
 */
public interface Bonuser {
	
	public void update();	
	public void update(Game playerOrGame);

}
