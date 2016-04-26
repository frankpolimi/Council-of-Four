/**
 * 
 */
package cg2.player;
import java.util.*;
import cg2.model.*;

/**
 * @author Emanuele Ricciardelli
 *	This class contains the most general informations about the player. This doesn't contain the status
 *	informations (points information, nobility information and so on).
 */
public class Player {
	private final String name;
	private final int playerID;
	private final Status status;
	private int remainingEmporiums;
	private final Game refGame;
	private final HashSet<Emporium> emporiumsOwned;
	
	public Player(String name, int playerID, Status status, int remainingEmporiums, Game refGame) {
		this.name = name;
		playerID = playerID;
		this.status = status;
		this.remainingEmporiums = remainingEmporiums;//caricato da file
		this.refGame = refGame;
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
	public Game getRefGame() {
		return refGame;
	}
	
	public void addEmporium(Emporium e){
		emporiumsOwned.add(e);
	}
	
	
	
	
	
}
