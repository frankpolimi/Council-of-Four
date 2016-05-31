package cg2;

import static org.junit.Assert.*;
import org.junit.Test;
import cg2.SupportClass;
import model.game.council.Councillor;
import model.game.politics.JollyPoliticsCard;

public class JollyPoliticsCardTest 
{
	Councillor randomColorCouncillor=new Councillor(SupportClass.giveRandomColor());
	JollyPoliticsCard jolly=new JollyPoliticsCard();
	
	@Test
	public void testPayCouncillorAlwaysTrue() 
	{
		assertTrue(jolly.payCouncillor(randomColorCouncillor));
	}

}
