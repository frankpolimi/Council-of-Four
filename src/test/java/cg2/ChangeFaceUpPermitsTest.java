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
	PermitsDeck deck;
	Player currentPlayer;
	Action action;

	public void changeFaceUpPermitsSetup()
	{
		try {
			game=SupportClass.gameWithPlayersCreator("Mariello", "Ugo","Umberto","Santiago");
			deck=game.getAllPermitsDecks().get(0);
			currentPlayer=game.getCurrentPlayer();
			action=new ChangeFaceUpPermits(deck);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTakeActionValid() 
	{
		this.changeFaceUpPermitsSetup();
		ArrayList<BuildingPermit> oldPermits=new ArrayList<>();
		for(BuildingPermit p:deck.getFaceUpPermits())
		{
			oldPermits.add(p);
		}
		currentPlayer.setCoins(2);
		assertTrue(action.takeAction(game));
		assertEquals(0,currentPlayer.getCoins());
		ArrayList<BuildingPermit> newPermits=new ArrayList<>();
		for(BuildingPermit p:deck.getFaceUpPermits())
		{
			newPermits.add(p);
		}
		assertNotEquals(newPermits, oldPermits);
	}
	
	@Test
	public void testActionWithNoCoins()
	{
		this.changeFaceUpPermitsSetup();
		currentPlayer.setCoins(0);
		try
		{
			action.takeAction(game);
			fail("Action should launch exception");
		}catch(IllegalStateException e)
		{
			assertTrue(true);
		}

	}
	
	@Test
	public void testActionWithNoMovesLeft()
	{
		this.changeFaceUpPermitsSetup();
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
