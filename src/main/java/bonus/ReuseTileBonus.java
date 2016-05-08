/**
 * 
 */
package bonus;

import cg2.view.*;

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
	@Override
	public <T> void update(T playerOrGame) {
	}
	
	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * this will start the view and the selection for the bonus
	 */
	@Override
	public void update() {
		this.notifyObservers(this.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReuseTileBonus";
	}

}
