package view;

import model.game.Player;

public class EndState implements State {

	private Player winner;
	
	@Override
	public void display() {
		System.out.println("The match is finished!");
		System.out.println("The player "+winner.getName()+"wins!");
		System.out.println("Player: "+winner.toString());
	}
	
	public void setWinner(Player winner){
		this.winner=winner;
	}

}
