/**
 * 
 */
package view;

import java.io.Serializable;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class MarketSellingState implements State, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2121727004582742362L;

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	/**
	 * this method will display the correct input when the game 
	 * reaches the first step of the market. this phase is 
	 * recognizable by the opportunity given to the player to sell
	 * something he possess
	 */
	@Override
	public void display() {
		System.out.println("Insert the action's name to perform");
		System.out.println("1. select a product you want to sell");
		System.out.println("2. pass to the next player");
		System.out.println("3. quit");
	}
	

}
