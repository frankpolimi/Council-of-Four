/**
 * 
 */
package bonus;

import cg2.player.*;

/**
 * @author 7ev
 *
 */
public class PointBonus extends TileBonus {
	
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
	public <T> void update(T playerOrGame) {
		((Player) playerOrGame).getStatus().setPoints(
				((Player) playerOrGame).getStatus().getPoints() + this.getAmount());

	}

}
