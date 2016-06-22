package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import org.junit.Test;

import model.actions.Action;
import model.actions.ChangeFaceUpPermits;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
import model.game.Player;

public class ChangeFaceUpPermitsTest 
{
	Game game;
	

	@Test
	public void testTakeAction() 
	{
		Game game;
		try {
			game=SupportClass.gameWithPlayersCreator("Mariello", "Ugo","Umberto","Santiago");
			ArrayList<BuildingPermit> oldPermits=new ArrayList<>();
			PermitsDeck deck=game.getAllPermitsDecks().get(0);
			Player currentPlayer=game.getCurrentPlayer();
			for(BuildingPermit p:deck.getFaceUpPermits())
			{
				oldPermits.add(p);
			}
			
			Action action=new ChangeFaceUpPermits(deck);
			currentPlayer.setCoins(2);
			assertTrue(action.takeAction(game));
			assertEquals(0,currentPlayer.getCoins());
			ArrayList<BuildingPermit> newPermits=new ArrayList<>();
			for(BuildingPermit p:deck.getFaceUpPermits())
			{
				newPermits.add(p);
			}
			assertNotEquals(newPermits, oldPermits);
			
			currentPlayer.setCoins(2);
			try
			{
				action.takeAction(game);
				fail("Action should launch exception");
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			
			game.incrementQuickActionCounter();
			currentPlayer.setCoins(0);
			try
			{
				action.takeAction(game);
				fail("Action should launch exception");
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			
		} catch (JDOMException | IOException e) {
			fail("Game creation error");
			e.printStackTrace();
		}
		
	}

}
