/**
 * 
 */
package bonus;

import cg2.game.Game;
import cg2.model.NobilityLane;

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
	
	public void setNobilityLane(NobilityLane nobilityLane){
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
	public void update(Game game) {
		game.getCurrentPlayer().setNobilityPoints(
				game.getCurrentPlayer().getNobilityPoints() + this.getAmount());
		this.notifyObservers(game);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NobilityBonus: "+super.toString();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
