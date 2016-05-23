/**
 * 
 */
package cg2.model;

import java.awt.Color;

import cg2.player.Player;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Emporium {
	private final Player player;
	private final City city;
	private final Color color;
	
	/**
	 * 
	 * @param p is the ref of the player that would to build. It can be null when the emporium is not linked with a player
	 * @param c is the city in which the emporium is built
	 * @param color is the color of the emporium. It's passed through getChosenColor() method of Player class, if this is linked with a player,
	 * else it's passed by the programmer.
	 * @throws NullPointerException if color or city is null
	 */
	
	//VALUTARE SE è IL CASO DI TOGLIERE IL REF DI PLAYER PER NON CREARE AMBIGUITà!!!
	public Emporium(Player p, City c, Color color){
		if(c==null||color==null){
			throw new NullPointerException("one either Color or city is null");
		}
		
		player=p;
		city = c;
		this.color=color;
	}

	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}



	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return the city
	 */
	public City getCity(){
		return city;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return city.toString();
	}
	
	
	
}
