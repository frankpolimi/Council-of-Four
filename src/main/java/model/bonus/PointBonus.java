/**
 * 
 */
package model.bonus;

import model.game.Game;

/**
 * @author Francesco Vetrò
 *
 */
public class PointBonus extends TileBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3685807709754032075L;

	/**
	 * construct a bonus with the given amount of winning points as parameter
	 * @param amount
	 */
	public PointBonus(Integer amount) {
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
		// TODO Auto-generated method stub
		
	}

}
