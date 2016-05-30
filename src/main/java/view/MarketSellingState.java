/**
 * 
 */
package view;

/**
 * @author francesco
 *
 */
public class MarketSellingState implements State {

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	
	@Override
	public void display() {
		System.out.println("Insert the action's name to perform");
		System.out.println("1. "+Commands.ADD_PRODUCT);
		System.out.println("2. "+Commands.TERMINATE_SELLING);
		System.out.println("3. quit");
	}
	

}
