package model.game;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;



public class WinnerSelector{
	private List<Player> players;
	
	/**
	 * constructor for the utility that calculate the 
	 * winning player of the game
	 * @param players the player that participated to game
	 * 			both still connected and disconnected through the
	 * 			game
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public WinnerSelector(List<Player> players) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if(players==null)
			throw new NullPointerException("Players must not be null");
		if(players.isEmpty()){
			throw new IllegalArgumentException("Players must not be empty");
		}
		this.players=players;
		this.countingPoints();
	}
	
	/**
	 * method that counts the final points of all the players.
	 * @throws IllegalAccessException if problems occurred while invoking the method via reflection
	 * @throws IllegalArgumentException if problems occurred while invoking the method via reflection
	 * @throws InvocationTargetException if problems occurred while invoking the method via reflection
	 * @throws NoSuchMethodExceptionif a matching method is not found
	 * @throws SecurityException if access is denied 
	 * 					to the package of this class
	 */
	private void countingPoints() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		final int FIRSTNOBILITYPOINTS=5;
		final int SECONDNOBILITYPOINTS=2;
		final int PERMITSPOINTS=3;
		final Method NOBILITYMETHOD=Player.class.getMethod("getNobilityPoints", (Class<?>[])null);
		final Method PERMITSMETHOD=Player.class.getMethod("howManyBuildingPermitsOwned", (Class<?>[])null);
		
		this.addTilesPoints();
		//Nobility points
		Player firstNobility=this.getMax(NOBILITYMETHOD);
		List<Player> firsts=this.sameLevel(firstNobility, NOBILITYMETHOD);
		firstNobility.setPoints(firstNobility.getPoints()+FIRSTNOBILITYPOINTS);
		if(firsts.size()>0){
			for(Player p:firsts){
				p.setPoints(p.getPoints()+FIRSTNOBILITYPOINTS);
			}
		}else{
			this.players.remove(firstNobility);
			Player secondNobility=this.getMax(NOBILITYMETHOD);
			secondNobility.setPoints(secondNobility.getPoints()+SECONDNOBILITYPOINTS);
			List<Player> seconds=this.sameLevel(secondNobility, NOBILITYMETHOD);
			if(seconds.size()>0){
				for(Player p:seconds){
					p.setPoints(p.getPoints()+SECONDNOBILITYPOINTS);
				}
			}
			this.players.add(firstNobility);
		}
		//Permits Points
		Player firstPermits=this.getMax(PERMITSMETHOD);
		List<Player> others=this.sameLevel(firstPermits, PERMITSMETHOD);
		if(others.isEmpty())
			firstPermits.setPoints(firstPermits.getPoints()+PERMITSPOINTS);
	}
	
	/**
	 * This method returns the winner of the match basing of the class calculations.
	 * @return the winner
	 * @throws IllegalAccessException if problems occurred while invoking the method via reflection
	 * @throws IllegalArgumentException if problems occurred while invoking the method via reflection
	 * @throws InvocationTargetException if problems occurred while invoking the method via reflection
	 * @throws NoSuchMethodExceptionif a matching method is not found
	 * @throws SecurityException if access is denied 
	 * 					to the package of this class
	 */
	public Player getWinnerPlayer() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		final Method POINTSMETHOD=Player.class.getMethod("getPoints", (Class<?>[])null);
		final Method ASSISTANTANDCARDSMETHOD=Player.class.getMethod("howManyAssistansAndCardsOwned", (Class<?>[])null);
		//Winning decision
		Player theFirstOne=this.getMax(POINTSMETHOD);
		List<Player> others=this.sameLevel(theFirstOne, POINTSMETHOD);
		if(others.size()>0){
			return this.getMax(ASSISTANTANDCARDSMETHOD);
		}else{
			return theFirstOne;
		}
	}
	
	/**
	 * This methods returns the ranking calculated by the class
	 * @return the final ranking
	 */
	public List<Player> getRanking(){
		//sorting by Victory Points
		this.players.sort(new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				if(o1.getPoints()>o2.getPoints())
					return -1;
				if(o1.getPoints()==o2.getPoints())
					return 0;
				else
					return 1;
			}

		});

		return this.players;
	}
	
	/**
	 * This method adds the tiles' point to the respective owner.
	 */
	private void addTilesPoints(){
		for(Player p:this.players){
			Set<PointsTile> tiles=p.getTilesOwned();
			for(PointsTile tile:tiles){
				p.setPoints(p.getPoints()+tile.getVPs());
			}
		}
	}
	
	/**
	 * This method gets the player the has the max of something like "the player who has the max number of coins, or
	 * max number of nobility points and so on".
	 * @param method is the what decide what kind of parameter should be analyzed for finding the player
	 * @return the Player who has the max of the parameter passed.
	 * @throws IllegalAccessException if there is a problem with the method's reflection so read the documentation about it
	 * @throws IllegalArgumentException if there is a problem with the method's reflection so read the documentation about it
	 * @throws InvocationTargetException if there is a problem with the method's reflection so read the documentation about it
	 */
	private Player getMax(Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(method==null){
			throw new NullPointerException("the method passed is null");
		}
		
		Player max=this.players.get(0);
		for(int i=1;i<this.players.size();i++){
			Player player=this.players.get(i);
			if((int)method.invoke(player, (Object[])null)>(int)method.invoke(max, (Object[])null)){
				max=player;
			}
		}
		return max;
	}
	
	
	/**
	 * This method returns a list of player that has the same level of points about different things explained by the method passed
	 * like "the players which has the same level of coins, or nobility points or victory points and so on
	 * @param player indicates the player for the comparison
	 * @param method indicates the parameter for apply the comparison
	 * @return the list of player that has the same amount of points specified in the method
	 * @throws IllegalAccessException if there is a problem with the method's reflection so read the documentation about it
	 * @throws IllegalArgumentException if there is a problem with the method's reflection so read the documentation about it
	 * @throws InvocationTargetException if there is a problem with the method's reflection so read the documentation about it
	 */
	private List<Player> sameLevel(Player player, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		this.players.remove(player);
		List<Player> sameLevel=new ArrayList<>();
		for(Player p:this.players){
			if((int)method.invoke(player, (Object[])null)==(int)method.invoke(p, (Object[])null))
				sameLevel.add(p);
		}
		this.players.add(player);
		
		return sameLevel;
	}

	/*public static void main(String[]args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, JDOMException, IOException{
		
		Player p1=new Player("Mario", 1);
		Player p2=new Player("Marco",2);
		Player p3=new Player("Luigi",3);
		p1.setNobilityPoints(10);
		p2.setNobilityPoints(15);
		p3.setNobilityPoints(10);
		List<Player> players=new ArrayList<>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		WinnerSelector ws=new WinnerSelector(players);
		ws.getRanking();
		ws.getWinnerPlayer();
		
		
		
	}*/
}

