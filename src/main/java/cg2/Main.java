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
			
			Player p1=new Player("Marco", 1, 10, 20, 10);
			Player p2=new Player("Paolo", 1, 10, 21, 10);
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
				view.displayState();
				input = in.nextLine();
				view.input(input);
			}while(!input.equals("quit"));
			
			in.close();
	}
}
