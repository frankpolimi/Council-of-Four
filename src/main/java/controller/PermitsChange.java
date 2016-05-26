/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import model.game.BuildingPermit;

/**
 * @author Francesco Vetrò
 *
 */
public class PermitsChange extends Change {
	
	private final List<BuildingPermit> permits;
	
	public PermitsChange() {
		permits = new ArrayList<BuildingPermit>();
	}
	
	public PermitsChange(List<BuildingPermit> permits) {
		this.permits = permits;
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
