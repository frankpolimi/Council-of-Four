package cg2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import org.junit.Test;
import model.game.council.Councillor;
import model.game.council.KingsCouncil;

public class KingsCouncilTest 
{
	Councillor c1=new Councillor(SupportClass.giveRandomColor());
	Councillor c2=new Councillor(SupportClass.giveRandomColor());
	Councillor c3=new Councillor(SupportClass.giveRandomColor());
	
	Councillor c4=new Councillor(SupportClass.giveRandomColor());
	
	
	@Test(expected=NullPointerException.class)
	public void testKingsCouncil() 
	{
		new KingsCouncil(null);
	}

	@Test
	public void testElectCouncillor() 
	{
		ArrayList<Councillor> arrayListCouncillor=new ArrayList<Councillor>();
		arrayListCouncillor.add(c1);
		arrayListCouncillor.add(c2);
		arrayListCouncillor.add(c3);
		ArrayBlockingQueue<Councillor> councillors=new ArrayBlockingQueue<Councillor>(3, true,arrayListCouncillor);
		KingsCouncil k=new KingsCouncil(councillors);
		assertEquals(k.electCouncillor(c4), c1);
		assertEquals(k.getCouncillors().element(), c2);
		assertTrue(k.getCouncillors().contains(c4));
		assertTrue(k.getCouncillors().contains(c3));
		
	}
}
