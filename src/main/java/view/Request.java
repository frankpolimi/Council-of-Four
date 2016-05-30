/**
 * 
 */
package view;

import java.io.Serializable;

import model.game.Player;

/**
 * @author francesco
 *
 */
public abstract class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8272737714020599087L;

	private final Player player;
	public Request(Player player) {
		this.player=player;
	} 
	
	public Player getPlayer() {
		return player;
	}
}
