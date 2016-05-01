/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.List;

import cg2.view.*;
import cg2.player.*;
import cg2.model.BuildingLicense;;

/**
 * @author Francesco Vetr�
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
	 * this bonus will get the building licenses of the player and notify
	 * the view. the view will manage the input for selecting the tile
	 * the player wants to earn its bonus again 
	 */
	@Override
	public <T> void update(T playerOrGame) {
		List<BuildingLicense> total = new ArrayList<BuildingLicense>();
		total.addAll(
				((Player)playerOrGame).getStatus().getBuildingLicensesObtained());
		total.addAll(
				((Player)playerOrGame).getStatus().getUsedBuildingLicenses());
		this.notifyObservers(total);
	}

}
