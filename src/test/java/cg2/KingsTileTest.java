package cg2;

import static org.junit.Assert.*;

import org.junit.Test;

import model.game.KingTile;

public class KingsTileTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAmountBelowZero() {
		new KingTile(-12);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAmountZero() {
		new KingTile(0);
	}
	
	@Test
	public void testAmountCorrect() {
		new KingTile(15);
	}

}
