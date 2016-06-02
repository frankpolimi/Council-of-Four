/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import model.game.BuildingPermit;

/**
 * @author Francesco Vetr�
 *
 */
public class PermitsChange extends Change {

	private static final long serialVersionUID = -6202796008069721606L;
	private final List<BuildingPermit> permits;
	
	public PermitsChange() {
		permits = new ArrayList<BuildingPermit>();
	}
	
	public PermitsChange(List<BuildingPermit> permits) {
		this.permits = new ArrayList<>(permits);
	}

	/**
	 * @return the permits
	 */
	public List<BuildingPermit> getPermits() {
		return permits;
	}

	public void addPermit(BuildingPermit permit) {
		this.permits.add(permit);
	}
	

}
