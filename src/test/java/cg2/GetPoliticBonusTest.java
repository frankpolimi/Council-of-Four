package cg2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.bonus.CoinBonus;
import model.bonus.GetPoliticBonus;
import model.game.Game;
import model.game.Player;
import model.game.politics.PoliticsCard;
import model.game.politics.PoliticsDeck;

public class GetPoliticBonusTest {

	@Test
	public void createPoliticBonusCorrect() {
		Integer i = new Integer(12);
		GetPoliticBonus b = new GetPoliticBonus(i);
		assertEquals(b.getAmount(), i);
	}
	
	@Test(expected = NullPointerException.class)
	public void createPoliticBonusWithNull(){
		new GetPoliticBonus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPoliticBonusBelowZero(){
		new GetPoliticBonus(new Integer(-54));
	}
	
	@Test
	public void AssignBonusToPlayer(){
		
	}

}
