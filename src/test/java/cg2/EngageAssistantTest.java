package cg2;

import static org.junit.Assert.*;
import java.io.IOException;
import org.jdom2.JDOMException;
import org.junit.Test;

import model.actions.Action;
import model.actions.EngageAssistant;
import model.game.Game;

public class EngageAssistantTest
{
	Game game;
	int CurrentPlayersCurrentAssistants;
	int OtherPlayerCurrentAssistants;
	Action action;
	
	public void EngageAssistantTestSetup()
	{
		try {
			game=SupportClass.gameWithPlayersCreator("Piergigi", "Marcantonio","Caesar","Augusto");
			CurrentPlayersCurrentAssistants=game.getCurrentPlayer().getAssistants();
			OtherPlayerCurrentAssistants=game.getPlayers().get(1).getAssistants();
			action=new EngageAssistant();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActionWithNoValidMoves()
	{
		this.EngageAssistantTestSetup();
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
	
	@Test
	public void testActionWithNotEnoughCoins()
	{
		this.EngageAssistantTestSetup();
		game.getCurrentPlayer().setCoins(1);
		
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
	
	
	@Test
	public void testActionValid()
	{
		this.EngageAssistantTestSetup();	
		game.getCurrentPlayer().setCoins(3);
		assertTrue(action.takeAction(game));
		assertEquals(CurrentPlayersCurrentAssistants+1, game.getCurrentPlayer().getAssistants());
		assertEquals(OtherPlayerCurrentAssistants, game.getPlayers().get(1).getAssistants());
	}

}
