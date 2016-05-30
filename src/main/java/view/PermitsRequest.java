/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.List;

import model.game.BuildingPermit;
import model.game.Player;

/**
 * @author francesco
 *
 */
public class PermitsRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4069051733145832810L;
	
	private final List<BuildingPermit> permits;

	public PermitsRequest(Player player) {
		super(player);
		permits = new ArrayList<BuildingPermit>();
	}

	public PermitsRequest(List<BuildingPermit> permits, Player player) {
		super(player);
		this.permits = permits;
		
	}

	/**
	 * the permits the player can obtain freely
	 * @return the permits
	 */
	public List<BuildingPermit> getPermits() {
		return permits;
	}

	/**
	 * to set the list of permits
	 * @param permit the permits to be set
	 */
	public void addPermit(BuildingPermit permit) {
		this.permits.add(permit);
	}

}
