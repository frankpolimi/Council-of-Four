package cg2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.bonus.CoinBonus;
import model.game.Game;
import model.game.Player;

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
	}
}
