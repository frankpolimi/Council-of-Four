/**
 * 
 */
package model.bonus;

import model.game.Game;

/**
 * @author Francesco Vetrò
 *
 */
public class MainActionBonus extends ActionBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4892378733613363361L;

	public MainActionBonus(Integer repeat) {
		super(repeat);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * @param the game instance
	 * increase by 1 the amount of main action that the player can perform
	 */
	@Override
	public void update(Game game) {
		
		game.incrementMainActionCounter();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MainActionBonus [amount=" + amount + "]";
	}

	
}
