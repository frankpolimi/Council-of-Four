package cg2.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.player.Player;
import council.Council;
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

	public List<Council> getCouncils() {
		List<Council> x = new ArrayList<Council>();
		for(Region r: game.getRegions())
			x.add(r.getCouncil());
		x.add(game.getKingsCouncil());
		return x;
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
