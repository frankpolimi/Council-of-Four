/**
 * 
 */
package view;

import java.io.Serializable;


/**
 * @author francesco
 * class that is used to communicate from the client to the server
 * and the player has the ability to influence the state of the game.
 * there are different requests: 
 * ActionRequest, the player want to perform an action
 * MarketRequest, the player want to influence the market. 
 * 					this will be seen by the controller in two different ways
 * 					the first means that the player wants to sell something (MarketSellingState)
 * 					the second means that the player wants to buy something (MarketBuyingState
 * BonusRequest, the player requires that a specific bonus is applied
 * PermitRequest, the player requires that a specific permit is given to him
 */
public abstract class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8272737714020599087L;

	private final int ID;
	
	/**
	 * constructor
	 * @param ID the id of the client that sends the request
	 */
	public Request(int ID) {
		this.ID=ID;
	} 
	
	/**
	 * @return the id of the client that sent the request
	 */
	public int getID() {
		return ID;
	}
}
