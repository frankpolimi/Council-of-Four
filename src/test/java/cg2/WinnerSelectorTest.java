package cg2;


import java.io.IOException;
import java.util.*;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.game.Player;
import model.game.WinnerSelector;

public class WinnerSelectorTest{
	
	 @Test(expected=NullPointerException.class)
	 public void nullPlayerTest(){
		 new WinnerSelector(null);	 
	 }
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void emptyPlayerTest(){
		 List<Player> players=new ArrayList<Player>();
		 new WinnerSelector(players);
	 }
	 
	 @Test
	 public void playersWithDifferentPoints() throws JDOMException, IOException{
		 Player p1=new Player("a",1);
		 Player p2=new Player("b",2);
		 Player p3=new Player("c",3);
		 p1.setNobilityPoints(20);
		 p2.setNobilityPoints(2);
		 p3.setNobilityPoints(1);
	 }
	 
}
