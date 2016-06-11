package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.actions.AcquirePermit;
import model.actions.Action;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.council.RegionalCouncil;
import model.game.politics.JollyPoliticsCard;
import model.game.politics.PoliticsCard;

public class AcquirePermitTest {

	@Test
	public void testTakeAction() 
	{
		try {
			Game game=SupportClass.gameWithPlayersCreator("Gianni", "Alberto");
			RegionalCouncil council=game.getRegionalCouncils().get(0);
			ArrayList<PoliticsCard> list=new ArrayList<PoliticsCard>();
			list.add(new JollyPoliticsCard());
			list.add(new JollyPoliticsCard());
			list.add(new JollyPoliticsCard());
			list.add(new JollyPoliticsCard());
			
			BuildingPermit validPermit=game.getAllPermitsDecks().get(0).giveAFaceUpPermit(0);
			Action action=new AcquirePermit(council, list, validPermit);
			assertFalse(game.getCurrentPlayer().getAllPermits().contains(validPermit));
			assertTrue(action.takeAction(game));
			assertTrue(game.getCurrentPlayer().getAllPermits().contains(validPermit));
			
			game.incrementMainActionCounter();
			BuildingPermit notValidPermit=game.getAllPermitsDecks().get(2).getBuildingPermitsDeck().get(2);
			action=new AcquirePermit(council, list, notValidPermit);
			try
			{
				action.takeAction(game);
			}
			catch(IllegalArgumentException e)
			{
				assertTrue(true);
			}
			
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
			list.clear();
			game.getCurrentPlayer().setCoins(0);
			try
			{
				action.takeAction(game);
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
