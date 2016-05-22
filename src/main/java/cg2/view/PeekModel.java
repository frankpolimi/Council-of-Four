package cg2.view;

import java.util.Iterator;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.player.Player;
import topology.Region;

public class PeekModel {
	
	private final Game game;
	
	public PeekModel(Game game) {
		this.game = game;
	}
	
	public void getStatsPlayer(int playerID){
		for(Player p: game.getPlayers())
			if(p.getPlayerID() == playerID)
				System.out.println(p.toString());
	}

	public void getCouncils() {
		for(Region r: game.getRegions()){
			System.out.println(r.getName());
			System.out.println(r.getCouncil().toString());
		}
		System.out.println("King council:");
		System.out.println(game.getKingsCouncil().toString());
	}

	public void getPermits() {
		for(Region r: game.getRegions()){
			System.out.println(r.getName());
			Iterator<BuildingPermit> i = r.getPermitsDeck().getFaceUpPermits().iterator();
			while(i.hasNext())
				System.out.println(i.next());
		}
		
	}

}
