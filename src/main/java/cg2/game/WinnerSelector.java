package cg2.game;

import cg2.model.PointsTile;
import cg2.player.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class WinnerSelector {
	private List<Player> players;
	
	public WinnerSelector(List<Player> players) {
		this.players=players;
	}
	
	public Player getWinnerPlayer() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		final int FIRSTNOBILITYPOINTS=5;
		final int SECONDNOBILITYPOINTS=2;
		final int PERMITSPOINTS=3;
		final Method NOBILITYMETHOD=Player.class.getMethod("getNobilityPoints", (Class<?>[])null);
		final Method PERMITSMETHOD=Player.class.getMethod("howManyBuildingPermitsOwned", (Class<?>[])null);
		final Method POINTSMETHOD=Player.class.getMethod("getPoints", (Class<?>[])null);
		final Method ASSISTANTANDCARDSMETHOD=Player.class.getMethod("howManyAssistansAndCardsOwned", (Class<?>[])null);
		
		this.addTilesPoints();
		//Nobility points
		Player firstNobility=this.getMax(NOBILITYMETHOD);
		firstNobility.setPoints(firstNobility.getPoints()+FIRSTNOBILITYPOINTS);
		List<Player> firsts=this.sameLevel(firstNobility, NOBILITYMETHOD);
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
		firstPermits.setPoints(firstPermits.getPoints()+PERMITSPOINTS);
		//Winning decision
		Player theFirstOne=this.getMax(POINTSMETHOD);
		List<Player> others=this.sameLevel(theFirstOne, POINTSMETHOD);
		if(others.size()>0){
			others.add(theFirstOne);
			return this.getMax(ASSISTANTANDCARDSMETHOD);
		}else{
			return theFirstOne;
		}
		
	}
	
	private void addTilesPoints(){
		for(Player p:this.players){
			Set<PointsTile> tiles=p.getTilesOwned();
			for(PointsTile tile:tiles){
				p.setPoints(p.getPoints()+tile.getVPs());
			}
		}
	}
	
	private Player getMax(Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Player max=this.players.get(0);
		for(int i=1;i<this.players.size();i++){
			Player player=this.players.get(i);
			if((int)method.invoke(player, (Object[])null)>(int)method.invoke(max, (Object[])null)){
				max=player;
			}
		}
		return max;
	}
	
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

}

