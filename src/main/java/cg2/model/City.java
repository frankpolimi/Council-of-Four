/**
 * 
 */
package cg2.model;

import java.awt.Color;
import java.util.*;

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
	
	//i collegamenti saranno letti da file, perciò ci sarà una fase di creazione
	//del vettore collegamenti da parte dell'inizializzatore della partita.
	public City(String name, Color color, List<TileBonus> bonus){
		super();
		this.name=name;
		firstChar=name.charAt(0);
		//linkedCities = link;
		cityColor=color;
		/*for(TileBonus b: bonus){
			super.registerBonus(b);
		}*/
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
	 * @return the linkedCities
	 */
	/*public Set<City> getLinkedCities() {
		return linkedCities;
	}*/

	
}
