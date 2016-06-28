package cg2;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.*;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.game.Player;
import model.game.politics.ColoredPoliticsCard;
import model.game.politics.PoliticsCard;
import model.game.politics.PoliticsDeck;

public class PoliticsDeckTest {

	@Test(expected=NullPointerException.class)
	public void arrayPassedIsNull() {
		PoliticsDeck deck=new PoliticsDeck(null);
	}
	
	@Test
	public void testAddUsedPolitics(){
		ArrayList<PoliticsCard> cards=new ArrayList<>();
		cards.add(new ColoredPoliticsCard(Color.red));
		cards.add(new ColoredPoliticsCard(Color.blue));
		cards.add(new ColoredPoliticsCard(Color.red));
		PoliticsDeck used=new PoliticsDeck(cards);
		PoliticsDeck deck=new PoliticsDeck(new ArrayList<PoliticsCard>());
		deck.addUsedPolitics(used);
		for(int i=0;i<cards.size();i++){
			assertEquals(used.getCardAtIndex(i),deck.getCardAtIndex(i));
		}
	}
	
	@Test(expected=NullPointerException.class)
	public void testDrawingWithDeckEmpty() throws JDOMException, IOException{
		PoliticsDeck deck=new PoliticsDeck(new ArrayList<PoliticsCard>());
		deck.drawCard(new Player("io",1));
	}
	
	@Test
	public void testAppendMethod(){
		ArrayList<PoliticsCard> cards=new ArrayList<>();
		cards.add(new ColoredPoliticsCard(Color.red));
		cards.add(new ColoredPoliticsCard(Color.blue));
		cards.add(new ColoredPoliticsCard(Color.red));
		ArrayList<PoliticsCard> cards2=new ArrayList<>();
		cards2.add(new ColoredPoliticsCard(Color.red));
		cards2.add(new ColoredPoliticsCard(Color.blue));
		cards2.add(new ColoredPoliticsCard(Color.red));
		cards2.add(new ColoredPoliticsCard(Color.red));
		cards2.add(new ColoredPoliticsCard(Color.blue));
		cards2.add(new ColoredPoliticsCard(Color.red));
		PoliticsDeck expected=new PoliticsDeck(cards2);
		PoliticsDeck deck=new PoliticsDeck(cards);
		deck.append();
		for(int i=0;i<cards2.size();i++){
			assertEquals(expected.getCardAtIndex(i),deck.getCardAtIndex(i));
		}
	}
	
	@Test
	public void testDrawingCards() throws JDOMException, IOException{
		ArrayList<PoliticsCard> cards=new ArrayList<>();
		cards.add(new ColoredPoliticsCard(Color.red));
		cards.add(new ColoredPoliticsCard(Color.blue));
		cards.add(new ColoredPoliticsCard(Color.red));
		PoliticsDeck deck=new PoliticsDeck(cards);
		Player p=new Player("io",1);
		deck.drawCard(p);
		assertFalse(p.getCardsOwned().isEmpty());
		assertEquals(new ColoredPoliticsCard(Color.red),p.getCardsOwned().get(0));
		for(int i=0;i<cards.size();i++){
			assertEquals(cards.get(i),deck.getCardAtIndex(i));
		}
	}
	

}
