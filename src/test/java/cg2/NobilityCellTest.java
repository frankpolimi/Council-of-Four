package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.bonus.Bonus;
import model.bonus.CoinBonus;
import model.game.Game;
import model.game.NobilityCell;

public class NobilityCellTest {

	@Test(expected = NullPointerException.class)
	public void testBonusListNull() {
		new NobilityCell(null);
	}
	
	@Test
	public void testConstructor(){
		ArrayList<Bonus> b = new ArrayList<>();
		b.add(new CoinBonus(2));
		NobilityCell nc = new NobilityCell(b);
		try {
			Game g = SupportClass.gameWithPlayersCreator("g1", "g2");
			int c = g.getCurrentPlayer().getCoins();
			nc.applyBonus(g);
			assertEquals(c+2, g.getCurrentPlayer().getCoins());
		} catch (JDOMException | IOException e) {
			fail("Game creation Errors");
			e.printStackTrace();
		}
		
	}
}
