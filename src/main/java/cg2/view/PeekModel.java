package cg2.view;

import java.util.ArrayList;
import java.util.List;

import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.model.City;
import cg2.player.Player;
import council.Councillor;
import council.KingsCouncil;
import politics.PoliticsCard;
import topology.Region;

public class PeekModel {
	
	private final Game game;
	
	public PeekModel(Game game) {
		this.game = game;
	}
	
	public Player getCurrentPlayer(){
		return game.getCurrentPlayer();
	}
	
	public Player getStatsPlayer(int playerID){
		return this.getPlayerByID(playerID);
	}

	public List<Region> getRegion() {
		return new ArrayList<Region>(game.getRegions());
	}
	
	public KingsCouncil getKingCouncil(){
		return game.getKingsCouncil();
	}
	
	public List<City> getCities(){
		List<City> c = new ArrayList<City>();
		for(Region r : game.getRegions())
			c.addAll(r.getCities());
		return c;
	}

	public List<BuildingPermit> getPlayerPermit(int playerID) {
		return this.getPlayerByID(playerID).getAllPermits();
	}
	
	public List<PoliticsCard> getPlayerPolitic(int playerID){
			return this.getPlayerByID(playerID).getCardsOwned();
	}

	public List<Councillor> getAvailableCouncillor() {
		return game.getAvaliableCouncillor();
	}
	
	public List<BuildingPermit> getPlayerUsedPermits(int playerID){
		return this.getPlayerByID(playerID).getUsedBuildingPermits();
	}
	
	public int getPlayerAssistants(int playerID){
		return this.getPlayerByID(playerID).getAssistants();
	}
	
	public Player getPlayerByID(int playerID){
		for(Player p : game.getPlayers())
			if(p.getPlayerID() == playerID)
				return p;
		return null;
	}
}
