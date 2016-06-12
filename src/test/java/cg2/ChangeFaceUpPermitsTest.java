package cg2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.game.Game;

public class ChangeFaceUpPermitsTest {

	@Test
	public void testTakeAction() 
	{
		Game game;
		try {
			game=SupportClass.gameWithPlayersCreator("Mariello", "Ugo");
			
			
			
		} catch (JDOMException | IOException e) {
			fail("Game creation error");
			e.printStackTrace();
		}
		
	}

}
