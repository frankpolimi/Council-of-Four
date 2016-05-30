package view;

import model.game.Player;

public class EndState implements State {

	private Player winner;
	
	/**
	 * This method displays the winner of the game.
	 */
	@Override
	public void display() {
		System.out.println("The match is finished!");
		System.out.println("The player "+winner.getName()+"wins!");
		System.out.println("Player: "+winner.toString());
	}
	
	/**
	 * This methods sets the winner of the match.
	 * @param winner is the player who wins the match
	 * @throws IllegalArgumentException if the player passed is null
	 */
	public EndState(Player winner){
		if(winner==null)
			throw new IllegalArgumentException("The player passed is null");
		this.winner=winner;
	}

}
