package model.game.politics;

import java.util.ArrayList;
import java.util.Collections;

import model.game.Player;

/**
 * @author Vitaliy Pakholko
 */
public class PoliticsDeck 
{
	private ArrayList<PoliticsCard> deck;
	
	public PoliticsDeck(ArrayList<PoliticsCard> array) 
	{
		deck=array;
	}
	
	public void addUsedPolitics(PoliticsDeck politicsDeck)
	{
		for(PoliticsCard p:politicsDeck.deck)
			this.deck.add(p);
	}
	
	/**
	 * Makes given player draw a card from politics deck.
	 * @param player that wants to draw a card
	 * @throws NullPointerException if the deck is empty (caught in Game class)
	 */
	public void drawCard(Player player)
	{
		if(!deck.isEmpty())
		{
			player.addPoliticsCard(deck.remove(0));
			return;
		}
		throw new NullPointerException();
	}
	
	public void shuffle()
	{
		Collections.shuffle(deck);
	}
	
	public boolean isEmpty()
	{
		return deck.isEmpty();
	}
	
	public void clear()
	{
		deck.clear();
	}
	
	/**
	 * modified by Emanuele Ricciardelli
	 */
	public void append(){
		this.deck.addAll(this.deck);
	}

	public void drawNCards(Player player){
		final int n=6;
		for(int i=0;i<n;i++){
			this.drawCard(player);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PoliticsDeck [politicsDeck=" + deck + "]";
	}
}
