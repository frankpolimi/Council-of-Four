package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.actions.AcquirePermit;
import model.actions.Action;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.council.RegionalCouncil;
import model.game.politics.PoliticsCard;

public class AcquirePermitTest 
{
	Game game;
	RegionalCouncil council;
	ArrayList<PoliticsCard> list;
	BuildingPermit validPermit;
	BuildingPermit notValidPermit;
	Action action;
	
	public void AcquirePermitTestSetup()
	{
		try 
		{
			game=SupportClass.gameWithPlayersCreator("Gianni", "Alberto","Gigi","SanFrancesco");
			council=game.getRegionalCouncils().get(0);
			list=SupportClass.giveJollyHand();
			validPermit=game.getAllPermitsDecks().get(0).giveAFaceUpPermit(0);
			action=new AcquirePermit(council, list, validPermit);
			notValidPermit=game.getAllPermitsDecks().get(2).getBuildingPermitsDeck().get(2);
		} catch (JDOMException | IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTakeActionWithValidPermit()
	{
		this.AcquirePermitTestSetup();
		assertFalse(game.getCurrentPlayer().getAllPermits().contains(validPermit));
		assertTrue(action.takeAction(game));
		assertTrue(game.getCurrentPlayer().getAllPermits().contains(validPermit));
	}
	
	@Test
	public void testTakeActionWithNotValidPermit()
	{
		this.AcquirePermitTestSetup();
		action=new AcquirePermit(council, list, notValidPermit);
		try
		{
			action.takeAction(game);
			fail("Action should launch exception");
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}
	
	@Test
	public void testTakeActionWithNoCoinsAndEmptyHanded()
	{
		this.AcquirePermitTestSetup();
		list.clear();
		game.getCurrentPlayer().setCoins(0);
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
	public void testTakeActionWithNoActionsLeft()
	{
		this.AcquirePermitTestSetup();
		game.decrementMainActionCounter();
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
