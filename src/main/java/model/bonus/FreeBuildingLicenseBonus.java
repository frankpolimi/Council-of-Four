/**
 * 
 */
package model.bonus;

import java.util.ArrayList;
import java.util.List;

import controller.PermitsChange;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.topology.Region;
import view.*;

/**
 * @author Francesco Vetr�
 *
 */
public class FreeBuildingLicenseBonus extends ActionBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7459584739431982172L;

	/**
	 * constructor for a bonus to obtain a building permit
	 * @param repeat the amount of building permit that a player can obtain
	 * @throws NullPointerException if repeat is null
	 * @throws IllegalArgumentException if repeat is not greater than zero
	 */
	public FreeBuildingLicenseBonus(Integer repeat) throws NullPointerException, IllegalArgumentException{
		super(repeat);
	}
	
	public void setView(View view) {
		this.registerObserver(view);
	}

	/**
	 * will be update by a change in the model and will get all the face-up
	 * permits from each region and will pass it to the specific view of the 
	 * player whose turn is.
	 * @param game the instance of the game that must collect
	 * the permits to give to the view from which the player must choose
	 */
	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	@Override
	public void update(Game game) {
		
		game.notifyObserver(game.getCurrentPlayer().getPlayerID(), new PermitsChange(game.getAllPermitsDecks()));
		
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
		return "FreeBuildingLicenseBonus"+super.toString();
	}

}
