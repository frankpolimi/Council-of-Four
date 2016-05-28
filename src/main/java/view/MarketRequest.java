/**
 * 
 */
package view;

import model.market.MarketObject;

/**
 * @author francesco
 * @param <T>
 *
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
	public MarketRequest(MarketObject<T> obj)  {
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
