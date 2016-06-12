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

	@Test
	public void testTakeAction()
	{
		try 
		{
			Game game=SupportClass.gameWithPlayersCreator("Piergigi", "Marcantonio");
			int CurrentPlayersCurrentAssistants=game.getCurrentPlayer().getAssistants();
			int OtherPlayerCurrentAssistants=game.getPlayers().get(1).getAssistants();
			game.getCurrentPlayer().setCoins(1);
			Action a=new EngageAssistant();
			try
			{
				a.takeAction(game);
				fail("Action should launch exception");
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			game.getCurrentPlayer().setCoins(3);
			assertTrue(a.takeAction(game));
			assertEquals(CurrentPlayersCurrentAssistants+1, game.getCurrentPlayer().getAssistants());
			assertEquals(OtherPlayerCurrentAssistants, game.getPlayers().get(1).getAssistants());
			
			try
			{
				a.takeAction(game);
				fail("Action should launch exception");
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			
		} catch (JDOMException | IOException e) {
			fail("Errors on game's creation");
			e.printStackTrace();
		}
		
	}

}
