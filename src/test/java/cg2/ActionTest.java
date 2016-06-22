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

	
	public void ActionSetup()
	{
		try {
			game=SupportClass.gameWithPlayersCreator("Giacobbe", "Salvatore","Angelo","Gesu'");
			list=new ArrayList<>();
			Color c1=SupportClass.giveRandomColor();
			Color c2=SupportClass.giveRandomColor();
			Color c3=SupportClass.giveRandomColor();
			list.add(new ColoredPoliticsCard(c1));
			list.add(new ColoredPoliticsCard(c2));
			list.add(new JollyPoliticsCard());
			list.add(new JollyPoliticsCard());
			council=SupportClass.councilCreatorByColors(c1, c1, c2, c3, "King");
			action=new Action();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testPayCouncil() 
	{
		this.ActionSetup();
		assertTrue(action.payCouncil(game, council, list));
	}
	
	@Test
	public void testJolliesCost1Each()
	{
		this.ActionSetup();
		list=SupportClass.giveJollyHand();
		game.getCurrentPlayer().setCoins(4);
		assertTrue(action.payCouncil(game, council, list));
		assertEquals(0, game.getCurrentPlayer().getCoins());
	}

}
