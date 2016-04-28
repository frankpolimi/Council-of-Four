/**
 * 
 */
package cg2.player;
import java.util.*;

import cg2.model.BuildingLicense;
import politics.*;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Status {
	private int coins;
	private int nobilityPoints;
	private int points;
	private int helpers;
	private final List<PoliticsCard> cardsOwned;
	private final List<BuildingLicense> buildingLicensesObtained;
	private final List<BuildingLicense> usedBuildingLicenses;
	
	public Status(int coins, int helpers){
		this.coins = coins;
		this.nobilityPoints = 0;
		this.points = 0;
		this.helpers = helpers;
		this.cardsOwned = new ArrayList<>();
		this.buildingLicensesObtained = new ArrayList<>();
		this.usedBuildingLicenses = new ArrayList<>();
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
	 * @return the helpers
	 */
	public int getHelpers() {
		return helpers;
	}

	/**
	 * @param helpers the helpers to set
	 */
	public void setHelpers(int helpers) {
		this.helpers = helpers;
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
	public List<BuildingLicense> getBuildingLicensesObtained() {
		return buildingLicensesObtained;
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
	
	public void addBuildingLicense(BuildingLicense bl){
		buildingLicensesObtained.add(bl);
	}

	/**
	 * @return the usedBuildingLicenses
	 */
	public List<BuildingLicense> getUsedBuildingLicenses() {
		return usedBuildingLicenses;
	}
	
	public void useLicense(BuildingLicense bl){
		int index = buildingLicensesObtained.indexOf(bl);
		usedBuildingLicenses.add(buildingLicensesObtained.remove(index));
	}
	
	
	
	
	
	
}
