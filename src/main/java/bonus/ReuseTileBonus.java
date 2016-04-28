/**
 * 
 */
package bonus;

import cg2.controller.Controller;
import cg2.player.*;

/**
 * @author Francesco Vetrò
 *
 */
public class ReuseTileBonus extends ActionBonus {
	
	public ReuseTileBonus(Controller controller) {
		this.registerObserver(controller);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * @param the player
	 * this bonus will get the used building licenses of the player and notify
	 * the controller. the controller will ask the view for input 
	 */
	@Override
	public <T> void update(T playerOrGame) {
		this.notifyObservers(((Player)playerOrGame).getStatus().getUsedBuildingLicenses());
	}

}
