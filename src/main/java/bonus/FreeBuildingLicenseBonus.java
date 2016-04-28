/**
 * 
 */
package bonus;

import cg2.game.*;
import cg2.view.View;

/**
 * @author Francesco Vetrò
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
	 * don't know what to notify
	 * give the set of regions or just the output "pick a region" and
	 * then the controller should provide all things??
	 */
	@Override
	public <T> void update(T playerOrGame) {
		
		//this.notifyObservers(((Game)playerOrGame).getRegions());
		//this.notifyObservers("Scegli la regione di cui vuoi prendere un permesso");
	}

}
