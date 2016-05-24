package cg2.game;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
		if(this.players.size()>2){
			this.init();
		}else{
			this.initFor2Players();
		}
	}

	/*
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
	}*/
	
	public void init(){
		int playerNumber=this.players.size();
		int div=Math.floorDiv(playerNumber, 4);
		//appending PoliticsDeck
		for(int i=0;i<div;i++){
			this.politicsDeck.append();
		}
		this.politicsDeck.shuffle();
		for(int i=0;i<playerNumber;i++){
			Player current=this.players.get(i);
			current.setCoins(10+i);
			current.setAssistants(1+i);
			this.politicsDeck.drawNCards(current);
		}
		
		for(Region r:this.regions){
			r.getPermitsDeck().faceUpInit();
		}
	}
	
	public void initFor2Players(){
		Random random=new Random();
		this.init();
		for(Region region:this.regions){
			BuildingPermit permit=region.getPermitsDeck().popPermit();
			Set<City> buildingCities=permit.getBuildingAvaliableCities();
			
			Color color;
			do{
				int r=random.nextInt(256);
				int g=random.nextInt(256);
				int b=random.nextInt(256);
				color=new Color(r,g,b);
			}while(isColorAlreadyCreated(color));

			for(City city:buildingCities){
				city.addEmporium(color);
			}
			region.getPermitsDeck().faceUpInit();
		}
		
	}
	
	public ExtendedGraph<City, DefaultEdge> getMap(){
		return this.map;
	}
	
	private boolean isColorAlreadyCreated(Color color){
		boolean check=false;
		for(Player p:this.players){
			if(p.getChosenColor().equals(color)){
				check=true;
			}
		}
		return check;
	}
	public void endOfTheGame(List<Player> players) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<Player> copyList=new ArrayList<>();
		for(int i=0;i<players.size();i++){
			copyList.add(i, players.get(i));
		}
		
		WinnerSelector winnerSelector=new WinnerSelector(copyList);
		this.notifyObservers(new StateChange(new EndState(winnerSelector.getWinnerPlayer())));
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
		Player p1=new Player("Marco", 1, 10, 10);
		Player p2=new Player("Paolo", 5, 10, 10);
		Player p3=new Player("Mario", 4, 10, 2);
		Player p4=new Player("Marco",2,10,2);
		Player p5=new Player("Luigi",3,10,2);
		List<Player> players=new ArrayList<>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		players.add(p5);
		Game game=new Game(players);
		System.out.println(game.players);
		
		
		
		System.out.println("Mappa PRIMA");
		ExtendedGraph<City, DefaultEdge> map=game.getMap();
		
	}
	
	
}
