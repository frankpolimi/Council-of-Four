package cg2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
import model.game.Player;

public class PermitDeckTest 
{
	
	
	Game game;
	PermitsDeck deck;
	BuildingPermit permit;
	Player currentPlayer;
	
	public void permitsDeckTestSetup()
	{
		try {
			game=SupportClass.gameWithPlayersCreator("Player1", "Player2", "Player3", "Player4");
			deck=game.getAllPermitsDecks().get(0);
			currentPlayer=game.getCurrentPlayer();
			permit=deck.giveAFaceUpPermit(1);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGivePermitValid() 
	{
		this.permitsDeckTestSetup();
		permit=deck.giveAFaceUpPermit(1);
		assertTrue(deck.getFaceUpPermits().contains(permit));
		assertFalse(currentPlayer.getAllPermits().contains(permit));
		deck.givePermit(game, permit);
		assertFalse(deck.getFaceUpPermits().contains(permit));
		assertTrue(currentPlayer.getAllPermits().contains(permit));
		assertTrue(deck.getFaceUpPermits().size()==2);
	}
	
	@Test
	public void testGivePermitNotPresentInFaceUps()
	{
		this.permitsDeckTestSetup();
		permit=deck.getBuildingPermitsDeck().get(0);
		try
		{
			deck.givePermit(game, permit);
			fail();
		}catch (IllegalArgumentException e)
		{
			assertTrue(true);
		}
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGivePermitWithNoFaceUps()
	{
		this.permitsDeckTestSetup();
		deck.getFaceUpPermits().clear();
		deck.givePermit(game, permit);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testGivePermitWithEmptyDeck()
	{
		this.permitsDeckTestSetup();
		deck.getBuildingPermitsDeck().clear();
		deck.givePermit(game, permit);
	}
	
	@Test
	public void testChangeFaceUpPermitsValid() 
	{
		this.permitsDeckTestSetup();
		BuildingPermit p1=deck.giveAFaceUpPermit(0);
		BuildingPermit p2=deck.giveAFaceUpPermit(1);
		deck.changeFaceUpPermits();
		assertFalse(deck.getFaceUpPermits().contains(p1));
		assertFalse(deck.getFaceUpPermits().contains(p2));
		assertTrue(deck.getFaceUpPermits().size()==2);
	}


	@Test
	public void testPopPermitValid()
	{
		this.permitsDeckTestSetup();
		permit=deck.getBuildingPermitsDeck().get(0);
		int oldSize=deck.getBuildingPermitsDeck().size();
		BuildingPermit poppedPermit=deck.popPermit();
		assertFalse(deck.getBuildingPermitsDeck().contains(poppedPermit));
		assertEquals(poppedPermit, permit);
		assertEquals(oldSize-1, deck.getBuildingPermitsDeck().size());
	}



}
