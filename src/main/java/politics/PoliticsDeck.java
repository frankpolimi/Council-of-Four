package politics;

import java.util.ArrayList;

import cg2.player.Player;

/**
 * @author Vitaliy Pakholko
 */
public class PoliticsDeck 
{
	ArrayList<PoliticsCard> politicsDeck;
	
	
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
	
}
