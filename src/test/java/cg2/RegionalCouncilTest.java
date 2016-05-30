package cg2;


import java.awt.Color;
import java.util.concurrent.ArrayBlockingQueue;
import org.junit.Test;
import model.game.PermitsDeck;
import model.game.council.Councillor;
import model.game.council.RegionalCouncil;

public class RegionalCouncilTest {

	@Test(expected=NullPointerException.class)
	public void testRegionalCouncilNullCouncil() 
	{
		new RegionalCouncil(null, new PermitsDeck(null));
	}
	
	@Test(expected=NullPointerException.class)
	public void testRegionalCouncilNullDeck() 
	{
		new RegionalCouncil(new ArrayBlockingQueue<>(1), null);
	}

	/*@Test
	public void testGetPermitsDeckNull() 
	{
		new RegionalCouncil(new ArrayBlockingQueue<>(1), new PermitsDeck(null));  // Non posso testarlo perche' non posso impostarlo null, poi per ora funzioneranno tutti i null che ho dato in ingresso ma poi quando modificheremo il loro codice non andranno
	}*/

	@Test(expected=NullPointerException.class)
	public void testElectCouncillorNullCouncillor() 
	{
		new RegionalCouncil(new ArrayBlockingQueue<>(1), new PermitsDeck(null)).electCouncillor(null);
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testElectCouncillorCannotRemoveHeadCouncil() 
	{
		new RegionalCouncil(new ArrayBlockingQueue<>(1), new PermitsDeck(null)).electCouncillor(new Councillor(Color.black));
	}
	

	@Test(expected=NullPointerException.class)
	public void testGetCouncillors() 
	{
		new RegionalCouncil(new ArrayBlockingQueue<>(1), new PermitsDeck(null)).getCouncillors();
	}

	@Test(expected=NullPointerException.class)
	public void testSetCouncillors() 
	{
		new RegionalCouncil(new ArrayBlockingQueue<>(1), new PermitsDeck(null)).setCouncillors(null);
	}

}
