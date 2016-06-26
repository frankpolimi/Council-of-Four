package cg2;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import controller.BonusChange;
import model.bonus.Bonus;
import model.bonus.CoinBonus;
import model.game.topology.City;

public class BonusChangeTest {

	@Test
	public void testConstructor() {
		List<Bonus> b = new ArrayList<>();
		b.add(new CoinBonus(1));
		City c = new City("name", Color.red, b);
		BonusChange bc = new BonusChange(1);
		bc.addCityBonus(c);
		assertEquals(b, c.getBonus());
	}
	
	@Test
	public void testAddBonus(){
		List<Bonus> b = new ArrayList<>();
		b.add(new CoinBonus(1));
		City c = new City("name", Color.red, b);
		BonusChange bc = new BonusChange(1);
		bc.addCityBonus(c);
		assertEquals(b, c.getBonus());
	}

}
