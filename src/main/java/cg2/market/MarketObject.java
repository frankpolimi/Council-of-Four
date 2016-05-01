/**
 * 
 */
package cg2.market;

import cg2.player.Player;

/**
 * @author Francesco Vetrò
 * generic type <T> can be an Assistant, a PolitcsCard or a BuildingLicense
 * no aggregation of elements can be performed
 */
public class MarketObject<T> {

	private final T object;
	private final Player sellingPlayer;
	private final int price;
	
	public MarketObject(T object, Player p, int price) {
		this.object = object;
		this.sellingPlayer = p;
		this.price = price;
	}

	/**
	 * @return the sellingPlayer
	 */
	public Player getSellingPlayer() {
		return sellingPlayer;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @return the object
	 */
	public T getObject() {
		return object;
	}
	
}
