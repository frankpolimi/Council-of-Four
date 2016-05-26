package view;

import model.game.Player;

public class EndState implements State {

	private final Player winner;
	
	@Override
	public void display() {
		System.out.println("The match is finished!");
		System.out.println("The player "+winner.getName()+"wins!");
		System.out.println("Player: "+winner.toString());
	}
	
	public EndState(Player winner) {
		this.winner=winner;
	}

}
