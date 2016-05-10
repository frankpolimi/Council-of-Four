package cg2;

import java.util.Scanner;

import cg2.controller.Controller;
import cg2.game.Game;
import cg2.view.View;

public class Main 
{

	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		
		//Game game = new Game();
		View view = new View(null);
		Controller controller = new Controller(view, null);
		
		view.update("action phase");
		while(true){
			String command = in.nextLine();
			view.input(command);
		}
		
	}

}
