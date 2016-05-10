package cg2.game;

import java.util.List;
import java.util.Set;

import actions.MainAction;
import actions.QuickAction;
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
	
	private final PermitsDeck licenseDeck;
	private final PoliticsDeck politicsDeck;
	private final PoliticsDeck usedPolitics;
	private final List<Player> players;
	private final Set<Region> regions;
	private final List<Councillor> avaliableCouncillors;
	private final List<KingTile> kingTileList;
	private final List<ColorTile> colorTileList;
	private final List<RegionTile> regionTileList;
	private final NobilityLane nobilityLane;
	
	private final Set<MainAction> mainAction = null; //just for avoiding errors
	private final Set<QuickAction> quickAction = null;
	
	private Player currentPlayer;
	
	private int mainActionCounter;
	private int quickActionCounter;
	

	public Game(PermitsDeck licenseDeck, PoliticsDeck politicsDeck, List<Player> players, Set<Region> regions,
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
		this.usedPolitics = new PoliticsDeck(null); //da cambiare
	}
	
	public void gioca(){
		boolean endOfGame;
		do{
			this.resetActionCounters();
			politicsDeck.drawCard(currentPlayer);
			do{
				this.notifyObservers("action_phase");
				//mossa principale obbligatoria
				//possibilità skip mossa secondaria
				//controllore esegue mossa
				//diminuzione counter mosse	
			}while(mainActionCounter != 0 && quickActionCounter != 0);
			//this.setCurrentPlayer(); gestione dei turni in controller
		endOfGame = checkEndOfGame();
		}while(endOfGame);
	}

	private void resetActionCounters() {
		mainActionCounter = 1;
		quickActionCounter = 1;
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
	
	public void addCouncillor(Councillor councillor)
	{
		this.avaliableCouncillors.add(councillor);
	}
	
	
	/**
	 * @return the number of mainActionNumber
	 */
	public int getMainActionCounter() {
		return mainActionCounter;
	}


	public void incrementMainActionCounter()
	{
		mainActionCounter++;
	}
	
	public void decrementMainActionCounter()
	{
		mainActionCounter--;
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
	public int getQuickActionCounter() {
		return quickActionCounter;
	}
	
	public void incrementQuickActionCounter()
	{
		quickActionCounter++;
	}
	
	public void decrementQuickActionCounter()
	{
		quickActionCounter--;
	}

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @return the mainAction
	 */
	public Set<MainAction> getMainAction() {
		return mainAction;
	}
	
	/**
	 * @return the quickAction
	 */
	public Set<QuickAction> getQuickAction() {
		return quickAction;
	}

	/**
	 * @return the politicsDeck
	 */
	public PoliticsDeck getPoliticsDeck() {
		return politicsDeck;
	}
	
	public List<Councillor> getAvaliableCouncillor(){
		return this.avaliableCouncillors;
	}
	
}
