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
	Game game;
	Council validCouncil;
	Council notValidCouncil;
	Councillor validCouncillor;
	Councillor notValidCouncillor;
	Action action;
	
	public void electCouncillorSetup()
	{
		try {
			game=SupportClass.gameWithPlayersCreator("JohnFitzgerald", "Marylin","Castro","Osama");
			validCouncil=game.getAllCouncils().get(0);
			notValidCouncil=SupportClass.councilCreatorByColors(Color.black, Color.blue, Color.black, Color.cyan, "");
			validCouncillor=game.getAvaliableCouncillor().get(0);
			do
			{
				notValidCouncillor=new Councillor(SupportClass.giveRandomColor());
			}while(game.getAvaliableCouncillor().contains(notValidCouncillor));
			
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTakeAction()
	{
			
			ArrayList<Councillor> oldCouncil=new ArrayList<>();
			for(Councillor co:validCouncil.getCouncillors())
			{
				oldCouncil.add(co);
			}
			game.getAvaliableCouncillor().clear();
			game.getAvaliableCouncillor().add(validCouncillor);
			
			int oldPlayerCoins=game.getCurrentPlayer().getCoins();
			/*for(Councillor co:validCouncil.getCouncillors())
			{
				oldCouncil.add(co);
			}	*/	
			action=new ElectCouncillor(notValidCouncillor, validCouncil);
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
			
		
	}
	

}
