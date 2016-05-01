/**
 * 
 */
package cg2.model;

import cg2.player.Player;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Emporium {
	private final Player player;
	private final City city;
	
	public Emporium(Player p, City c){
		player=p;
		city = c;
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
	
	
	
}
