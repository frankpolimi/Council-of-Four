/**
 * 
 */
package model.market;

import java.io.Serializable;

import model.game.Player;

/**
 * @author Francesco Vetrò
 * generic type <T> can be an Assistant, a PolitcsCard or a BuildingLicense
 * no aggregation of elements can be performed
 */
public class MarketObject<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6266006920696407080L;
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
