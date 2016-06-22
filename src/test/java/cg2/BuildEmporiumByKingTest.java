package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.actions.Action;
import model.actions.BuildEmporiumByKing;
import model.game.Game;
import model.game.Player;
import model.game.council.KingsCouncil;
import model.game.politics.PoliticsCard;
import model.game.topology.City;

public class BuildEmporiumByKingTest 
{
	
	private Game game;
	private Player player;
	private KingsCouncil council;
	private ArrayList<PoliticsCard> list;
	private City city;
	private Action action;
	
	public void buildPermitByKingTestSetup()
	{
		try 
		{
			game = SupportClass.gameWithPlayersCreator("Gianni", "Alberto","Giargianna","Lucrezio");
			player=game.getCurrentPlayer();
			list=SupportClass.giveJollyHand();
			city=game.getAllCities().get(0);
			council=game.getKingsCouncil();
			action=new BuildEmporiumByKing(council, list, city);
			player.setCoins(100);
			player.setAssistants(100);
		} catch (JDOMException | IOException e) 
		{
			e.printStackTrace();
		}
	}

	
	@Test
	public void testTakeActionWithNoActionsLeft()
	{
		this.buildPermitByKingTestSetup();
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
	
	@Test
	public void testTakeActionWithKingNotMoving()
	{
		this.buildPermitByKingTestSetup();
		city=game.getKingsPosition();
		action=new BuildEmporiumByKing(council, list, city);
		game.getCurrentPlayer().setCoins(4);
		assertFalse(city.hasPlayerBuilt(player));
		assertTrue(action.takeAction(game));
		assertTrue(city.hasPlayerBuilt(player));
		assertEquals(0, game.getCurrentPlayer().getCoins());
	}
	
	@Test
	public void testTakeActionWithKingMoving()
	{
		this.buildPermitByKingTestSetup();
		City oldKingsCity=game.getKingsPosition();
		assertFalse(city.hasPlayerBuilt(player));
		assertTrue(action.takeAction(game));
		assertTrue(city.hasPlayerBuilt(player));
		assertEquals(100-game.getMap().howManyVertexPassed(oldKingsCity, city)*2-4,player.getCoins());
	}
	
	@Test
	public void testTakeActionWhilePlayerAlreadyBuiltInCity()
	{
		this.buildPermitByKingTestSetup();
		city.addEmporium(player);
		try
		{
			action.takeAction(game);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}
	
	@Test
	public void testMakeActionKingMovingButNoCoins()
	{
		this.buildPermitByKingTestSetup();
		player.setCoins(0);
		assertTrue(game.getKingsPosition()!=city);
		try
		{
			action.takeAction(game);
			fail();
		}
		catch(IllegalStateException e)
		{
			assertTrue(true);
		}
	}
	
	@Test
	public void testTakeActionWhilePlayerHasNoEmporiums()
	{

		this.buildPermitByKingTestSetup();
		player.setRemainingEmporiums(0);
		try
		{
			action.takeAction(game);
			fail();
		}
		catch(IllegalStateException e)
		{
			assertTrue(true);
		}
	}
	
	@Test
	public void testTakeActionWithOtherPlayerEmporiumsPresent()
	{

		this.buildPermitByKingTestSetup();
		city.addEmporium(game.getPlayers().get(2));
		assertFalse(city.hasPlayerBuilt(player));
		assertTrue(action.takeAction(game));
		assertEquals(99,player.getAssistants());
		assertTrue(city.hasPlayerBuilt(player));
	}
	
	@Test
	public void testTakeActionWithOtherPlayerEmporiumsPresentButNoAssistants()
	{

		this.buildPermitByKingTestSetup();
		city.addEmporium(game.getPlayers().get(2));
		player.setAssistants(0);
		assertFalse(city.hasPlayerBuilt(player));
		try
		{
			action.takeAction(game);
			fail();
		}
		catch (IllegalStateException e)
		{
			assertTrue(true);
		}
	}
	

}
