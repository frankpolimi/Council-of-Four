package model.game.politics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import model.game.Player;

/**
 * @author Vitaliy Pakholko
 */
public class PoliticsDeck implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4630033173259427714L;
	private ArrayList<PoliticsCard> deck;
	
	/**
	 * constructor of a deck of politics card
	 * @param array the list of politics card in the deck
	 */
	public PoliticsDeck(ArrayList<PoliticsCard> array)
	{
		if(array==null){
			throw new NullPointerException("Array is null");
		}

		deck=array;
	}
	
	/**
	 * method that add the cards of the deck as parameter
	 * to this deck
	 * @param politicsDeck the deck of used cards
	 * @throws NullPointerException if param is null
	 */
	public void addUsedPolitics(PoliticsDeck politicsDeck)
	{
		if(politicsDeck==null)
			throw new NullPointerException("used Deck passed is null");
		
		for(PoliticsCard p:politicsDeck.deck)
			this.deck.add(p);
	}
	
	/**
	 * gives the card at the parameter position
	 * @param i the index of the card in the deck
	 * @return the card at the given index
	 */
	public PoliticsCard getCardAtIndex(int i)
	{
		return this.deck.get(i);
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
	
	/**
	 * shuffle the deck of politics card
	 */
	public void shuffle()
	{
		Collections.shuffle(deck);
	}
	
	/**
	 * query if the deck is empty
	 * @return true if deck is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return deck.isEmpty();
	}
	
	/**
	 * removes all the cards from the deck
	 */
	public void clear()
	{
		deck.clear();
	}
	
	/**
	 * @author Emanuele Ricciardelli
	 * duplicate all the cards already in the deck
	 * and adds them to the deck
	 */
	public void append(){
		this.deck.addAll(this.deck);
	}

	/**
	 * gives the player given as parameter 
	 * the amount of cards specified in the method
	 * @param player the player that must draw a number 
	 * 					of cards
	 */
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
