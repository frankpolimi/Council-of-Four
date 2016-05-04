/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.List;

import cg2.view.View;
import cg2.player.*;
import cg2.model.City;
import cg2.model.Emporium;

/**
 * @author Francesco Vetrò
 *
 */
public class CityBonus extends ActionBonus {
	
	public CityBonus(View view) {
		this.registerObserver(view);
	}

	/* (non-Javadoc)
	 * @see bonus.ActionBonus#update(java.lang.Object)
	 */
	/**
	 * @param the player
	 * this will notify the view with the list of cities where the player
	 * has built an emporium. the view will handle the input for choosing
	 * the city's bonus the player wants to earn again.
	 */
	@Override
	public <T> void update(T playerOrGame) {
		List<City> builton = new ArrayList<City>();
		
		for(Emporium e : ((Player)playerOrGame).getEmporium())
			builton.add(e.getCity());
		
		this.notifyObservers(builton);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityBonus";
	}
	
	

}
