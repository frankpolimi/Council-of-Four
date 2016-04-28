/**
 * 
 */
package bonus;

import cg2.player.*;
import cg2.view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class ReuseTileBonus extends ActionBonus {
	
	public ReuseTileBonus(View view) {
		this.registerObserver(view);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * @param the player
	 * this bonus will get the used building licenses of the player and notify
	 * the view for a choice. will there be a rule?? 
	 */
	@Override
	public <T> void update(T playerOrGame) {
		
		//this.notifyObservers(((Player)playerOrGame).getStatus().getUsedBuildingLicense());
		//this.notifyObservers("Scegli di quale permesso vuoi riusare il bonus");
		
	}

}
