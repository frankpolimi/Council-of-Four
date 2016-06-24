/**
 * 
 */
package model.bonus;

import model.game.Game;

/**
 * @author Francesco Vetro'
 *
 */
public class PointBonus extends TileBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3685807709754032075L;

	/**
	 * constructor for a points bonus
	 * @param amount the victory points given to a player
	 * @throws NullPointerException if amount is null
	 * @throws IllegalArgumentException if amount is not greater than zero
	 */
	public PointBonus(Integer amount) throws NullPointerException, IllegalArgumentException{
		super(amount);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * update the amount of points of the player as parameter
	 * by the amount specified
	 * @param player
	 */
	@Override
	public void update(Game game) {
		game.getCurrentPlayer().setPoints(
				game.getCurrentPlayer().getPoints() + this.getAmount());

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PointBonus: " + super.toString();
	}

	@Override
	public void update() {
	}

}
