/**
 * 
 */
package model.bonus;

import controller.ModelChange;
import model.game.Game;
import model.game.NobilityLane;

/**
 * @author Francesco Vetro'
 */
public class NobilityBonus extends TileBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8814654733458365846L;

	/**
	 * constructor for a nobility bonus
	 * @param amount the nobility points given to a player
	 * @throws NullPointerException if amount is null
	 * @throws IllegalArgumentException if amount is not greater than zero
	 */
	public NobilityBonus(Integer amount) throws NullPointerException, IllegalArgumentException {
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
		ModelChange x = new ModelChange(game);
		game.getNobilityLane().update(x);
		this.notifyObservers(x);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Nobility Bonus: "+super.toString();
	}

	@Override
	public void update() {
	}

}
