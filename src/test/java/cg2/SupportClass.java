package cg2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import model.bonus.AssistantBonus;
import model.bonus.Bonus;
import model.bonus.CoinBonus;
import model.game.topology.City;

public class SupportClass 
{
	public static Color giveRandomColor()
	{
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r, g, b);
	}
	
	public static Set<City> citySetCreator(String s, String s2)
	{
		Set<City> cities=new HashSet<City>();
		cities.add(SupportClass.cityCreator(s));
		cities.add(SupportClass.cityCreator(s2));
		return cities;
	}
	
	public static Set<Bonus> bonusCreator(int n1, int n2)
	{
		Set<Bonus> bonuses=new HashSet<Bonus>();
		bonuses.add(new AssistantBonus(n1));
		bonuses.add(new CoinBonus(n2));
		return bonuses;
	}
	
	public static City cityCreator(String s)
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
}
