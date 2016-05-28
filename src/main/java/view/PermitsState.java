/**
 * 
 */
package view;

import java.util.List;
import model.game.BuildingPermit;

/**
 * @author Francesco Vetr�
 *
 */
public class PermitsState implements State {
	
	private List<BuildingPermit> permitsList;

	public PermitsState(List<BuildingPermit> permitsList) {
		this.permitsList = permitsList;
	}
	
	public List<BuildingPermit> getPermitsList() {
		return permitsList;
	}
	
	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	/**
	 * display the face up permits for each region
	 * gives the controller the desired building permits to acquire
	 */
	@Override
	public void display() {

	}

}
