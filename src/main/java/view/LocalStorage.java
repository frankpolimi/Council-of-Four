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

/**
 * @author Francesco Vetr�
 *
 */
public class LocalStorage {
	
	private List<Bonus> bonus;
	private List<BuildingPermit> permits;
	private Game gameRef;
	
	public LocalStorage(Change list, Game game) {
		if(Change.class.equals(BonusChange.class))
			bonus = ((BonusChange)list).getBonusList();
		else if(Change.class.equals(PermitsChange.class))
			permits = ((PermitsChange)list).getPermits();
		gameRef=game;
	}
	
	public LocalStorage() {
		bonus=new ArrayList<>();
		permits=new ArrayList<>();
	}

	public Bonus retrieveBonus(int place){
		return bonus.get(place);
	}
	
	public BuildingPermit retrievePermit(int place){
		return permits.get(place);
	}
	
	public int getBonusLenght(){
		return bonus.size();
	}
	
	public int getPermitsLenght(){
		return permits.size();
	}

	/**
	 * @return the bonus
	 */
	public List<Bonus> getBonus() {
		return bonus;
	}

	/**
	 * @return the permits
	 */
	public List<BuildingPermit> getPermits() {
		return permits;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(List<Bonus> bonus) {
		this.bonus = bonus;
	}

	/**
	 * @param permits the permits to set
	 */
	public void setPermits(List<BuildingPermit> permits) {
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
