/**
 * 
 */
package bonus;

import cg2.player.*;

/**
 * @author Francesco Vetrò
 */
public class NobilityBonus extends TileBonus {

	/**
	 * construct a bonus with the given amount of nobility points as parameter
	 * @param amount
	 */
	public NobilityBonus(Integer amount) {
		super(amount);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * update the amount of nobility points of the player as parameter
	 * by the amount specified
	 * @param player
	 */
	@Override
	public <T> void update(T playerOrGame) {
		((Player) playerOrGame).getStatus().setNobilityPoints(
				((Player) playerOrGame).getStatus().getNobilityPoints() + this.getAmount());

	}

}
