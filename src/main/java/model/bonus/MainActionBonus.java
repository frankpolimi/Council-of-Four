/**
 * 
 */
package model.bonus;

import controller.ModelChange;
import model.game.Game;

/**
 * @author Francesco Vetr�
 *
 */
public class MainActionBonus extends ActionBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4892378733613363361L;

	/**
	 * constructor for a main action bonus
	 * @param repeat the amount of main actions that a player can do 
	 * in addition to others after this bonus
	 * @throws NullPointerException if repeat is null
	 * @throws IllegalArgumentException if repeat is not greater than zero
	 */
	public MainActionBonus(Integer repeat) throws NullPointerException, IllegalArgumentException{
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
		game.notifyObservers(new ModelChange(game));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MainActionBonus [amount=" + amount + "]";
	}

	
}
