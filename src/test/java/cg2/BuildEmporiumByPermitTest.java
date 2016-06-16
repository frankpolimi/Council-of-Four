package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.game.Game;
import model.game.politics.PoliticsCard;

public class BuildEmporiumByPermitTest {

	@Test
	public void testTakeAction() 
	{
		Game game;
		try {
			game=SupportClass.gameWithPlayersCreator("Gesualdo", "Cristoforo","Amerigo","Tomahawk");
			ArrayList<PoliticsCard> list=SupportClass.giveJollyHand();
			//Most of the exceptions were tested in BuildEmporiumByKing so in this test i will only test his particular exceptions and functions. 
			//Probably I will unite the common exceptions in one method given the time to do it.
			
			
		} catch (JDOMException | IOException e) {
			fail("Game creation error");
			e.printStackTrace();
		}
	}

}
