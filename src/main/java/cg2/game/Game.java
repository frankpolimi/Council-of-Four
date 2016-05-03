package cg2.game;

import java.util.List;
import java.util.Set;

import cg2.model.*;
import cg2.observers.Observable;
import politics.PoliticsDeck;
import cg2.player.*;
import council.Councillor;
import topology.*;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Game extends Observable {
	
	private final LicenseDeck licenseDeck;
	private final PoliticsDeck politicsDeck;
	private final PoliticsDeck usedPolitics;
	private final List<Player> players;
	private final Set<Region> regions;
	private final List<Councillor> avaliableCouncillors;
	private final List<KingTile> kingTileList;
	private final List<ColorTile> colorTileList;
	private final List<RegionTile> regionTileList;
	private final NobilityLane nobilityLane;
	
	private int currentPlayer;
	
	private int mainActionNumber;
	private int quickActionNumber;
	

	public Game(LicenseDeck licenseDeck, PoliticsDeck politicsDeck, List<Player> players, Set<Region> regions,
			List<Councillor> avaliableCouncillors, List<KingTile> kingTileList, List<ColorTile> colorTileList,
			List<RegionTile> regionTileList, NobilityLane nobilityLane) {
		super();
		this.licenseDeck = licenseDeck;
		this.politicsDeck = politicsDeck;
		this.players = players;
		this.regions = regions;// in realtà viene inizializzato in questo costruttore
		this.avaliableCouncillors = avaliableCouncillors;
		this.kingTileList = kingTileList;
		this.colorTileList = colorTileList;
		this.regionTileList = regionTileList;
		this.nobilityLane = nobilityLane;
		this.usedPolitics = new PoliticsDeck();
		this.currentPlayer = 0;
	}
	
	public void gioca(){
		boolean endOfGame;
		do{
			while(currentPlayer < players.size()){
				this.setActionNumber();
				politicsDeck.drawCard(players.get(currentPlayer));
				do{
					this.notifyObservers("action phase");
					//mossa principale obbligatoria
					//possibilità skip mossa secondaria
					//controllore esegue mossa
					//diminuzione counter mosse	
				}while(mainActionNumber != 0 && quickActionNumber != 0);
				currentPlayer = (currentPlayer++)%players.size();
			}
			endOfGame = checkEndOfGame();
		}while(endOfGame);
	}

	private void setActionNumber() {
		mainActionNumber = 1;
		quickActionNumber = 1;
	}

	private boolean checkEndOfGame() {
		for(Player p: players)
			if(p.getRemainingEmporiums() == 0)
				return true;
		return false;
	}

	public void shuffleUsedPolitics()
	{
		if(usedPolitics.isEmpty())
			throw new NullPointerException();
		politicsDeck.addUsedPolitics(usedPolitics);
		usedPolitics.clear();
		politicsDeck.shuffle();
	}
	
	
	/**
	 * @return the number of mainActionNumber
	 */
	public int getMainActionNumber() {
		return mainActionNumber;
	}


	/**
	 * @param mainActionNumber2
	 * set the number of main action still to be done
	 */
	public void setMainActionNumber(int mainActionNumber2) {
		this.mainActionNumber = mainActionNumber2;		
	}


	/**
	 * @return the set of region
	 */
	public Set<Region> getRegions() {
		return regions;
	}
	
	/**
	 * @return the quickActionNumber
	 */
	public int getQuickActionNumber() {
		return quickActionNumber;
	}

	/**
	 * @param quickActionNumber the quickActionNumber to set
	 */
	public void setQuickActionNumber(int quickActionNumber) {
		this.quickActionNumber = quickActionNumber;
	}
	
	
}
