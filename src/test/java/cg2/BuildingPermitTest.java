package cg2;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import model.game.BuildingPermit;
import model.game.topology.City;

public class BuildingPermitTest 
{

	@Test(expected=NullPointerException.class)
	public void testBuildingPermitNull() 
	{
		new BuildingPermit(null, null);
	}
	
	
	@Test
	public void testGetFirstChars()
	{
		BuildingPermit permit=new BuildingPermit(SupportClass.citySetCreator("Milano","Torino" ), SupportClass.bonusCreator(2,3));
		Set<Character> firstChars=new HashSet<>();
		firstChars.add('M');
		assertNotEquals(firstChars, permit.getFirstChars());
		firstChars.add('T');
		assertEquals(permit.getFirstChars(),firstChars);
		firstChars.clear();
		firstChars.add('M');
		firstChars.add('H');
		assertNotEquals(firstChars, permit.getFirstChars());
	}

	@Test
	public void testGetBuildingAvaliableCities()
	{
		Set<City> cities=SupportClass.citySetCreator("Milano", "Torino");
		BuildingPermit permit=new BuildingPermit(cities, SupportClass.bonusCreator(2,3));
		assertEquals(permit.getBuildingAvaliableCities(), cities);
		cities=SupportClass.citySetCreator("Torino", "Roma");
		assertNotEquals(permit.getBuildingAvaliableCities(), cities);
	}

}
