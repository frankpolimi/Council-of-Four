/**
 * 
 */
package model.bonus;

import controller.ModelChange;
import model.game.Game;
import model.game.NobilityLane;

/**
 * @author Francesco Vetr�
 */
public class NobilityBonus extends TileBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8814654733458365846L;

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
		this.notifyObservers(new ModelChange(game));
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
