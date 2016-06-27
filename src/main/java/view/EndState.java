package view;

import java.io.Serializable;
import java.util.*;

import model.game.Player;

/**
 * @author Emanuele Ricciardelli
 */

public class EndState implements State, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1714166266502408130L;
	private final Player winner;
	private final List<Player> ranking;
	
	/**
	 * This method displays the winner of the game.
	 */
	@Override
	public void display() {
		System.out.println("The match is finished!");
		System.out.println("The player "+winner.getName()+"wins!\n");
		System.out.println("Player: "+winner.toString());
	}
	
	/**
	 * This methods sets the winner of the match.
	 * @param winner is the player who wins the match
	 * @param ranking is the ranking of the match
	 * @throws IllegalArgumentException if the player passed is null
	 */
	public EndState(Player winner, List<Player> ranking){
		if(winner==null||ranking==null)
			throw new NullPointerException("The player or ranking passed is null");
		if(ranking.isEmpty()){
			throw new IllegalArgumentException("The ranking is empty");
		}
		this.winner=winner;
		this.ranking=ranking;
	}

	/**
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * @return the ranking
	 */
	public List<Player> getRanking() {
		return ranking;
	}
	
	
}
