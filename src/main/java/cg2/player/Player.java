/**
 * 
 */
package cg2.player;
import java.lang.reflect.Method;
import java.util.*;

import cg2.game.Game;
import cg2.model.*;

/**
 * @author Emanuele Ricciardelli, Vitaliy Pakholko
 *	This class contains the most general informations about the player. This doesn't contain the status
 *	informations (points information, nobility information and so on).
 */
public class Player {
	private final String name;
	private final int playerID;
	private final Status status;
	private int remainingEmporiums;
	//private final Game refGame;
	private final HashSet<Emporium> emporiumsOwned;
	
	public Player(String name, int playerID, Status status, int remainingEmporiums) {
		this.name = name;
		this.playerID = playerID;
		this.status = status;
		this.remainingEmporiums = remainingEmporiums;//caricato da file
		//this.refGame = refGame;
		emporiumsOwned=new HashSet<>();
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
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return the refGame
	 */
	/*
	public Game getRefGame() {
		return refGame;
	}
	*/
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
