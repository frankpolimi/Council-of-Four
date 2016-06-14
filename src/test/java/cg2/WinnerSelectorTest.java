package cg2;

import java.util.*;

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
	 
}
