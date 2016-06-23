package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import org.jdom2.JDOMException;
import org.junit.Test;
import model.bonus.GetPoliticBonus;
import model.game.Game;
import model.game.politics.PoliticsCard;

public class GetPoliticBonusTest 
{
	Game game;
	GetPoliticBonus politcsBonus;
	List<PoliticsCard> currentPlayersPolitics;
	List<PoliticsCard> otherPlayersPolitics;
	
	public void politcsBonusTestSetup()
	{
		try {
			game = SupportClass.gameWithPlayersCreator("G1", "G2","G3","G4");
			politcsBonus = new GetPoliticBonus(1);
			currentPlayersPolitics=game.getCurrentPlayer().getCardsOwned();
			otherPlayersPolitics=game.getPlayers().get(1).getCardsOwned();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

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
	public void testAssignBonusToPlayer()
	{
		this.politcsBonusTestSetup();
		currentPlayersPolitics.add(game.getPoliticsDeck().getCardAtIndex(0));
		politcsBonus.update(game);
		assertEquals(currentPlayersPolitics, game.getCurrentPlayer().getCardsOwned());
		assertEquals(otherPlayersPolitics, game.getPlayers().get(1).getCardsOwned());
		assertEquals(currentPlayersPolitics.size(), game.getCurrentPlayer().getCardsOwned().size());
		assertEquals(otherPlayersPolitics.size(), game.getPlayers().get(1).getCardsOwned().size());
	}

}
