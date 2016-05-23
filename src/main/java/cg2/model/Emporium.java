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
	
	public Emporium(Player p, City c){
		player=p;
		city = c;
		color=p.getChosenColor();
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
