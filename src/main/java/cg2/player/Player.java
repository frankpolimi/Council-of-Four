/**
 * 
 */
package cg2.player;
import java.lang.reflect.Method;
import java.util.*;

import cg2.game.Game;
import cg2.model.*;
import politics.PoliticsCard;

/**
 * @author Emanuele Ricciardelli, Vitaliy Pakholko
 *	This class contains the most general informations about the player. This doesn't contain the status
 *	informations (points information, nobility information and so on).
 */
public class Player {
	private final String name;
	private final int playerID;
	private int remainingEmporiums;
	private final HashSet<Emporium> emporiumsOwned;
	
	private int coins;
	private int nobilityPoints;
	private int points;
	private int assistants;
	private final List<PoliticsCard> cardsOwned;
	private final List<BuildingPermit> buildingPermits;
	private final List<BuildingPermit> usedBuildingPermits;
	
	public Player(String name, int playerID, int remainingEmporiums, 
			int coins, int assistants) {
		this.name = name;
		this.playerID = playerID;
		this.remainingEmporiums = remainingEmporiums;//caricato da file
		emporiumsOwned=new HashSet<>();
		
		this.coins = coins;
		this.nobilityPoints = 0;
		this.points = 0;
		this.assistants = assistants;
		this.cardsOwned = new ArrayList<>();
		this.buildingPermits = new ArrayList<>();
		this.usedBuildingPermits = new ArrayList<>();
	}

	/**
	 * @return the remainingEmporiums
	 */
	public int getRemainingEmporiums() {
		return remainingEmporiums;
	}

	/**
	 * @param remainingEmporiums the remainingEmporiums to set
	 */
	public void setRemainingEmporiums(int remainingEmporiums) {
		this.remainingEmporiums = remainingEmporiums;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @param emporium
	 * add the emporium to the set of owned emporiums
	 */
	public void addEmporium(Emporium e){
		emporiumsOwned.add(e);
	}
	
	/**
	 * @return the set of emporiums a player possess
	 */
	public HashSet<Emporium> getEmporium(){
		return this.emporiumsOwned;
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
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public boolean checkCoins(int coins)
	{
		if(this.getStatus().getCoins()>=coins)
		{
			this.getStatus().setCoins(this.getStatus().getCoins()-coins);
			return true;
		}
		return false;		
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public boolean checkAssistants(int assistants)
	{
		if(this.getStatus().getAssistants()>=assistants)
		{
			this.getStatus().setAssistants(this.getStatus().getAssistants()-assistants);
			return true;
		}
		return false;		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Player [name=" + name + ", playerID=" + playerID + ", status=" + status + ", remainingEmporiums="
				+ remainingEmporiums + ", emporiumsOwned=" + emporiumsOwned + "]";
	}
	
	
	
}
