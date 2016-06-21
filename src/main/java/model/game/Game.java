package model.game;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import org.jdom2.JDOMException;
import org.jgrapht.graph.DefaultEdge;
import controller.Change;
import controller.ModelChange;
import controller.StateChange;
import model.actions.DisconnectionTimer;
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
public class Game extends Observable<Change> implements Serializable, Remote{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6566029665416943724L;
	
	public final static int DISCONNECTION_TIME=600*1000;
	private State gameState;
	private int lastTurnRemainingPlayers;
	private boolean lastTurn;
	private transient Timer timer;
	private final PoliticsDeck politicsDeck;
	private final PoliticsDeck usedPolitics;
	private final List<Player> players;
	private final List<Player> shuffledPlayers;
	private final List<Player> disconnectedPlayers;
	private final Set<Region> regions;
	private final KingsCouncil kingsCouncil;
	private final List<Councillor> avaliableCouncillors;
	private final List<PointsTile> kingTileList;
	private final List<PointsTile> colorTileList;
	private final List<PointsTile> regionTileList;
	private final NobilityLane nobilityLane;
	private final ExtendedGraph<City,DefaultEdge> map;
	private Market market;
	private City kingsPosition;
	
	/*
	private final Set<MainAction> mainAction = null; //just for avoiding errors
	private final Set<QuickAction> quickAction = null;
	*/

	private Player currentPlayer;
	
	private int mainActionCounter;
	private int quickActionCounter;
	

	public Game() throws JDOMException, IOException {
		MapMaker mp=new MapMaker();
		
		this.lastTurn=false;
		this.politicsDeck=mp.createPoliticsDeck();
		this.usedPolitics=new PoliticsDeck(null);
		this.players=new ArrayList<>();
		this.regions=mp.createRegionSet(this.extractRandomMap(mp.getRegionNumber()));
		this.map=mp.generateMap(this.regions);
		this.kingsCouncil=mp.getKingsCouncil();
		this.avaliableCouncillors=mp.getExtractedCouncillors();//The councils have been created yet, so these are the remaining councillors.
		this.kingTileList=mp.createTiles("kingTileList", this.regions);
		this.colorTileList=mp.createTiles("colorTileList", this.regions);
		this.regionTileList=mp.createTiles("regionTileList", this.regions);
		this.nobilityLane=mp.createNobilityLane();
		this.kingsPosition=mp.getKingCity(this.regions, this.map);
		this.market=new Market(this);
		this.shuffledPlayers=new ArrayList<>();
		this.disconnectedPlayers=new ArrayList<>();
		this.timer=new Timer();
	}

	private String extractRandomMap(int regionNum){
		String extracted="";
		for(int i=0;i<regionNum;i++){
			Random random=new Random();
			int sel=random.nextInt(2)+65; 
			extracted=extracted+(char)sel;
		}
		System.out.println(extracted);
		return extracted;
		
	}
	
	public void setPlayers(List<Player> players){
		this.players.addAll(players);
		if(this.players.size()>2){
			this.init();
		}else{
			this.initFor2Players();
		}
		this.currentPlayer=this.players.get(0);
		this.lastTurnRemainingPlayers=this.players.size();
		this.shuffledPlayers.addAll(this.players);
		this.gameState=new StartState();
		this.mainActionCounter = 1;
		this.quickActionCounter = 1;
		this.notifyObservers(new ModelChange(this));
		this.timer.schedule(new DisconnectionTimer(this), DISCONNECTION_TIME);
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
		
	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}


	/**
	 * @param timer the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	/**
	 * @return the lastTurn
	 */
	public boolean isLastTurn() {
		return lastTurn;
	}

	/**
	 * @param lastTurn the lastTurn set true
	 */
	public void setLastTurnTrue() {
		this.lastTurn = true;
	}
	
	public void decrementLastRemainingPlayers(){
		this.lastTurnRemainingPlayers--;
	}
	
	public int getLastTurnRemainingPlayers(){
		return this.lastTurnRemainingPlayers;
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
	
	public void endOfTheGame() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		this.players.addAll(this.disconnectedPlayers);
		List<Player> copyList=new ArrayList<>(this.players);
		WinnerSelector winnerSelector=new WinnerSelector(copyList);
		this.gameState=new EndState(winnerSelector.getWinnerPlayer());
		this.notifyObservers(new StateChange(this.gameState));
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
	 * @return the disconnectedPlayers
	 */
	public List<Player> getDisconnectedPlayers() {
		return disconnectedPlayers;
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
	 * this method will return the player 
	 * with the same playerID as the socket out
	 * @return the player with the ID desired
	 */
	public Player getPlayerByID(int id) {
		return this.players.stream().filter(e->e.getPlayerID()==id).findFirst().get();
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
	 */
	public void setMarket(Market market) {
		this.market=market;
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
		return "GAME\n[KINGS COUNCIL\n" + kingsCouncil + "\n\nKING TILE LIST\n" + kingTileList + "\n\nCOLOR TILE LIST\n"
				+ colorTileList + "\n\nREGION TILE LIST\n" + regionTileList + "\n\nNOBILITY LANE\n" + nobilityLane + "\n\nREGIONS\n\n"+this.regions
				+"MAP\n"+ map + "\nKING POSITION=" + kingsPosition + "\nCURRENT PLAYER=" + currentPlayer + " ]";
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
	
	public List<City> getAllCities()
	{
		List<City> list=new ArrayList<>();
		for(Region r:this.getRegions())
		{
			list.addAll(r.getCities());
		}
		return list;
	}
	
	public List<PermitsDeck> getAllPermitsDecks()
	{
		List<PermitsDeck> list=new ArrayList<>();
		for(Region r:this.getRegions())
		{
			list.add(r.getPermitsDeck());
		}
		return list;
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
			this.market.returnUnselledItems();
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
	

	/**
	 * method that check if a player deserve a PointsTile
	 * checks if the player has obtained a regionTile or a cityTile
	 * and assign it only if not already obtained. this two types must always
	 * be available for other players.
	 * if one of the above is obtained, the method will activate the controls on
	 * the kingsTile and, if available, will assign the first on top and then 
	 * remove this from the game.
	 * @param curr the current player that has performed the action of building an emporium
	 * @param builtOn the city in which has been built an emporium 
	 */
	public void giveTiles(Player curr, City builtOn) {
		
		Iterator<Region> r = regions.iterator();
		int i = 0;
		while(r.hasNext()){
			Region x = r.next();
			if(x.getCities().contains(builtOn) && 
					curr.getEmporiumsCitiesSet().containsAll(x.getCities())){
				PointsTile pt = regionTileList.get(i);
				if(!curr.getTilesOwned().contains(pt))
					curr.addPointsTile(pt);
				this.giveKingTile(curr);
			}
			i++;
		}
		
		List<City> sameColorCities = new ArrayList<>();
		for(City c : this.getAllCities())
			if(c.getCityColor().equals(builtOn))
				sameColorCities.add(c);
		if(curr.getEmporiumsCitiesSet().containsAll(sameColorCities))
			for(PointsTile pt2 : colorTileList)
				if(((ColorTile)pt2).getCityColor().equals(builtOn.getCityColor()) && 
						!curr.getTilesOwned().contains(pt2))
					curr.addPointsTile(pt2);
			this.giveKingTile(curr);		
	}

	/**
	 * the method will perform a check only if the player 
	 * as parameter has obtained a colorTile or a regionTile
	 * and will assign a kingsTile to the player if is available.
	 * than will remove the tile from the game
	 * @param curr the player that has built on a city and
	 * 			checks if is available one of the kingsTile
	 */
	private void giveKingTile(Player curr) {
		if(!this.kingTileList.isEmpty()){
			curr.addPointsTile(kingTileList.get(0));
			kingTileList.remove(0);
		}
	}


	/**
	 * @return the nobilityLane
	 */
	public NobilityLane getNobilityLane() {
		return nobilityLane;
	}
	
	public static void main(String[] args) throws JDOMException, IOException {
		Game game=new Game();
		List<Player> players=new ArrayList<>();
		players.add(new Player("io",1));
		players.add(new Player("tu",2));
		game.setPlayers(players);
		City source=game.getKingsPosition();
		City target=null;
		for(City c:game.getAllCities()){
			if(c.getFirstChar()=='H')
				target=c;
		}
		System.out.println(game.getMap().howManyVertexPassed(source, target));
	}

	/**
	 * @return the kingTileList
	 */
	public List<PointsTile> getKingTileList() {
		return kingTileList;
	}
	
}
