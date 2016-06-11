package cg2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.game.Game;
import model.game.RegionTile;
import model.game.council.Council;
import model.game.topology.Region;

public class RegionTileTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAmountBelowZero() {
		new RegionTile(-15, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAmountZero() {
		new RegionTile(0, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testRegionNull(){
		new RegionTile(1, null);
	}
	
	@Test
	public void testConstructor(){
		try {
			Region r = SupportClass.regionCreator();
			RegionTile rt = new RegionTile(1, r);
			assertEquals(r, rt.getRegion());
			assertEquals(1, rt.getVPs());
		} catch (JDOMException | IOException e) {
			fail("Game creation errors");
			e.printStackTrace();
		}
	}
}
