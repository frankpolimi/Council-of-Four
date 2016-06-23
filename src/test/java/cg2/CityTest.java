package cg2;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import org.jdom2.JDOMException;
import org.junit.Ignore;
import org.junit.Test;
import model.game.Emporium;
import model.game.Player;
import model.game.topology.City;

public class CityTest 
{
	City city;
	Player player;
	Color color2=SupportClass.giveRandomColor();
	
	public void CityTestSetup()
	{
		city=SupportClass.cityCreator("Milano");
		try {
			player = new Player("Asdrubale", 12345);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
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
		this.CityTestSetup();
		city.addEmporium(player);
		city.addEmporium(player);
	}
	
	@Test
	public void testAddEmporiumPlayer() 
	{

		this.CityTestSetup();
		Emporium emporium= new Emporium(city, player.getChosenColor());
		assertFalse(city.getEmporiums().contains(emporium));
		city.addEmporium(player);
		assertTrue(city.getEmporiums().contains(emporium));

	}
	
	@Test
	public void testHasPlayerBuilt()
	{
		this.CityTestSetup();
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

	}
	
	@Test
	public void testAddEmporiumColor()
	{
		this.CityTestSetup();
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
		this.CityTestSetup();
		city.addEmporium(color2);
		city.addEmporium(color2);
	}
	
	@Test
	public void testGetFirstChar() 
	{
		this.CityTestSetup();
		assertEquals(city.getFirstChar(), 'M');
	}
}
