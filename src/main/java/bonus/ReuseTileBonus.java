/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.List;

import cg2.model.BuildingPermit;
import cg2.player.Player;
import cg2.view.*;

/**
 * @author Francesco Vetrò
 *
 */
public class ReuseTileBonus extends ActionBonus {
	
	public ReuseTileBonus(Integer repeat) {
		super(repeat);
	}
	
	
	public void setView(View view) {
		this.registerObserver(view);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	@Override
	public <T> void update(T playerOrGame) {
		Player p = (Player)playerOrGame;
		List<BuildingPermit> listUnified = new ArrayList<BuildingPermit>(
				p.getStatus().getBuildingPermits());
		listUnified.addAll(p.getStatus().getUsedBuildingPermits());
		List<Bonus> permits;
		for(int i=0;i<this.amount;i++){
			permits = new ArrayList<Bonus>();
			for(BuildingPermit b: listUnified)
				permits.addAll(b.getBonusList());
			this.notifyObserver(p.getPlayerID(), permits);
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
		return "ReuseTileBonus"+super.toString();
	}

}
