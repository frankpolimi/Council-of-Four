package cg2;

import static org.junit.Assert.*;
import java.io.IOException;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.bonus.AssistantBonus;
import model.game.Game;

public class AssistantBonusTest {

	@Test
	public void testCreateAssistantBonus() {
		Integer i = new Integer(12);
		AssistantBonus b = new AssistantBonus(i);
		assertEquals(b.getAmount(), i);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreateAssistantBonusWithNull(){
		new AssistantBonus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAssistantBonusBelowZero(){
		new AssistantBonus(new Integer(-54));
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	@Test
	public void testAssignBonusToPlayer()
	{
		try {
			Game game=SupportClass.gameWithPlayersCreator("Giocatore1", "Giocatore2");
			AssistantBonus ab=new AssistantBonus(new Integer(3));
			int presentAssistantsValuePlayer1=game.getCurrentPlayer().getAssistants();
			int presentAssistantsValuePlayer2=game.getPlayers().get(1).getAssistants();
			ab.update(game);
			assertEquals(presentAssistantsValuePlayer1+3, game.getCurrentPlayer().getAssistants());
			assertEquals(presentAssistantsValuePlayer2, game.getPlayers().get(1).getAssistants());
		} catch (JDOMException e) {
			e.printStackTrace();
			fail("Jdome");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO");
		}
	}
}
