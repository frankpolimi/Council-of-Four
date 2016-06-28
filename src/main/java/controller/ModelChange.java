/**
 * 
 */
package controller;

import model.game.Game;

/**
 * @author Francesco Vetro'
 *
 */
public class ModelChange extends Change {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -899114273528545171L;
	private final Game game;
	
	/**
	 * constructor for a game change.
	 * this message contains the game that the clients are playing
	 * this is due to the high complexity of the game itself 
	 * @param game the game that the clients are playing 
	 * 				that changed and must be sent to players
	 */
	public ModelChange(Game game) {
		this.game = game;
	}

	/**
	 * get the game for the change
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * return a string representation of the game as field
	 * @return a string that represents the game
	 */
	public String stampModelSnapshot(){
		return game.toString();
	}
}
