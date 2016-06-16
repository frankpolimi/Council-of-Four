package cg2;

import static org.junit.Assert.*;
import java.io.IOException;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.bonus.CoinBonus;
import model.game.Game;

public class CoinBonusTest {

	@Test
	public void testCreateCoinBonusCorrect() {
		Integer i = new Integer(12);
		CoinBonus b = new CoinBonus(i);
		assertEquals(b.getAmount(), i);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreateCoinBonusWithNull(){
		CoinBonus b = new CoinBonus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateCoinBonusBelowZero(){
		Integer i = new Integer(-54);
		CoinBonus b = new CoinBonus(i);
	}
	
	@Test
	public void testAssignBonusToPlayer(){
		try {
			Game g = SupportClass.gameWithPlayersCreator("G1", "G2","G3","G4");
			CoinBonus b = new CoinBonus(12);
			int coinG1 = g.getCurrentPlayer().getCoins();
			int coinG2 = g.getPlayers().get(1).getCoins();
			b.update(g);
			assertEquals(coinG1+12, g.getCurrentPlayer().getCoins());
			assertEquals(coinG2, g.getPlayers().get(1).getCoins());
		} catch (JDOMException e) {
			e.printStackTrace();
			fail("JDome");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO");
		}
	}
}
