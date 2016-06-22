package cg2;

import static org.junit.Assert.*;
import java.io.IOException;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.bonus.CoinBonus;
import model.game.Game;

public class CoinBonusTest 
{
	Game game;

	public void CoinBonusSetup()
	{
		try {
			game=SupportClass.gameWithPlayersCreator("G1", "G2","G3","G4");
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateCoinBonusCorrect() {
		Integer i = new Integer(12);
		CoinBonus b = new CoinBonus(i);
		assertEquals(b.getAmount(), i);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateCoinBonusWithNull(){
		new CoinBonus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateCoinBonusBelowZero(){
		Integer i = new Integer(-54);
		new CoinBonus(i);
	}

	@Test
	public void testAssignBonusToPlayer()
	{
		this.CoinBonusSetup();
		CoinBonus b = new CoinBonus(12);
		int coinG1 = game.getCurrentPlayer().getCoins();
		int coinG2 = game.getPlayers().get(1).getCoins();
		b.update(game);
		assertEquals(coinG1+12, game.getCurrentPlayer().getCoins());
		assertEquals(coinG2, game.getPlayers().get(1).getCoins());
	}
}
