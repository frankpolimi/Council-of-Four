package cg2.view;

import cg2.game.Game;
import cg2.model.Emporium;
import cg2.player.Player;

public class PeekModel {
	
	private final Game game;
	
	public PeekModel(Game game) {
		this.game = game;
	}
	
	public void getStatsPlayer(int playerID){
		for(Player p: game.getPlayers())
			if(p.getPlayerID() == playerID)
				this.printStat(p);
	}

	private void printStat(Player p){
		System.out.println("Name: "+p.getName());
		System.out.println("Coins: "+p.getCoins());
		System.out.println("Assistants: "+p.getAssistants());
		System.out.println("Victory points: "+p.getPoints());
		System.out.println("Nobility points: "+p.getNobilityPoints());
		System.out.println("Emporiums owned: ");
		for(Emporium e: p.getEmporium())
			e.toString();
	}

}
