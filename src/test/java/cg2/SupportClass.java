package cg2;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.jdom2.JDOMException;

import model.bonus.AssistantBonus;
import model.bonus.Bonus;
import model.bonus.CoinBonus;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
import model.game.Player;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.council.KingsCouncil;
import model.game.council.RegionalCouncil;
import model.game.politics.JollyPoliticsCard;
import model.game.politics.PoliticsCard;
import model.game.topology.City;
import model.game.topology.Region;

public abstract class SupportClass 
{
	public static Council councilCreatorByColors(Color c1, Color c2, Color c3, Color c4, String type)
	{
		Councillor con1=new Councillor(c1);
		Councillor con2=new Councillor(c2);
		Councillor con3=new Councillor(c3);
		Councillor con4=new Councillor(c4);
		ArrayBlockingQueue<Councillor> queue=new ArrayBlockingQueue<>(4);
		queue.offer(con1);
		queue.offer(con2);
		queue.offer(con3);
		queue.offer(con4);
		List<BuildingPermit> list=new ArrayList<>();
		switch(type)
		{
		case "King": return new KingsCouncil(queue);
		case "Regional":
		default: return new RegionalCouncil(queue, new PermitsDeck(list));
		}
	}
	
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
	
	public static Game gameWithPlayersCreator(String playerName1,String playerName2) throws JDOMException, IOException
	{
			List<Player> players=new ArrayList<>();
			Player p1=new Player(playerName1, 1);
			Player p2=new Player(playerName2, 2);
			players.add(p1);
			players.add(p2);
			Game game=new Game();
			game.setPlayers(players);
			game.setCurrentPlayer(p1);
			return game;
	
	}

	public static Region regionCreator() throws JDOMException, IOException {
		return gameWithPlayersCreator("p1", "p2").getRegions().iterator().next();
	}
	
	public static ArrayList<PoliticsCard> giveJollyHand()
	{
		ArrayList<PoliticsCard> list=new ArrayList<PoliticsCard>();
		list.add(new JollyPoliticsCard());
		list.add(new JollyPoliticsCard());
		list.add(new JollyPoliticsCard());
		list.add(new JollyPoliticsCard());
		return list;
	}
	
}
