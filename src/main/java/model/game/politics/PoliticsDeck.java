package model.game.politics;

import java.util.ArrayList;
import java.util.Collections;

import model.game.Player;

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
			player.addPoliticsCard(politicsDeck.remove(0));return;
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
	/**
	 * modified by Emanuele Ricciardelli
	 */
	public PoliticsDeck(ArrayList<PoliticsCard> array) 
	{
		politicsDeck=array;
	}
	
	public void append(){
		this.politicsDeck.addAll(this.politicsDeck);
	}

	public void drawNCards(Player player){
		final int N=6;
		for(int i=0;i<N;i++){
			this.drawCard(player);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PoliticsDeck [politicsDeck=" + politicsDeck + "]";
	}
	
	
}
