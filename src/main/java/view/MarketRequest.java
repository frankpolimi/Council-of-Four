/**
 * 
 */
package view;

import model.market.MarketObject;

/**
 * @author Francesco Vetro'
 * @param <T> is the generic type of the market object
 * 				that a player can sell or buy from the market
 */
public class MarketRequest<T> extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5046370217035631986L;
	private MarketObject<T> object;
	
	
	/**
	 * construct the object that will be sent to the controller
	 * to add it to the market
	 * @param obj the new sellable object for the market
	 */
	public MarketRequest(MarketObject<T> obj, int id)  {
		super(id);
		this.object = obj;
	}
	
	/**
	 * get the object for the market
	 * @return the market object
	 */
	public MarketObject<T> getObject(){
		return object;
	}

}
