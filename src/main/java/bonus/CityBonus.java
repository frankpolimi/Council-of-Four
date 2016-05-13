/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.List;

import cg2.model.Emporium;
import cg2.player.Player;
import cg2.view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class CityBonus extends ActionBonus {
	
	public CityBonus (Integer repeat){
		super(repeat);
	}
	
	public void setView(View view) {
		this.registerObserver(view);
	}

	/* (non-Javadoc)
	 * @see bonus.ActionBonus#update(java.lang.Object)
	 */
	/**
	 * get updated by the action that perform the changes in the model
	 * and will update the specific view of the player that must perform the input
	 * @param playerOrGame the player that will obtain the bonus
	 */
	@Override
	public <T> void update(T playerOrGame) {
		List<Bonus> cities;
		Player p = (Player)playerOrGame;
		for(int i=0; i< this.amount;i++){
			cities = new ArrayList<Bonus>();
			for(Emporium e : p.getEmporium())
				cities.addAll(
						super.checkNoNobility(e.getCity().getBonus()));
			this.notifyObserver(p.getPlayerID(), cities);
		}
	}
	
	/* (non-Javadoc)
	 * @see bonus.ActionBonus#update(java.lang.Object)
	 */
	@Override
	public void update(){
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityBonus" + super.toString();
	}
	
	

}
