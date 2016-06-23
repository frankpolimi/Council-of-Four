package cg2;

import static org.junit.Assert.*;
import java.awt.Color;
import java.util.ArrayList;
import org.junit.Test;
import model.bonus.AssistantBonus;
import model.bonus.Bonus;
import model.bonus.CoinBonus;
import model.game.Emporium;
import model.game.topology.City;

public class EmporiumTest 
{
	ArrayList<Bonus> bonusList;

	@Test(expected = NullPointerException.class)
	public void testNullConstructor(){
		new Emporium(null, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCityNull() {
		new Emporium(null, Color.BLUE);
	}
	
	@Test(expected = NullPointerException.class)
	public void testColorNull() {
		ArrayList<Bonus> b = new ArrayList<>();
		b.add(new CoinBonus(1));
		new Emporium(new City("city", Color.BLACK, b), null);
	}
	
	@Test
	public void testNotEquals(){
		bonusList = new ArrayList<>();
		bonusList.add(new CoinBonus(1));
		Emporium e1 = new Emporium(new City("city", Color.BLACK, bonusList), Color.BLUE);
		bonusList.clear();
		bonusList.add(new AssistantBonus(1));
		Emporium e2 = new Emporium(new City("city2", Color.BLUE, bonusList), Color.DARK_GRAY);
		assertNotEquals(e1, e2);
	}
	
	@Test
	public void testEquals(){
		bonusList = new ArrayList<>();
		bonusList.add(new CoinBonus(1));
		Emporium e1 = new Emporium(new City("city", Color.BLACK, bonusList), Color.BLUE);
		Emporium e2 = new Emporium(new City("city", Color.BLACK, bonusList), Color.BLUE);
		assertEquals(e1, e2);
	}
	
	@Test
	public void testColor(){
		bonusList = new ArrayList<>();
		bonusList.add(new CoinBonus(1));
		Emporium e1 = new Emporium(new City("city", Color.BLACK, bonusList), Color.BLUE);
		assertEquals(Color.BLUE, e1.getColor());
	}
	
	@Test
	public void testCity(){
		bonusList = new ArrayList<>();
		bonusList.add(new CoinBonus(1));
		City c = new City("city", Color.BLACK, bonusList);
		Emporium e1 = new Emporium(c, Color.BLUE);
		assertEquals(c, e1.getCity());
	}

}
