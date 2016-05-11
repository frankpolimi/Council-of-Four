/**
 * 
 */
package cg2.player;
import java.util.*;

import cg2.model.BuildingPermit;
import politics.*;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Status {
	private int coins;
	private int nobilityPoints;
	private int points;
	private int assistants;
	private final List<PoliticsCard> cardsOwned;
	private final List<BuildingPermit> buildingPermits;
	private final List<BuildingPermit> usedBuildingPermits;
	
	public Status(int coins, int assistants){
		this.coins = coins;
		this.nobilityPoints = 0;
		this.points = 0;
		this.assistants = assistants;
		this.cardsOwned = new ArrayList<>();
		this.buildingPermits = new ArrayList<>();
		this.usedBuildingPermits = new ArrayList<>();
		//scelta implementativa: anche se all'inizio il regolamento dice che il giocatore parte
		//con 6 carte politica già nel mazzo, meglio farle caricare dalla partita in fase di 
		//init.
	}

	/**
	 * @return the coins
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * @param coins the coins to set
	 */
	public void setCoins(int coins) {
		this.coins = coins;
	}

	/**
	 * @return the nobilityPoints
	 */
	public int getNobilityPoints() {
		return nobilityPoints;
	}

	/**
	 * @param nobilityPoints the nobilityPoints to set
	 */
	public void setNobilityPoints(int nobilityPoints) {
		this.nobilityPoints = nobilityPoints;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the assistants
	 */
	public int getAssistants() {
		return assistants;
	}

	/**
	 * @param assistants to set
	 */
	public void setAssistants(int assistants) {
		this.assistants = assistants;
	}

	/**
	 * @return the cardsOwned
	 */
	public List<PoliticsCard> getCardsOwned() {
		return cardsOwned;
	}

	/**
	 * @return the buildingLicensesObtained
	 */
	public List<BuildingPermit> getBuildingPermits() {
		return buildingPermits;
	}
	
	public void addPoliticsCard(PoliticsCard pc){
		cardsOwned.add(pc);
	}
	
	public void removePoliticsCard(int position){
		cardsOwned.remove(position);
	}
	
	public PoliticsCard getPoliticsCard(int position){
		return cardsOwned.get(position);
	}
	
	public void addBuildingPermit(BuildingPermit bl){
		buildingPermits.add(bl);
	}
	
	
	public List<BuildingPermit> getUsedBuildingPermits() {
		return usedBuildingPermits;
	}
	
	public void usePermit(BuildingPermit bl){
		int index = buildingPermits.indexOf(bl);
		usedBuildingPermits.add(buildingPermits.remove(index));
	}
	
	
}
