/**
 * Interface for bonuses.
 * Provides methods for updating:
 * - player status 
 * - how player interacts with the game
 */
package bonus.bonusers;

/**
 * @author Francesco Vetr�
 */
public interface Bonuser {
		
	public <T> void update(T playerorgame);

}
