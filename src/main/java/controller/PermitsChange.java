/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import model.game.BuildingPermit;
import model.game.PermitsDeck;

/**
 * @author Francesco Vetr�
 *
 */
public class PermitsChange extends Change {

	private static final long serialVersionUID = -6202796008069721606L;
	private final List<PermitsDeck> permits;
	
	public PermitsChange() {
		permits = new ArrayList<>();
	}
	
	public PermitsChange(List<PermitsDeck> permits) {
		this.permits = new ArrayList<>(permits);
	}

	/**
	 * @return the permits
	 */
	public List<PermitsDeck> getPermits() {
		return permits;
	}

	public void addPermit(PermitsDeck permit) {
		this.permits.add(permit);
	}
	

}
