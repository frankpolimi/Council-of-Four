/**
 * 
 */
package bonus;

import cg2.controller.Controller;
import cg2.game.*;

/**
 * @author Francesco Vetrò
 *
 */
public class FreeBuildingLicenseBonus extends ActionBonus {
	
	public FreeBuildingLicenseBonus(Controller controller) {
		this.registerObserver(controller);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * @param the game
	 * gives to the controller the region from where to choose
	 * the controller will notify the view for the input
	 */
	@Override
	public <T> void update(T playerOrGame) {
		this.notifyObservers(((Game)playerOrGame).getRegions());
	}

}
