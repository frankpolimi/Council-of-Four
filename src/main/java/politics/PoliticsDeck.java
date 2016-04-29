package politics;

import java.util.ArrayList;
import java.util.Collections;

import cg2.player.Player;

/**
 * @author Vitaliy Pakholko
 */
public class PoliticsDeck 
{
	private ArrayList<PoliticsCard> politicsDeck;
	
	
	/**
	 * Makes given player draw a card from politics deck.
	 * @param player that wants to draw a card
	 * @throws NullPointerException if the deck is empty (caught in Game class)
	 */
	public void drawCard(Player player)
	{
		if(!politicsDeck.isEmpty())
		{
			player.getStatus().addPoliticsCard(politicsDeck.remove(0));return;
		}
		throw new NullPointerException();
	}
	
	public void shuffle()
	{
		Collections.shuffle(politicsDeck);
	}
	
	public boolean isEmpty()
	{
		return politicsDeck.isEmpty();
	}
	
	public void addUsedPolitics(PoliticsDeck politicsDeck)
	{
		for(PoliticsCard p:politicsDeck.politicsDeck)
			this.politicsDeck.add(p);
	}
	
	public void clear()
	{
		politicsDeck.clear();
	}

	public PoliticsDeck() 
	{
		
	}
	
	
	
}
