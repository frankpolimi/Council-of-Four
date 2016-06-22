/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.List;
import model.game.BuildingPermit;

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

	/**
	 * constructor for the class that will be sent to the
	 * controller. the class's field is the list of permits
	 * that the player has decided to acquire when he receives
	 * a permits change.
	 * @param iD the ID of the player
	 */
	public PermitsRequest(int iD) {
		super(iD);
		permits = new ArrayList<BuildingPermit>();
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
