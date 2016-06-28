/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import model.game.PermitsDeck;

/**
 * @author Francesco Vetro'
 *
 */
public class PermitsChange extends Change {

	private static final long serialVersionUID = -6202796008069721606L;
	private final List<PermitsDeck> permits;
	
	/**
	 * constructor for a permits change
	 * this message is sent to the client 
	 * (only one client and only if the correspondent
	 * bonus on the nobility lane is triggered)
	 * to let him chose one permits form the decks 
	 * that are in the game
	 */
	public PermitsChange() {
		permits = new ArrayList<>();
	}
	
	/**
	 * constructor for a permits change that 
	 * send to the client the decks of permits from which
	 * he may chose once the correspondent nobility bonus has
	 * been triggered
	 * @param permits the list of decks of the model
	 */
	public PermitsChange(List<PermitsDeck> permits) {
		this.permits = new ArrayList<>(permits);
	}

	/**
	 * get the list of decks given in this change
	 * @return the permits
	 */
	public List<PermitsDeck> getPermits() {
		return permits;
	}

	/**
	 * add a deck of permits to the change
	 * @param permit the deck that will be added to the change
	 */
	public void addPermit(PermitsDeck permit) {
		this.permits.add(permit);
	}
	

}
