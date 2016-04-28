/**
 * 
 */
package bonus;

import cg2.player.*;

/**
 * @author Francesco Vetrò
 *
 */
public class ReuseTileBonus extends ActionBonus {

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
		
		/*
		 * ((Player)playerOrGame).getStatus().getUsedBuildingLicense();
		 * need the view for the selection
		 * change the implementation of the status:
		 * - the used building license must be in a different arraylist
		 */
	}

}
