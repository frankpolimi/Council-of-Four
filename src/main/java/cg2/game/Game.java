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
	private final List<Player> players;
	private final Set<Region> regions;
	private final List<Councillor> avaliableCouncillors;
	private final List<KingTile> kingTileList;
	private final List<ColorTile> colorTileList;
	private final List<RegionTile> regionTileList;
	private Player currentPlayer;
	
	private int mainActionNumber;
	private int quickActionNumber;
	
	
	public Game(LicenseDeck licenseDeck, PoliticsDeck politicsDeck, List<Player> players, Set<Region> regions,
			List<Councillor> avaliableCouncillors, List<KingTile> kingTileList, List<ColorTile> colorTileList,
			List<RegionTile> regionTileList) {
		super();
		this.licenseDeck = licenseDeck;
		this.politicsDeck = politicsDeck;
		this.players = players;
		this.regions = regions;// in realtà viene inizializzato in questo costruttore
		this.avaliableCouncillors = avaliableCouncillors;
		this.kingTileList = kingTileList;
		this.colorTileList = colorTileList;
		this.regionTileList = regionTileList;
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
	
	
	
	
	
	
	
	
	
}
