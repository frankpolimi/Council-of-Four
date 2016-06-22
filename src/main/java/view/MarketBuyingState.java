/**
 * 
 */
package view;

import java.io.Serializable;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class MarketBuyingState implements State, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5936770799532000255L;

	/* (non-Javadoc)
	 * @see view.State#display()
	 */
	/**
	 * this method will display the correct input when the game 
	 * reaches the second step of the market. this phase is 
	 * recognizable by the opportunity given to the player to buy
	 * something from the market
	 */
	@Override
	public void display() {
		System.out.println("Select the action to perform");
		System.out.println("1. Show the avaliable objects to sell");
		System.out.println("2. Pass to the next player");
		System.out.println("3. quit");
	}

}
