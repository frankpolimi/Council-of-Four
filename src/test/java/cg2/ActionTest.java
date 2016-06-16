package cg2;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.actions.Action;
import model.game.Game;
import model.game.council.Council;
import model.game.politics.*;

public class ActionTest 
{
	private Game game;
	private ArrayList<PoliticsCard> list;
	private Council council;
	Action action;

	@Test
	public void testPayCouncil() 
	{
		try {
			game=SupportClass.gameWithPlayersCreator("Giacobbe", "Salvatore","Angelo","Gesu'");
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		list=new ArrayList<>();
		Color c1=SupportClass.giveRandomColor();
		Color c2=SupportClass.giveRandomColor();
		Color c3=SupportClass.giveRandomColor();
		list.add(new ColoredPoliticsCard(c1));
		list.add(new ColoredPoliticsCard(c2));
		list.add(new JollyPoliticsCard());
		list.add(new JollyPoliticsCard());
		council=SupportClass.councilCreatorByColors(c1, c1, c2, c3, "King");
		game.getCurrentPlayer().setCoins(1);
		action=new Action();
		assertTrue(action.payCouncil(game, council, list));
	}

}
