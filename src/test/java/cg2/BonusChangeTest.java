package cg2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import controller.BonusChange;
import model.bonus.Bonus;
import model.bonus.CoinBonus;

public class BonusChangeTest {

	@Test
	public void testConstructor() {
		List<Bonus> b = new ArrayList<>();
		b.add(new CoinBonus(1));
		BonusChange bc = new BonusChange(b);
		assertEquals(b, bc.getBonusList());
	}
	
	@Test
	public void testAddBonus(){
		Bonus b = new CoinBonus(1);
		BonusChange bc = new BonusChange();
		bc.addBonus(b);
		assertEquals(b, bc.getBonusList().get(0));
	}

}
