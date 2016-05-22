package cg2.game;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jdom2.JDOMException;
import org.jgrapht.graph.DefaultEdge;

import cg2.controller.Change;
import cg2.controller.StateChange;
import cg2.model.*;
import cg2.observers.Observable;
import politics.PoliticsDeck;
import cg2.player.*;
import cg2.view.EndState;
import council.Councillor;
import council.KingsCouncil;
import topology.*;

/**
 * @author Emanuele Ricciardelli, Vitaliy Pakholko
 *
 */
public class Game extends Observable<Change> {
	
	private final PoliticsDeck politicsDeck;
	private final PoliticsDeck usedPolitics;
	private final List<Player> players;
	private final Set<Region> regions;
	private final KingsCouncil kingsCouncil;
	private final List<Councillor> avaliableCouncillors;
	private final List<PointsTile> kingTileList;
	private final List<PointsTile> colorTileList;
	private final List<PointsTile> regionTileList;
	private final NobilityLane nobilityLane;
	private final ExtendedGraph<City,DefaultEdge> map;
	private City kingsPosition;
	
	/*
	private final Set<MainAction> mainAction = null; //just for avoiding errors
	private final Set<QuickAction> quickAction = null;
	*/

	private Player currentPlayer;
	
	private int mainActionCounter;
	private int quickActionCounter;
	

	public Game(List<Player> players) throws JDOMException, IOException {
		MapMaker mp=new MapMaker();
		this.politicsDeck=mp.createPoliticsDeck();
		this.usedPolitics=new PoliticsDeck(null);
		this.players=players;
		this.regions=mp.createRegionSet();
		this.map=mp.generateMap(this.regions);
		this.kingsCouncil=mp.getKingsCouncil();
		this.avaliableCouncillors=mp.getExtractedCouncillors();//The councils have been created yet, so these are the remaining councillors.
		this.kingTileList=mp.createTiles("kingTileList", this.regions);
		this.colorTileList=mp.createTiles("colorTileList", this.regions);
		this.regionTileList=mp.createTiles("regionTileList", this.regions);
		this.nobilityLane=mp.createNobilityLane();
		this.currentPlayer=this.players.get(0);
		this.kingsPosition=this.map.getVertexByKey("J");
	}

	public void gioca(){
		boolean endOfGame;
		do{
			this.resetActionCounters();
			politicsDeck.drawCard(currentPlayer);
			do{
				this.notifyObservers("action_phase");
				//TODO something
			}while(mainActionCounter != 0 && quickActionCounter != 0);
			//this.setCurrentPlayer(); gestione dei turni in controller
		endOfGame = checkEndOfGame();
		}while(endOfGame);
	}
	
	public void endOfTheGame() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<Player> copyList=new ArrayList<>();
		for(int i=0;i<this.players.size();i++){
			copyList.add(i, this.players.get(i));
		}
		
		WinnerSelector winnerSelector=new WinnerSelector(copyList);
		this.notifyObservers(new StateChange(new EndState(winnerSelector.getWinnerPlayer())));
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
	
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void shuffleUsedPolitics()
	{
		if(usedPolitics.isEmpty())
			throw new NullPointerException();
		politicsDeck.addUsedPolitics(usedPolitics);
		usedPolitics.clear();
		politicsDeck.shuffle();
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void addCouncillor(Councillor councillor)
	{
		this.avaliableCouncillors.add(councillor);
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void incrementQuickActionCounter()
	{
		quickActionCounter++;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void decrementQuickActionCounter()
	{
		quickActionCounter--;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void incrementMainActionCounter()
	{
		mainActionCounter++;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void decrementMainActionCounter()
	{
		mainActionCounter--;
	}

	
	/**
	 * @author Vitaliy Pakholko
	 */
	public City getKingsPosition() {
		return kingsPosition;
	}

	/**
	 * @author Vitaliy Pakholko
	 */
	public void setKingsPosition(City kingsPosition) {
		this.kingsPosition = kingsPosition;
	}

	/**
	 * @return the number of mainActionNumber
	 */
	public int getMainActionCounter() {
		return mainActionCounter;
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
	 * @return the politicsDeck
	 */
	public PoliticsDeck getPoliticsDeck() {
		return politicsDeck;
	}
	
	public List<Councillor> getAvaliableCouncillor(){
		return this.avaliableCouncillors;
	}
	
	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		
	}

	/**
	 * @return the kingsCouncil
	 */
	public KingsCouncil getKingsCouncil() {
		return kingsCouncil;
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Game [politicsDeck=" + politicsDeck + ", usedPolitics=" + usedPolitics + ",\n players=" + players
				+ ",\n regions=" + regions + ",\n avaliableCouncillors=" + avaliableCouncillors + ",\n kingTileList="
				+ kingTileList + ",\n colorTileList=" + colorTileList + ",\n regionTileList=" + regionTileList
				+ ",\n nobilityLane=" + nobilityLane + ",\n map=" + map + ",\n kingsPosition=" + kingsPosition
				+ ",\n currentPlayer=" + currentPlayer + ",\n mainActionCounter=" + mainActionCounter
				+ ",\n quickActionCounter=" + quickActionCounter + "]";
	}
	

	public static void main(String[]args) throws JDOMException, IOException{
		Player p1=new Player("Marco", 1, 10, 20, 10);
		Player p2=new Player("Paolo", 1, 10, 21, 10);
		
		
		
	}
}
