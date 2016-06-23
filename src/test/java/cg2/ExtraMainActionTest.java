package cg2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.actions.Action;
import model.actions.ExtraMainAction;
import model.game.Game;
import model.game.Player;

public class ExtraMainActionTest 
{
	Game game;
	Player currentPlayer;
	Action action;
	int oldMainActions;
	
	public void ExtraMainActionTestSetup()
	{
		try {
			game=SupportClass.gameWithPlayersCreator("Gesualdo", "MbareGiacomo","Pino","Pina");
			currentPlayer=game.getCurrentPlayer();
			action=new ExtraMainAction();
			oldMainActions=game.getMainActionCounter();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testTakeActionWithNotEnoughAssistants()
	{
		this.ExtraMainActionTestSetup();
		currentPlayer.setAssistants(2);
		int oldMainActions=game.getMainActionCounter();
		try
		{
			action.takeAction(game);
			fail("Action should launch exception");
		}
		catch (IllegalStateException e)
		{
			assertTrue(true);
		}
		assertEquals(oldMainActions, game.getMainActionCounter());
	}
	
	@Test
	public void testActionValid()
	{
		this.ExtraMainActionTestSetup();
		game.getCurrentPlayer().setAssistants(4);
		assertTrue(action.takeAction(game));
		assertEquals(oldMainActions+1, game.getMainActionCounter());
	}
	
	@Test
	public void testActionWithNoMovesLeft()
	{
		this.ExtraMainActionTestSetup();
		game.decrementQuickActionCounter();
		try
		{
			action.takeAction(game);
			fail("Action should launch exception");
		}
		catch(IllegalStateException e)
		{
			assertTrue(true);
		}
	}
}
