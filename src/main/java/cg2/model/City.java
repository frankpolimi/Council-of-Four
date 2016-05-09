/**
 * 
 */
package cg2.model;

import java.awt.Color;
import java.util.*;

import bonus.AssistantBonus;
import bonus.Bonus;
import bonus.CoinBonus;
import bonus.MainActionBonus;
import bonus.NobilityBonus;
import bonus.PointBonus;
import bonus.TileBonus;
import bonus.bonusers.Bonusable;
import cg2.player.Player;

/**
 * @author Emanuele Ricciardelli
 *	
 */
public class City  extends Bonusable{
	private final String name;
	private final char firstChar;
	private final Color cityColor;
	//private final Set<City> linkedCities;
	private ArrayList<Emporium> emporiums;
	private final Set<TileBonus> bonusList;
	
	//i collegamenti saranno letti da file, perciò ci sarà una fase di creazione
	//del vettore collegamenti da parte dell'inizializzatore della partita.
	public City(String name, Color color, Set<TileBonus> bonus){
		super();
		this.name=name;
		firstChar=name.charAt(0);
		//linkedCities = link;
		cityColor=color;
		bonusList = bonus;
	}
	
	public void addEmporium(Player player)
	{
		Emporium e=new Emporium(player, this);
		emporiums.add(e);
		player.addEmporium(e);
	}
	

	/**
	 * @return the emporiums
	 */
	public ArrayList<Emporium> getEmporiums() {
		return emporiums;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the firstChar
	 */
	public char getFirstChar() {
		return firstChar;
	}

	/**
	 * @return the cityColor
	 */
	public Color getCityColor() {
		return cityColor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "City [name=" + name + ", firstChar=" + firstChar + ", cityColor=" + cityColor + "]";
	}

	/**
	 * @author Francesco vetrò
	 * @return the bonuses applied to the permit
	 */
	public String displayBonus() {
		Iterator<TileBonus> i = bonusList.iterator();
		String x = "";
		while(i.hasNext()){
			Bonus b = (Bonus) i.next();
			if(b.getClass().equals(AssistantBonus.class))
				x.concat(((AssistantBonus)b).toString());
			else if(b.getClass().equals(CoinBonus.class))
				x.concat(((CoinBonus)b).toString());
			else if(b.getClass().equals(MainActionBonus.class))
				x.concat(((MainActionBonus)b).toString());
			else if(b.getClass().equals(NobilityBonus.class))
				x.concat(((NobilityBonus)b).toString());
			else if(b.getClass().equals(PointBonus.class))
				x.concat(((PointBonus)b).toString());
			x.concat(" ");
		}
		return x;
	}

	/**
	 * @return the linkedCities
	 */
	/*public Set<City> getLinkedCities() {
		return linkedCities;
	}*/

	
}
