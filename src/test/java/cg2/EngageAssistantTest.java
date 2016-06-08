package cg2;

import static org.junit.Assert.*;
import java.io.IOException;
import org.jdom2.JDOMException;
import org.junit.Test;
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
			try
			{
				new EngageAssistant().takeAction(game);
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			game.getCurrentPlayer().setCoins(3);
			try
			{
				new EngageAssistant().takeAction(game);
			}
			catch(IllegalStateException e)
			{
				fail("No exception expected here");
			}
			
			assertEquals(CurrentPlayersCurrentAssistants+1, game.getCurrentPlayer().getAssistants());
			assertEquals(OtherPlayerCurrentAssistants, game.getPlayers().get(1).getAssistants());
			
			
			
		} catch (JDOMException | IOException e) {
			fail("Errors on game's creation");
			e.printStackTrace();
		}
		
	}

}
