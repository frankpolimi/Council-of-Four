package cg2;

import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.Test;
import model.game.council.Councillor;
import model.game.politics.ColoredPoliticsCard;

public class ColoredPoliticsCardTest 
{
	
	Councillor blackCouncillor=new Councillor(Color.black);
	Councillor whiteCouncillor=new Councillor(Color.white);
	ColoredPoliticsCard whiteCard=new ColoredPoliticsCard(Color.white);

	@Test(expected=NullPointerException.class)
	public void testConstructorNull() 
	{
		new ColoredPoliticsCard(null);
	}
	
	@Test
	public void testPayCouncillorWithDifferentColor()
	{
		assertFalse(whiteCard.payCouncillor(blackCouncillor));
	}
	
	@Test
	public void testPayCouncillorWithSameColor()
	{
		assertTrue(whiteCard.payCouncillor(whiteCouncillor));
	}

}
