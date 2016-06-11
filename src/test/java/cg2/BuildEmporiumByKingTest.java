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

public class BuildEmporiumByKingTest {

	@Test
	public void testTakeAction() 
	{
		Game game;
		try 
		{
			game = SupportClass.gameWithPlayersCreator("Gianni", "Alberto");
			Player player=game.getCurrentPlayer();
			KingsCouncil council=game.getKingsCouncil();
			ArrayList<PoliticsCard> list=SupportClass.giveJollyHand();
			City city=game.getAllCities().get(0);
			Action action=new BuildEmporiumByKing(council, list, city);
			game.decrementMainActionCounter();
			try
			{
				action.takeAction(game);
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			
			game.incrementMainActionCounter();
			city=game.getKingsPosition();
			action=new BuildEmporiumByKing(council, list, city);
			game.getCurrentPlayer().setCoins(0);
			assertFalse(city.hasPlayerBuilt(player));
			assertTrue(action.takeAction(game));
			assertTrue(city.hasPlayerBuilt(player));
			assertEquals(0, game.getCurrentPlayer().getCoins());

			city=game.getAllCities().get(0);
			assertFalse(city.hasPlayerBuilt(player));
			game.incrementMainActionCounter();
			
			try
			{
				action.takeAction(game);
			}
			catch(IllegalArgumentException e)
			{
				assertTrue(true);
			}
			
			action=new BuildEmporiumByKing(council, list, city);
			try
			{
				action.takeAction(game);
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			
			player.setCoins(100);
			player.setRemainingEmporiums(0);
			
			try
			{
				action.takeAction(game);
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			
			player.setRemainingEmporiums(10);
			assertTrue(action.takeAction(game));
			assertTrue(city.hasPlayerBuilt(player));
			
			game.incrementMainActionCounter();
			player.setCoins(100);
			player.setAssistants(0);
			city=game.getAllCities().get(2);
			city.addEmporium(SupportClass.giveRandomColor());
			city.addEmporium(SupportClass.giveRandomColor());
			action=new BuildEmporiumByKing(council, list, city);
			
			try
			{
				action.takeAction(game);
			}
			catch(IllegalStateException e)
			{
				assertTrue(true);
			}
			
			player.setAssistants(20);
			assertTrue(action.takeAction(game));
			
		} catch (JDOMException | IOException e) {
			fail("Game creation error");
			e.printStackTrace();
		}
		
	}

}
