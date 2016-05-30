package model.game;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.jdom2.JDOMException;
import org.jgrapht.graph.DefaultEdge;

import controller.Change;
import controller.StateChange;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.council.KingsCouncil;
import model.game.council.RegionalCouncil;
import model.game.politics.PoliticsDeck;
import model.game.topology.*;
import model.market.Market;
import model.observers.Observable;
import view.EndState;
import view.MarketBuyingState;
import view.MarketSellingState;
import view.StartState;
import view.State;

/**
 * @author Emanuele Ricciardelli, Vitaliy Pakholko
 *
 */
public class Game extends Observable<Change> {
	
	private State gameState;
	private final PoliticsDeck politicsDeck;
	private final PoliticsDeck usedPolitics;
	private final List<Player> players;
	private final List<Player> shuffledPlayers;
	private final Set<Region> regions;
	private final KingsCouncil kingsCouncil;
	private final List<Councillor> avaliableCouncillors;
	private final List<PointsTile> kingTileList;
	private final List<PointsTile> colorTileList;
	private final List<PointsTile> regionTileList;
	private final NobilityLane nobilityLane;
	private final ExtendedGraph<City,DefaultEdge> map;
	private final Market market;
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
		this.market=new Market();
		this.shuffledPlayers=new ArrayList<>(this.players);
		if(this.players.size()>2){
			this.init();
		}else{
			this.initFor2Players();
		}
		this.gameState=new StartState();
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
		EndState state=new EndState();
		state.setWinner(winnerSelector.getWinnerPlayer());
		this.notifyObservers(new StateChange(state));
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
	 * @return the market
	 */
	public Market getMarket() {
		return market;
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
		return "GAME\n [kingsCouncil=" + kingsCouncil + ", kingTileList=" + kingTileList + ", colorTileList="
				+ colorTileList + ", regionTileList=" + regionTileList + ", nobilityLane=" + nobilityLane + ", map="
				+ map + ", kingsPosition=" + kingsPosition + ", currentPlayer=" + currentPlayer + ", regions="+this.regions+" ]";
	}
	
	public List<RegionalCouncil> getRegionalCouncils(){
		Set<Region> regions=this.getRegions();
		List<RegionalCouncil> regional=new ArrayList<>();
		for(Region r:regions){
			regional.add(r.getCouncil());
		}
		
		return regional;
	}
	
	public List<Council> getAllCouncils(){
		List<RegionalCouncil> regCouncils=this.getRegionalCouncils();
		List<Council> councils=new ArrayList<>();
		councils.addAll(regCouncils);
		councils.add(this.kingsCouncil);
		return councils;
	}
	
	
	
	/**
	 * @return the shuffledPlayers
	 */
	public List<Player> getShuffledPlayers() {
		return shuffledPlayers;
	}
	
	public void nextState(){
		if(this.gameState instanceof StartState){
			this.gameState=new MarketSellingState();
		}else if(this.gameState.getClass().equals(MarketSellingState.class)){
			this.gameState=new MarketBuyingState();
			Collections.shuffle(this.shuffledPlayers);
		}else{
			this.gameState=new StartState();
		}
	}

	/**
	 * @return the gameState
	 */
	public State getGameState() {
		return gameState;
	}

	/**
	 * This method set a new gameState. It is notified to all clients connected to the server
	 * @param gameState the gameState to set
	 * 
	 */
	public void setGameState(State gameState) {
		this.gameState = gameState;
		this.notifyObservers(new StateChange(gameState));
	}
	
	

	/*public static void main(String[]args) throws JDOMException, IOException{
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
		
	}*/
	
	
}
