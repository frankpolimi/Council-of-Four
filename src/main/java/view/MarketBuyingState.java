/**
 * 
 */
package view;


import java.util.List;

import model.market.*;
/**
 * @author Emanuele Ricciardelli
 *
 */
public class MarketBuyingState implements State {

	/* (non-Javadoc)
	 * @see view.State#display()
	 */
	@Override
	public void display() {
		System.out.println("Select the action to perform");
		System.out.println("1. Show the avaliable objects to sell");
		System.out.println("2. Pass to the next player");
	}

}
