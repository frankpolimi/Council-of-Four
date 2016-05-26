/**
 * 
 */
package controller;

import model.game.Game;

/**
 * @author Francesco Vetrò
 *
 */
public class ModelChange extends Change {
	
	private final Game game;
	
	public ModelChange(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
	
	public String stampModelSnapshot(){
		return game.toString();
	}
}
