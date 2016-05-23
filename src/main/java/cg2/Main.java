package cg2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jdom2.JDOMException;

import cg2.game.Game;
import cg2.player.Player;
import cg2.view.View;

public class Main 
{

	public static void main(String[] args){
			
			Scanner in = new Scanner(System.in);
			
			Player p1 = null;
			Player p2 = null;
			
			try {
				p1 = new Player("Marco", 1, 10, 0);
				p2 = new Player("Paolo", 2, 11, 1);
			} catch (JDOMException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<Player> player=new ArrayList<>();
			player.add(p1);
			player.add(p2);
			Game game = null;
			try {
				game = new Game(player);
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			View view = new View(game, 1);
			
			String input;
			
			do{
				view.getState().display();
				input = in.nextLine();
				view.input(input.toLowerCase());
			}while(!input.equals("quit"));
			
			in.close();
	}
}
