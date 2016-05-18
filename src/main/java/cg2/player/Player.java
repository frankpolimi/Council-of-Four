/**
 * 
 */
package cg2.player;

import java.util.*;

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
		if(this.coins>=coins)
		{
			this.coins=this.coins-coins;
			return true;
		}
		return false;		
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public boolean checkAssistants(int assistants)
	{
		if(this.assistants>=assistants)
		{
			this.assistants=this.assistants-assistants;
			return true;
		}
		return false;		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assistants;
		result = prime * result + ((buildingPermits == null) ? 0 : buildingPermits.hashCode());
		result = prime * result + ((cardsOwned == null) ? 0 : cardsOwned.hashCode());
		result = prime * result + coins;
		result = prime * result + ((emporiumsOwned == null) ? 0 : emporiumsOwned.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nobilityPoints;
		result = prime * result + playerID;
		result = prime * result + points;
		result = prime * result + remainingEmporiums;
		result = prime * result + ((usedBuildingPermits == null) ? 0 : usedBuildingPermits.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (assistants != other.assistants)
			return false;
		if (buildingPermits == null) {
			if (other.buildingPermits != null)
				return false;
		} else if (!buildingPermits.equals(other.buildingPermits))
			return false;
		if (cardsOwned == null) {
			if (other.cardsOwned != null)
				return false;
		} else if (!cardsOwned.equals(other.cardsOwned))
			return false;
		if (coins != other.coins)
			return false;
		if (emporiumsOwned == null) {
			if (other.emporiumsOwned != null)
				return false;
		} else if (!emporiumsOwned.equals(other.emporiumsOwned))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nobilityPoints != other.nobilityPoints)
			return false;
		if (playerID != other.playerID)
			return false;
		if (points != other.points)
			return false;
		if (remainingEmporiums != other.remainingEmporiums)
			return false;
		if (usedBuildingPermits == null) {
			if (other.usedBuildingPermits != null)
				return false;
		} else if (!usedBuildingPermits.equals(other.usedBuildingPermits))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "name: " + name+ "\ncoins: " + coins + "\nassistants: " + assistants +
				"\nvictory points: " + points + "\nnobility points: " + nobilityPoints + 
				"\ncards owned: " + cardsOwned + "\nremaining emporiums: " + 
				remainingEmporiums + "\nemporiums owned: " + emporiumsOwned + 
				"\nbuilding permits: " + buildingPermits + ", usedBuildingPermits: " + 
				usedBuildingPermits;
	}

	public Set<City> getEmporiumsCitiesSet() {
		Set<City> cities=new HashSet<>();
		for(Emporium e:this.emporiumsOwned){
			cities.add(e.getCity());
		}
		return cities;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	
	
	
}
