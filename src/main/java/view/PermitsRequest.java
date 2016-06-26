/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.List;
import model.game.BuildingPermit;
import model.game.PermitsDeck;

/**
 * @author Francesco Vetro'
 *
 */
public class PermitsRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4069051733145832810L;
	
	//private final List<BuildingPermit> permits;
	private final PermitsDeck deck;
	private final BuildingPermit permit;
	/**
	 * constructor for the class that will be sent to the
	 * controller. the class's field is the list of permits
	 * that the player has decided to acquire when he receives
	 * a permits change.
	 * @param iD the ID of the player
	 */
	public PermitsRequest(int iD, PermitsDeck deck, BuildingPermit permit) {
		super(iD);
		//permits = new ArrayList<BuildingPermit>();
		this.deck=deck;
		this.permit=permit;
	}

	public PermitsDeck getPermitsDeck(){
		return this.deck;
	}
	
	public BuildingPermit getPermit() {
		return permit;
	}
/*
	/**
	 * the permits the player can obtain freely
	 * @return the permits
	 
	public List<BuildingPermit> getPermits() {
		return permits;
	}

	/**
	 * to set the list of permits
	 * @param permit the permits to be set
	 
	public void addPermit(BuildingPermit permit) {
		this.permits.add(permit);
	}*/

}
