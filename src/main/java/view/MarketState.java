/**
 * 
 */
package view;

/**
 * @author francesco
 *
 */
public class MarketState implements State {

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	
	@Override
	public void display() {
		System.out.println("Insert the action's name to perform");
		System.out.println("- "+Commands.ADD_PRODUCT);
		System.out.println("- "+Commands.TERMINATE_SELLING);
	}
	

}
