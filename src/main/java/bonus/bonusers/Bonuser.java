/**
 * Interface for bonuses.
 * Provides methods for updating:
 * - player status 
 * - how player interacts with the game
 */
package bonus.bonusers;

/**
 * @author Francesco Vetrò
 */
public interface Bonuser {
	
	public void update();	
	public <T> void update(T playerOrGame);

}
