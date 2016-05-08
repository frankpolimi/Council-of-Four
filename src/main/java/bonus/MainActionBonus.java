/**
 * 
 */
package bonus;

import cg2.game.Game;

/**
 * @author Francesco Vetrò
 *
 */
public class MainActionBonus extends ActionBonus {
	
	public MainActionBonus() {
		super();
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * @param the game instance
	 * increase by 1 the amount of main action that the player can perform
	 */
	@Override
	public <T> void update(T playerOrGame) {
		
		((Game)playerOrGame).setMainActionCounter(
				((Game)playerOrGame).getMainActionCounter()+1);
	}

}
