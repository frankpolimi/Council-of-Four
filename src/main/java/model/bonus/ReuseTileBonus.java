/**
 * 
 */
package model.bonus;

import java.util.ArrayList;
import java.util.List;

import controller.BonusChange;
import model.game.BuildingPermit;
import model.game.Game;
import view.*;

/**
 * @author Francesco Vetro'
 *
 */
public class ReuseTileBonus extends ActionBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5580070888752831045L;

	/**
	 * constructor for a bonus that allows the player to obtain a bonus on a permit 
	 * owned by the same player again
	 * @param repeat the times of the bonus can be selected
	 * @throws NullPointerException if repeat is null
	 * @throws IllegalArgumentException if repeat is not greater than zero
	 */
	public ReuseTileBonus(Integer repeat) throws NullPointerException, IllegalArgumentException{
		super(repeat);
	}
	
	
	public void setView(View view) {
		this.registerObserver(view);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	@Override
	public void update(Game game) {
		BonusChange change=new BonusChange(1);
		for(BuildingPermit bp:game.getCurrentPlayer().getAllPermits()){
			change.addPermitBonus(bp);
		}
		game.notifyObserver(game.getCurrentPlayer().getPlayerID(), change);
		
	}
	
	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	@Override
	public void update() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReuseTileBonus"+super.toString();
	}

}
