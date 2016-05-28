/**
 * 
 */
package view;

import model.game.Game;

/**
 * @author Francesco Vetr�
 *
 */
public class StartState implements State {
	
	private int actionType;
	private int selection;
	
	/**
	 * display to the view the first selection's level
	 * that will lead to perform an action
	 */
	@Override
	public void display() {
		System.out.println("Select the action type to perform");
		System.out.println("1. main action");
		System.out.println("2. quick action");
		System.out.println("3. pass to the next player");
		actionType=this.selector(1, 3);
		
		switch(actionType){
		case 1:
			System.out.println("Select now the main action to perform");
			System.out.println("1. To Elect a councillor");
			System.out.println("2. To Acquire a Building Permit");
			System.out.println("3. To Build an Emporium by permit");
			System.out.println("4. To Build an Emporium under the consense of the king");
			break;
		case 2: 
			System.out.println("Select now the quick action to perform");
			System.out.println("1. To Engage a new Assistant");
			System.out.println("2. To Change Face Up permits using an assistant");
			System.out.println("3. To Elect a councillor using an assistant");
			System.out.println("4. To obtain an extra main action");
			break;			
		}
		selection=this.selector(1, 4);
		
	}


}
