package view;

import model.game.Player;

public class EndState implements State {

	private Player winner;
	
	/**
	 * This method displays the winner of the game.
	 * @throws IllegalStateException if the winner is null. So it is necessary to wait the ending of the match before calling this method.
	 */
	@Override
	public void display() {
		if(winner==null)
			throw new IllegalStateException("The winner is not proclaimed yet!");
		
		System.out.println("The match is finished!");
		System.out.println("The player "+winner.getName()+"wins!");
		System.out.println("Player: "+winner.toString());
	}
	
	/**
	 * This methods sets the winner of the match.
	 * @param winner is the player who wins the match
	 * @throws IllegalArgumentException if the player passed is null
	 */
	public void setWinner(Player winner){
		if(winner==null)
			throw new IllegalArgumentException("The player passed is null");
		
		this.winner=winner;
	}

}
