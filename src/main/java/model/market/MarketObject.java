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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MarketObject [object=" + object + "\n, sellingPlayer=" + sellingPlayer + "\n, price=" + price + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + price;
		result = prime * result + ((sellingPlayer == null) ? 0 : sellingPlayer.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarketObject other = (MarketObject) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (price != other.price)
			return false;
		if (sellingPlayer == null) {
			if (other.sellingPlayer != null)
				return false;
		} else if (!sellingPlayer.equals(other.sellingPlayer))
			return false;
		return true;
	}
	
	
	
}
