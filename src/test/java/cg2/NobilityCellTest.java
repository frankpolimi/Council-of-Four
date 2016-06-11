package cg2;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.bonus.Bonus;
import model.bonus.CoinBonus;
import model.game.NobilityCell;

public class NobilityCellTest {

	@Test(expected = NullPointerException.class)
	public void testBonusListNull() {
		new NobilityCell(null);
	}
	
	@Test
	public void testConstructor(){
		ArrayList<Bonus> b = new ArrayList<>();
		b.add(new CoinBonus(2));
		new NobilityCell(b);
	}
}
