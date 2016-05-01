/**
 * 
 */
package bonus;

import cg2.view.*;
import cg2.game.*;

/**
 * @author Francesco Vetr�
 *
 */
public class FreeBuildingLicenseBonus extends ActionBonus {
	
	public FreeBuildingLicenseBonus(View view) {
		this.registerObserver(view);
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
