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
	
	public final int DISCONNECTION_TIME;
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
	private Player currentPlayer;
	private int mainActionCounter;
	private int quickActionCounter;
	
	/**
	 * constructor for a game of "council of four"
	 * setup the game without the players by reading a configuration file
	 * @throws JDOMException when errors occurs during parsing from file
	 * @throws IOException when errors occurs during reading from file
	 */
	public Game(int disconnectionTime) throws JDOMException, IOException {
		MapMaker mp=new MapMaker();
		this.DISCONNECTION_TIME=disconnectionTime;
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

	/**
	 * randomize the map that will be used for the game
	 * and allows to select the right configuration file
	 * @param regionNum the number of regions that the game will contains
	 * @return the sequence of characters that represent the kind 
	 * 			of region that will be used for the game
	 */
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
	
	/**
	 * method that allows the server to setup the players
	 * in the game. this method will also initialize the game
	 * due to specifics: if only two players are participating,
	 * the game will setup differently rather than when 3 or more
	 * players are connected
	 * @param players the list of players connected to the game
	 */
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
		this.players.get(0).addEmporium(new Emporium(this.map.getVertexByKey("a"), this.players.get(0).getChosenColor()));
		this.players.get(0).addEmporium(new Emporium(this.map.getVertexByKey("b"), this.players.get(0).getChosenColor()));
		this.players.get(0).addEmporium(new Emporium(this.map.getVertexByKey("c"), this.players.get(0).getChosenColor()));
		this.players.get(0).addEmporium(new Emporium(this.map.getVertexByKey("d"), this.players.get(0).getChosenColor()));
		this.players.get(0).addEmporium(new Emporium(this.map.getVertexByKey("f"), this.players.get(0).getChosenColor()));
		this.getAllPermitsDecks().get(2).givePermit(this, this.getAllPermitsDecks().get(2).giveAFaceUpPermit(1));
		this.getAllPermitsDecks().get(2).givePermit(this, this.getAllPermitsDecks().get(2).giveAFaceUpPermit(1));
		this.getAllPermitsDecks().get(2).givePermit(this, this.getAllPermitsDecks().get(2).giveAFaceUpPermit(1));
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
			}while(mainActionCounter != 0 && quickActionCounter != 0);
			//this.setCurrentPlayer(); gestione dei turni in controller
		endOfGame = checkEndOfGame();
		}while(endOfGame);
	}*/
	
	/**
	 * method to initialize the game with the specific setup
	 * for 3 or more players. this method is also capable of
	 * disposing a sufficient number of game items so that
	 * the game can proceed without errors or slowly
	 */
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
	
	/**
	 * method that initialize the game when only two players 
	 * are connected specifically. as rules state, a number of
	 * random emporiums on random cities is built prior to the game
	 * this doesn't affect players because emporiums aren't owned by anyone
	 * but the player will follow the game rules and pay 1 extra assistants 
	 * for each emporium built on the city he tries to build on.  
	 */
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
	
	/**
	 * get the graph of the cities
	 * @return the graph of the cities
	 */
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
	
	/**
	 * this will allow the game to know how many
	 * players are left to the end of the phase,
	 * whether is the action phase, market phase
	 * or the end phase when all players perform 
	 * their last actions due to another player 
	 * building 10 emporiums
	 */
	public void decrementLastRemainingPlayers(){
		this.lastTurnRemainingPlayers--;
	}
	
	/**
	 * get the amount of players left that
	 * haven't jet performed an action
	 * @return the number of players waiting to perform an action
	 */
	public int getLastTurnRemainingPlayers(){
		return this.lastTurnRemainingPlayers;
	}

	/**
	 * checks if a color has already been used
	 * for a player so that the future built emporium
	 * isn't possessed by anyone 
	 * @param color the color to check if already used
	 * @return true if a color has already been used
	 * 			false otherwise
	 */
	private boolean isColorAlreadyCreated(Color color){
		boolean check=false;
		for(Player p:this.players){
			if(p.getChosenColor().equals(color)){
				check=true;
			}
		}
		return check;
	}
	
	/**
	 * method that allows the game to calculate the winner
	 * of the specific game
	 * @throws IllegalAccessException if problems occurred while invoking the method via reflection
	 * @throws IllegalArgumentException if problems occurred while invoking the method via reflection
	 * @throws InvocationTargetException if problems occurred while invoking the method via reflection
	 * @throws NoSuchMethodExceptionif a matching method is not found
	 * @throws SecurityException if access is denied 
	 * 					to the package of this class
	 */
	public void endOfTheGame() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		this.players.addAll(this.disconnectedPlayers);
		List<Player> copyList=new ArrayList<>(this.players);
		WinnerSelector winnerSelector=new WinnerSelector(copyList);
		this.gameState=new EndState(winnerSelector.getWinnerPlayer(),winnerSelector.getRanking());
		this.notifyObservers(new ModelChange(this));
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * this method allows to remove all used politic cards,
	 * add them to the politic deck in the game and then shuffle 
	 * the deck.
	 * @throws NullPointerException if the used politic deck is empty
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
	 * this method allows to add a councillor to the available 
	 * councillors that are in the game. this councillors can be used to perform
	 * all the actions that involves the election of a councillor
	 * @param councillor the councillor that was eliminated from a council
	 * 						and that will be available in the game
	 */
	public void addCouncillor(Councillor councillor)
	{
		this.avaliableCouncillors.add(councillor);
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * increment by 1 the amount of quick actions 
	 * available for a player
	 */
	public void incrementQuickActionCounter()
	{
		quickActionCounter++;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * decrement by 1 the amount of quick actions 
	 * available for a player
	 */
	public void decrementQuickActionCounter()
	{
		quickActionCounter--;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * increment by 1 the amount of main actions 
	 * available for a player
	 */
	public void incrementMainActionCounter()
	{
		mainActionCounter++;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * decrement by 1 the amount of main actions 
	 * available for a player
	 */
	public void decrementMainActionCounter()
	{
		mainActionCounter--;
	}

	
	/**
	 * @author Vitaliy Pakholko
	 * get the city where the king is placed
	 * @return the city where the king is placed
	 */
	public City getKingsPosition() {
		return kingsPosition;
	}

	/**
	 * @author Vitaliy Pakholko
	 * setup the position of the king. is used both when initializing
	 * and moving the king through the map
	 * @param kingsPosition the city where the king will be 
	 * 						placed after this method call
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
	public PoliticsDeck getPoliticsDeck() 
	{
		return politicsDeck;
	}
	
	/**
	 * get all the available councillors in the game
	 * @return the availble councillors in the game
	 */
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
	
	/**
	 * get only the regional councils present in the game
	 * @return the regional councils in the game
	 */
	public List<RegionalCouncil> getRegionalCouncils(){
		Set<Region> regions=this.getRegions();
		List<RegionalCouncil> regional=new ArrayList<>();
		for(Region r:regions){
			regional.add(r.getCouncil());
		}
		
		return regional;
	}
	
	/**
	 * get all the regional councils in the game and 
	 * the king's council
	 * @return all the councils
	 */
	public List<Council> getAllCouncils(){
		List<RegionalCouncil> regCouncils=this.getRegionalCouncils();
		List<Council> councils=new ArrayList<>();
		councils.addAll(regCouncils);
		councils.add(this.kingsCouncil);
		return councils;
	}

	/**
	 * get the list of all the cities available in the game
	 * @return the list of cities in each region
	 */
	public List<City> getAllCities()
	{
		List<City> list=new ArrayList<>();
		for(Region r:this.getRegions())
		{
			list.addAll(r.getCities());
		}
		return list;
	}
	
	/**
	 * get all the permits deck, one for each region
	 * @return all the permits deck in the game
	 */
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
	
	/**
	 * method that define the rule of evolution of the FSM
	 * that lies behind the game. the game evolves in the following 
	 * manner: 
	 * at the beginning the game allows the players to perform actions
	 * (all main actions must be used, quick ones can be skipped)
	 * when all players have performed their actions, the game goes into
	 * the first step of the market phase: each player select items that will
	 * be eventually bought by other players (a player can skip this phase).
	 * when all players are done selling items, the market evolves in the second 
	 * phase, the buying one: all players (randomized) will see a selection of objects (without
	 * the ones that they sold) that may buy. even this phase is not mandatory.
	 * then the game starts all over again till the end
	 */
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
		boolean assigned = false;;
		while(r.hasNext()){
			Region x = r.next();
			if(x.getCities().contains(builtOn) && 
					curr.getEmporiumsCitiesSet().containsAll(x.getCities())){
				Iterator<PointsTile> rti = regionTileList.iterator();
				List<PointsTile> eliminatedTiles=new ArrayList<>();
				while(rti.hasNext()){
					PointsTile prova=rti.next();
					RegionTile rt = (RegionTile)prova;
					if(rt.getRegion().getName().equals(x.getName()))
						if(!curr.getTilesOwned().contains(rt)){
							curr.addPointsTile(rt);
							//regionTileList.remove(rt);
							eliminatedTiles.add(rt);
							assigned  = true;
						}
				}
				regionTileList.removeAll(eliminatedTiles);
			}
		}
		
		List<City> sameColorCities = new ArrayList<>();
		for(City c : this.getAllCities())
			if(c.getCityColor().equals(builtOn.getCityColor()))
				sameColorCities.add(c);
		ArrayList<PointsTile> eliminatedTiles=new ArrayList<>();
		if(curr.getEmporiumsCitiesSet().containsAll(sameColorCities)){
			
			for(PointsTile pt2 : colorTileList){
				if(((ColorTile)pt2).getCityColor().equals(builtOn.getCityColor()) && 
						!curr.getTilesOwned().contains(pt2)){
					curr.addPointsTile(pt2);
					//colorTileList.remove(pt2);
					eliminatedTiles.add(pt2);
					assigned = true;
				}
			}
			colorTileList.removeAll(eliminatedTiles);
		}
		
		if(assigned)
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

	/**
	 * @return the dISCONNECTION_TIME
	 */
	public int getDISCONNECTION_TIME() {
		return DISCONNECTION_TIME;
	}

	/**
	 * @return the kingTileList
	 */
	public List<PointsTile> getKingTileList() {
		return kingTileList;
	}

	/**
	 * @return the colorTileList
	 */
	public List<PointsTile> getColorTileList() {
		return colorTileList;
	}

	/**
	 * @return the regionTileList
	 */
	public List<PointsTile> getRegionTileList() {
		return regionTileList;
	}
	
}
