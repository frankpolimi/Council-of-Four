package cg2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.actions.Action;
import model.actions.ExtraMainAction;
import model.game.Game;

public class ExtraMainActionTest 
{
	@Test
	public void testTakeAction()
	{
		try 
		{
			Game game=SupportClass.gameWithPlayersCreator("Gesualdo", "MbareGiacomo");
			game.getCurrentPlayer().setAssistants(2);
			int oldMainActions=game.getMainActionCounter();
			Action a=new ExtraMainAction();
			try
			{
				a.takeAction(game);
			}
			catch (IllegalStateException e)
			{
				assertTrue(true);
			}
			assertEquals(oldMainActions, game.getMainActionCounter());
			game.getCurrentPlayer().setAssistants(4);
			try
			{
				a.takeAction(game);
			}
			catch(IllegalStateException e)
			{
				fail("No errors expected this time");
			}
			assertEquals(oldMainActions+1, game.getMainActionCounter());
			
			try
			{
				a.takeAction(game);
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			assertEquals(oldMainActions+1, game.getMainActionCounter());
				
		} catch (JDOMException | IOException e) {
			fail("Game creation errors");
			e.printStackTrace();
		}
		
	}

}
