package cg2;

import static org.junit.Assert.*;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.actions.Action;
import model.actions.ElectCouncillor;
import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;

public class ElectCouncillorTest 
{

	@Test
	public void testTakeAction()
	{
		Game game;
		try {
			game=SupportClass.gameWithPlayersCreator("JohnFitzgerald", "Marylin");
			ArrayList<Councillor> oldCouncil=new ArrayList<>();
			Council validCouncil=game.getAllCouncils().get(0);
			for(Councillor co:validCouncil.getCouncillors())
			{
				oldCouncil.add(co);
			}
			
			Council notValidCouncil=SupportClass.councilCreatorByColors(Color.black, Color.blue, Color.black, Color.cyan, "");
			Councillor validCouncillor=game.getAvaliableCouncillor().get(0);
			game.getAvaliableCouncillor().clear();
			game.getAvaliableCouncillor().add(validCouncillor);
			Councillor notValidCouncillor;
			int oldPlayerCoins=game.getCurrentPlayer().getCoins();
			
			do
			{
				notValidCouncillor=new Councillor(SupportClass.giveRandomColor());
			}while(game.getAvaliableCouncillor().contains(notValidCouncillor));
			
			/*for(Councillor co:validCouncil.getCouncillors())
			{
				oldCouncil.add(co);
			}	*/	
			Action action=new ElectCouncillor(notValidCouncillor, validCouncil);
			try
			{
				action.takeAction(game);
				fail("Action should launch exception");
			}
			catch(IllegalArgumentException e)
			{
				assertTrue(true);
			}
			
			action=new ElectCouncillor(validCouncillor, notValidCouncil);
			try
			{
				action.takeAction(game);
				fail("Action should launch exception");
			}
			catch(IllegalArgumentException e)
			{
				assertTrue(true);
			}
			
			action=new ElectCouncillor(validCouncillor, validCouncil);
			assertTrue(action.takeAction(game));
			
			ArrayList<Councillor> newCouncil=new ArrayList<>();
			for(Councillor co:validCouncil.getCouncillors())
			{
				newCouncil.add(co);
			}
			assertTrue(newCouncil.contains(validCouncillor));
			for(int i=1;i<=3;i++)
			{
				assertTrue(newCouncil.contains(oldCouncil.get(i)));
			}
			assertTrue(game.getAvaliableCouncillor().size()==1);
			assertTrue(game.getAvaliableCouncillor().get(0).equals(oldCouncil.get(0)));
			assertTrue(newCouncil.contains(validCouncillor));
			assertEquals(oldPlayerCoins+4, game.getCurrentPlayer().getCoins());
			
		} catch (JDOMException | IOException e) {
			fail("Game creation error");
			e.printStackTrace();
		}
		
	}

}
