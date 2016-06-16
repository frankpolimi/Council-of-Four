package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.bonus.GetPoliticBonus;
import model.game.Game;
import model.game.politics.PoliticsCard;

public class GetPoliticBonusTest {

	@Test
	public void testCreatePoliticBonusCorrect() {
		Integer i = new Integer(12);
		GetPoliticBonus b = new GetPoliticBonus(i);
		assertEquals(b.getAmount(), i);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreatePoliticBonusWithNull(){
		new GetPoliticBonus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePoliticBonusBelowZero(){
		new GetPoliticBonus(new Integer(-54));
	}
	
	@Test
	public void testAssignBonusToPlayer(){
		try {
			Game g = SupportClass.gameWithPlayersCreator("G1", "G2","G3","G4");
			GetPoliticBonus b = new GetPoliticBonus(1);
			List<PoliticsCard> politics1 = g.getCurrentPlayer().getCardsOwned();
			List<PoliticsCard> politics2 = g.getPlayers().get(1).getCardsOwned();
			politics1.add(g.getPoliticsDeck().getCardAtIndex(0));
			b.update(g);
			assertEquals(politics1, g.getCurrentPlayer().getCardsOwned());
			assertEquals(politics2, g.getPlayers().get(1).getCardsOwned());
			assertEquals(politics1.size(), g.getCurrentPlayer().getCardsOwned().size());
			assertEquals(politics2.size(), g.getPlayers().get(1).getCardsOwned().size());
		} catch (JDOMException e) {
			e.printStackTrace();
			fail("JDome");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO");
		}
	}

}
