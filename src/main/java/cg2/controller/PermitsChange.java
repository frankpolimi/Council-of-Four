/**
 * 
 */
package cg2.controller;

import java.util.List;

import cg2.model.BuildingPermit;

/**
 * @author Francesco Vetrò
 *
 */
public class PermitsChange extends Change {
	
	private final List<BuildingPermit> permits;
	
	public PermitsChange(List<BuildingPermit> permits) {
		this.permits = permits;
	}

	/**
	 * @return the permits
	 */
	public List<BuildingPermit> getPermits() {
		return permits;
	}
	

}
