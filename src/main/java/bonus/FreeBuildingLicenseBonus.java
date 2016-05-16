/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.List;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.view.*;
import topology.Region;

/**
 * @author Francesco Vetrò
 *
 */
public class FreeBuildingLicenseBonus extends ActionBonus {
	
	public FreeBuildingLicenseBonus(Integer repeat) {
		super(repeat);
	}
	
	public void setView(View view) {
		this.registerObserver(view);
	}

	/**
	 * will be update by a change in the model and will get all the face-up
	 * permits from each region and will pass it to the specific view of the 
	 * player whose turn is.
	 * @param playerOrGame the instance of the game that must collect
	 * the permits to give to the view from which the player must choose
	 */
	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	@Override
	public void update(Game game) {
		List<BuildingPermit> permits = new ArrayList<BuildingPermit>();
		for(int i=0; i< this.amount;i++){
			for(Region r : game.getRegions())
				permits.addAll(r.getPermitsDeck().getFaceUpPermits());
			this.notifyObserver(game.getCurrentPlayer().getPlayerID(), permits);
		}
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
