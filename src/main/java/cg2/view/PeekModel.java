package cg2.view;

import java.util.Iterator;
import java.util.Set;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.player.Player;
import council.KingsCouncil;
import topology.Region;

public class PeekModel {
	
	private final Game game;
	
	public PeekModel(Game game) {
		this.game = game;
	}
	
	public Player getStatsPlayer(int playerID){
		for(Player p: game.getPlayers())
			if(p.getPlayerID() == playerID)
				return p;
		return null;
	}

	public Set<Region> getRegion() {
		return game.getRegions();
	}
	
	public KingsCouncil getKingCouncil(){
		return game.getKingsCouncil();
	}
	

	public void getPermits() {
		for(Region r: game.getRegions()){
			System.out.println(r.getName());
			Iterator<BuildingPermit> i = 
					r.getPermitsDeck().getFaceUpPermits().iterator();
			while(i.hasNext())
				System.out.println(i.next());
		}
		
	}

}
