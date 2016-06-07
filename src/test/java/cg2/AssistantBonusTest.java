package cg2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.bonus.AssistantBonus;
import model.game.Game;
import model.game.Player;

public class AssistantBonusTest {

	@Test
	public void createAssistantBonusCorrect() {
		Integer i = new Integer(12);
		AssistantBonus b = new AssistantBonus(i);
		assertEquals(b.getAmount(), i);
	}
	
	@Test(expected = NullPointerException.class)
	public void createAssistantBonusWithNull(){
		new AssistantBonus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createAssistantBonusBelowZero(){
		new AssistantBonus(new Integer(-54));
	}
	
	@Test
	public void AssignBonusToPlayer(){
		
	}
}
