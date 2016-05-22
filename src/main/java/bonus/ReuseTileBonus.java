/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.List;

import cg2.controller.BonusChange;
import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.view.*;

/**
 * @author Francesco Vetr�
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
	public void update(Game game) {
		List<BuildingPermit> listUnified = new ArrayList<BuildingPermit>(
				game.getCurrentPlayer().getAllPermits());
		List<Bonus> permits;
		for(int i=0;i<this.amount;i++){
			permits = new ArrayList<Bonus>();
			for(BuildingPermit b: listUnified)
				permits.addAll(b.getBonusList());
			this.notifyObserver(game.getCurrentPlayer().getPlayerID(), new BonusChange(permits));
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
