package cg2;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import model.game.ColorTile;

public class ColorTileTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAmountBelowZero() {
		new ColorTile(-13, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testColorNull(){
		new ColorTile(1, null);
	}
	
	@Test
	public void testCorrectConstructor(){
		ColorTile t = new ColorTile(1, Color.BLUE);
		assertEquals(1, t.getVPs());
		assertEquals(Color.BLUE, t.getCityColor());
	}

}
