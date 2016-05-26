/**
 * 
 */
package view;

import java.util.Scanner;

import model.game.Game;

/**
 * @author Francesco Vetrò
 *
 */
public interface State {

	public default void doAction(ClientView view, Game game){
		view.setState(new StartState());
	}
	
	public void display();
	
	public default int selector(int min, int max){
		Scanner scanner = new Scanner(System.in);
		int selection=scanner.nextInt();
		while(selection<min||selection>max){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=scanner.nextInt();
		}
		return selection;	
	}
}
