/**
 * 
 */
package model.game.topology;

import java.awt.Color;
import java.io.Serializable;
import java.util.*;

import model.bonus.*;
import model.bonus.bonusers.Bonusable;
import model.game.Emporium;
import model.game.Player;

/**
 * @author Emanuele Ricciardelli
 *	
 */
public class City  extends Bonusable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8246119752946533926L;
	private final String name;
	private final char firstChar;
	private final Color cityColor;
	private ArrayList<Emporium> emporiums;
	private final List<Bonus> bonusList;

	public City(String name, Color color, List<Bonus> bonus){
		super();
		this.name=name;
		firstChar=name.toUpperCase().charAt(0);
		cityColor=color;
		bonusList = bonus;
		emporiums=new ArrayList<>();
		/*
		for(Bonus b: bonus)
			this.registerBonus(b);*/
		 
	}
	
	/**
	 * This method add an emporium in this city. 
	 * @param player is the player that would to build the emporium
	 * @throws NullPointerException if the player is null
	 */
	public void addEmporium(Player player)
	{
		if(player==null){
			throw new NullPointerException("the player must be not null!");
		}
		
		Emporium e=new Emporium(this, player.getChosenColor());
		emporiums.add(e);
		player.addEmporium(e);
	}
	/**
	 * This one is used only for the 2-players initialization, because it's necessary to put random emporiums not linked with any player.
	 * @param color
	 * @throws NullPointerException if the color is null
	 */
	public void addEmporium(Color color)
	{
		if(color==null){
			throw new NullPointerException("The color must not be null");
		}
		Emporium e=new Emporium(this, color);
		emporiums.add(e);
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
	
	/**
	 * This method checks if a determinate player has built an emporium in this city
	 * @param player
	 * @return
	 */
	public boolean hasPlayerBuilt(Player player){
		
		for(Emporium e:this.emporiums){
			if(player.getEmporium().contains(e)){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "City [name=" + name + ", firstChar=" + firstChar + ", cityColor=" + cityColor + ", bonuses=" +bonusList+"]\n";
	}

	/**
	 * @author Francesco vetr�
	 * @return the bonuses applied to the permit
	 */
	public String displayBonus() {
		Iterator<Bonus> i = bonusList.iterator();
		String x = "";
		while(i.hasNext())
			x.concat(i.next().toString()+" ");
		return x;
	}

	public List<Bonus> getBonus() {
		return bonusList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonusList == null) ? 0 : bonusList.hashCode());
		result = prime * result + ((cityColor == null) ? 0 : cityColor.hashCode());
		result = prime * result + ((emporiums == null) ? 0 : emporiums.hashCode());
		result = prime * result + firstChar;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	*/
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object city) 
	{
		if(this.name.equals(((City) city).getName()))
			return true;
		return false;
	}
	
	/**
	 * @return the linkedCities
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	/*public Set<City> getLinkedCities() {
		return linkedCities;
	}*/

	
}
