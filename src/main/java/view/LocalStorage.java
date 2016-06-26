/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.List;
import controller.BonusChange;
import controller.Change;
import controller.PermitsChange;
import model.bonus.Bonus;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;

/**
 * @author Francesco Vetro'
 *
 */
public class LocalStorage {
	
	private List<Object> bonus;
	private List<PermitsDeck> permits;
	private Game gameRef;
	private boolean isUpdated;
	
	
	public void setBonusList(List<Object> bonus){
		this.bonus=bonus;
	}
	
	/**
	 * constructor for the class that initialize the two lists
	 */
	public LocalStorage() {
		bonus=new ArrayList<>();
		permits=new ArrayList<>();;
	}
	
	
	/**
	 * @return the flag that status of the game whether is updated or not
	 * the flag is used after a player has performed an action to prevent 
	 * an action to be sent when the game is in a different status on the server
	 * than the client one
	 */
	public boolean isUpdated() {
		return isUpdated;
	}

	/**
	 * @param isUpdated the flag to set
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	/**
	 * method used to select the bonus desired
	 * @param place the index of the bonus in the list
	 * @return the object (could be only a city or a Building Permit) that contains the bonus desired
	 */
	/*public Object retrieveBonus(int place){
		return bonus.get(place);
	}*/
	
	/**
	 * method used to select the permit desired
	 * @param place the index of the permit in the list
	 * @return the desired permit at the specified index
	 */
	public PermitsDeck retrievePermit(int place){
		return permits.get(place);
	}
	
	/**
	 * method used to get the information about the length of
	 * the bonus list
	 * @return the length of the list that contains bonuses
	 */
	public int getBonusLenght(){
		return bonus.size();
	}
	
	/**
	 * method used to get the information about the length of
	 * the permit list
	 * @return the length of the list that contains permits
	 */
	public int getPermitsLenght(){
		return permits.size();
	}

	/**
	 * @return the bonus
	 */
	public List<Object> getBonus() {
		return bonus;
	}

	/**
	 * @return the permits
	 */
	public List<PermitsDeck> getPermits() {
		return permits;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(List<Object> bonus) {
		this.bonus = bonus;
	}

	/**
	 * @param permits the permits to set
	 */
	public void setPermits(List<PermitsDeck> permits) {
		this.permits = permits;
	}

	/**
	 * @return the gameRef
	 */
	public Game getGameRef() {
		return gameRef;
	}

	/**
	 * @param gameRef the gameRef to set
	 */
	public void setGameRef(Game gameRef) {
		this.gameRef = gameRef;
	}

	
}
