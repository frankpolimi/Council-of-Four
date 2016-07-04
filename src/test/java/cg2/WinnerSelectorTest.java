package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.jdom2.JDOMException;
import org.junit.Test;
import java.util.*;

import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
import model.game.Player;
import model.game.WinnerSelector;

public class WinnerSelectorTest {

	@Test(expected=NullPointerException.class)
	public void testNullPointerExceptionConstructor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		new WinnerSelector(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsEmptyPlayerConstructor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Player> list=new ArrayList<>();
		new WinnerSelector(list);
	}
	
	@Test
	public void getSortedList() throws JDOMException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		List<Player> player=new ArrayList<>();
		Player p1=new Player("p1",1);
		Player p2=new Player("p2",2);
		Player p3=new Player("p3",3);
		p1.setPoints(10);
		p2.setPoints(12);
		p3.setPoints(9);
		player.add(p1);
		player.add(p2);
		player.add(p3);
		
		WinnerSelector selector=new WinnerSelector(player);
		List<Player> expected=new ArrayList<>();
		Player p11=new Player("p1",1);
		Player p22=new Player("p2",2);
		Player p33=new Player("p3",3);
		p11.setPoints(15);
		p22.setPoints(17);
		p33.setPoints(14);
		expected.add(p22);
		expected.add(p11);
		expected.add(p33);
		for(int i=0;i<selector.getRanking().size();i++){
			assertEquals(expected.get(i), selector.getRanking().get(i));
		}
	}
	
	@Test
	public void nobilityPointsAdditionWhenOnePlayerIsTheBest() throws JDOMException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//First has more points.
		List<Player> player=new ArrayList<>();
		Player p1=new Player("p1",1);
		Player p2=new Player("p2",2);
		Player p3=new Player("p3",3);
		p1.setNobilityPoints(12);
		p2.setNobilityPoints(5);
		p3.setNobilityPoints(2);
		player.add(p1);
		player.add(p2);
		player.add(p3);
		WinnerSelector winner=new WinnerSelector(player);
		List<Player> expected=new ArrayList<>();
		Player p11=new Player("p1",1);
		Player p22=new Player("p2",2);
		Player p33=new Player("p3",3);
		p11.setPoints(5);
		p22.setPoints(2);
		p33.setPoints(0);
		p11.setNobilityPoints(12);
		p22.setNobilityPoints(5);
		p33.setNobilityPoints(2);
		expected.add(p11);
		expected.add(p22);
		expected.add(p33);
		for(int i=0;i<expected.size();i++){
			assertEquals(expected.get(i),winner.getRanking().get(i));
		}
		
		assertEquals(expected.get(0),winner.getWinnerPlayer());
	}
	
	@Test
	public void nobilityPointsAddWhenTwoPlayersHaveTheSameFirst() throws JDOMException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<Player> player=new ArrayList<>();
		Player p1=new Player("p1",1);
		Player p2=new Player("p2",2);
		Player p3=new Player("p3",3);
		p1.setNobilityPoints(5);
		p2.setNobilityPoints(5);
		p3.setNobilityPoints(2);
		player.add(p1);
		player.add(p2);
		player.add(p3);
		WinnerSelector winner=new WinnerSelector(player);
		List<Player> expected=new ArrayList<>();
		Player p11=new Player("p1",1);
		Player p22=new Player("p2",2);
		Player p33=new Player("p3",3);
		p11.setPoints(5);
		p22.setPoints(5);
		p33.setPoints(0);
		p11.setNobilityPoints(5);
		p22.setNobilityPoints(5);
		p33.setNobilityPoints(2);
		expected.add(p11);
		expected.add(p22);
		expected.add(p33);
		for(int i=0;i<expected.size();i++){
			assertEquals(expected.get(i),winner.getRanking().get(i));
		}
		assertNotEquals(p33, winner.getWinnerPlayer());
	}
	
	@Test
	public void nobilityPointsAddWhenTwoPlayersHaveTheSameSecond() throws JDOMException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<Player> player=new ArrayList<>();
		Player p1=new Player("p1",1);
		Player p2=new Player("p2",2);
		Player p3=new Player("p3",3);
		p1.setNobilityPoints(5);
		p2.setNobilityPoints(2);
		p3.setNobilityPoints(2);
		player.add(p1);
		player.add(p2);
		player.add(p3);
		WinnerSelector winner=new WinnerSelector(player);
		List<Player> expected=new ArrayList<>();
		Player p11=new Player("p1",1);
		Player p22=new Player("p2",2);
		Player p33=new Player("p3",3);
		p11.setPoints(5);
		p22.setPoints(2);
		p33.setPoints(2);
		p11.setNobilityPoints(5);
		p22.setNobilityPoints(2);
		p33.setNobilityPoints(2);
		expected.add(p11);
		expected.add(p22);
		expected.add(p33);
		for(int i=0;i<expected.size();i++){
			assertEquals(expected.get(i),winner.getRanking().get(i));
		}
		assertEquals(expected.get(0),winner.getWinnerPlayer());
	}
	
	@Test
	public void permitsWhenOnlyOnePlayerHasMoreBuildingPermit() throws JDOMException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Game game=new Game(100000);
		List<Player> player=new ArrayList<>();
		Player p1=new Player("p1",1);
		Player p2=new Player("p2",2);
		Player p3=new Player("p3",3);
		player.add(p1);
		PermitsDeck deck=game.getAllPermitsDecks().get(0);
		deck.faceUpInit();
		BuildingPermit permit=deck.giveAFaceUpPermit(1);
		p1.addBuildingPermit(permit);
		player.add(p2);
		player.add(p3);
		WinnerSelector winner=new WinnerSelector(player);
		List<Player> expected=new ArrayList<>();
		Player p11=new Player("p1",1);
		Player p22=new Player("p2",2);
		Player p33=new Player("p3",3);
		p11.setPoints(8);
		p11.addBuildingPermit(permit);
		p22.setPoints(5);
		p33.setPoints(5);
		expected.add(p11);
		expected.add(p22);
		expected.add(p33);
		for(int i=0;i<expected.size();i++){
			assertEquals(expected.get(i),winner.getRanking().get(i));
		}
		assertEquals(expected.get(0),winner.getWinnerPlayer());
	}
	
	@Test
	public void permitsWhenManyPlayersHaveMoreBuildingPermit() throws JDOMException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Game game=new Game(10000000);
		List<Player> player=new ArrayList<>();
		Player p1=new Player("p1",1);
		Player p2=new Player("p2",2);
		Player p3=new Player("p3",3);
		player.add(p1);
		PermitsDeck deck=game.getAllPermitsDecks().get(0);
		deck.faceUpInit();
		BuildingPermit permit=deck.giveAFaceUpPermit(1);
		p1.addBuildingPermit(permit);
		player.add(p2);
		p2.addBuildingPermit(permit);
		player.add(p3);
		WinnerSelector winner=new WinnerSelector(player);
		List<Player> expected=new ArrayList<>();
		Player p11=new Player("p1",1);
		Player p22=new Player("p2",2);
		Player p33=new Player("p3",3);
		p11.setPoints(5);
		p11.addBuildingPermit(permit);
		p22.setPoints(5);
		p22.addBuildingPermit(permit);
		p33.setPoints(5);
		expected.add(p11);
		expected.add(p22);
		expected.add(p33);
		for(Player p:expected){
			assertEquals(p,winner.getRanking().stream().filter(e->e.getPlayerID()==p.getPlayerID()).findFirst().get());
		}
	}

}
