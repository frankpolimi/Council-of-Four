package cg2;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import org.junit.Ignore;
import org.junit.Test;
import model.bonus.AssistantBonus;
import model.bonus.Bonus;
import model.bonus.CoinBonus;
import model.game.Emporium;
import model.game.Player;
import model.game.topology.City;

public class CityTest 
{
	Color color2=SupportClass.giveRandomColor();
	public City cityCreator(String s)
	{
		Color color=SupportClass.giveRandomColor();
		Bonus bonus=new AssistantBonus(3);
		Bonus bonus2=new CoinBonus(2);
		ArrayList<Bonus> list=new ArrayList<Bonus>();
		list.add(bonus);
		list.add(bonus2);
		City city=new City(s, color,list);
		city.addEmporium(SupportClass.giveRandomColor());
		return city;
	}

	@Test(expected=NullPointerException.class)
	public void testCityNull() 
	{
		new City(null,null,null);
	}

	
	@Ignore
	@Test(expected=IllegalStateException.class)
	public void testAddEmporiumPlayerSamePlayerTwice() 
	{
		
		Player player;
		try {
			City city=this.cityCreator("Milano");
			player = new Player("Asdrubale", 12345);
			city.addEmporium(player);
			city.addEmporium(player);
		} catch (JDOMException e) {
			assertTrue(true);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(true);
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddEmporiumPlayer() 
	{
		
		Player player;
		try {
			City city=this.cityCreator("Milano");
			player = new Player("Asdrubale", 12345);
			Emporium emporium= new Emporium(city, player.getChosenColor());
			assertFalse(city.getEmporiums().contains(emporium));
			city.addEmporium(player);
			assertTrue(city.getEmporiums().contains(emporium));
		} catch (JDOMException e) {
			assertTrue(true);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(true);
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testHasPlayerBuilt()
	{
		Player player;
		try {
			City city=this.cityCreator("Milano");
			player = new Player("Asdrubale", 12345);
			assertFalse(city.hasPlayerBuilt(player));
			city.addEmporium(player);
			assertTrue(city.hasPlayerBuilt(player));
			for(Emporium e:city.getEmporiums())	
			{
				assertFalse(e.getColor().equals(color2));
			}
			boolean built=false;
			city.addEmporium(color2);
			for(Emporium e:city.getEmporiums())
			{
				built=built||(e.getColor().equals(color2));
			}
			assertTrue(built);
		}catch (JDOMException e) {
			assertTrue(true);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(true);
			e.printStackTrace();
		}

	}
	
	@Test
	public void testAddEmporiumColor()
	{

			City city=this.cityCreator("Milano");
			for(Emporium e:city.getEmporiums())	
			{
				assertFalse(e.getColor().equals(color2));
			}
			boolean built=false;
			city.addEmporium(color2);
			for(Emporium e:city.getEmporiums())
			{
				built=built||(e.getColor().equals(color2));
			}
			assertTrue(built);
	}
	
	@Ignore
	@Test(expected=IllegalStateException.class)
	public void testAddEmporiumColorSameColorTwice()
	{
		City city=this.cityCreator("Milano");
		city.addEmporium(color2);
		city.addEmporium(color2);
	}
	
	@Test
	public void testGetFirstChar() 
	{
		City city=this.cityCreator("Milano");
		assertEquals(city.getFirstChar(), 'M');
	}
}
