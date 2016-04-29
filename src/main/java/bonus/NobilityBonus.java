/**
 * 
 */
package bonus;

import cg2.model.NobilityLane;
import cg2.player.*;

/**
 * @author Francesco Vetr�
 */
public class NobilityBonus extends TileBonus {

	/**
	 * construct a bonus with the given amount of nobility points as parameter
	 * @param amount
	 */
	public NobilityBonus(Integer amount, NobilityLane nobilityLane) {
		super(amount);
		this.registerObserver(nobilityLane);
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
		Status s = ((Player)playerOrGame).getStatus();
		s.setNobilityPoints(
				s.getNobilityPoints() + this.getAmount());
		this.notifyObservers((Player)playerOrGame);
	}

}
