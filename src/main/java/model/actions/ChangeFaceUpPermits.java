package model.actions;

import model.game.Game;
import model.game.PermitsDeck;

/**
 * @author Vitaliy Pakholko
 */
public class ChangeFaceUpPermits extends QuickAction 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4536872089352094621L;
	private PermitsDeck deck;
	
	/**
	 * constructor for the action change face up permits
	 * @param deck the deck that will have its face up permits changed
	 */
	public ChangeFaceUpPermits(PermitsDeck deck) {
		this.deck = deck;
	}
	
	/** 
	 * The player pays to coins to change the face up permits of a given deck
	 * @throws IllegalStateException if the player has no Quick actions left
	 * @throws IllegalStateException if the player has not enough coins
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Quick actions");
		if(game.getCurrentPlayer().checkAssistants(1))
		{
			for(PermitsDeck p:game.getAllPermitsDecks())
			{
				if(p.equals(deck))
				{
					try
					{
					p.changeFaceUpPermits();
					}
					catch(IllegalStateException e)
					{
						game.getCurrentPlayer().setAssistants((game.getCurrentPlayer().getAssistants()+1));
						throw new IllegalStateException("There was only 1 permit in the faceups, you cannot change them");
					}
					game.decrementQuickActionCounter();
					super.takeAction(game);
					return true;
				}
			}
			return false;
		}
		else 
		{
			throw new IllegalStateException("Not enough coins to change the Permits");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChangeFaceUpPermits: The player pays 2 coins to swap the 2 faced up BuildingPermits of a PermitDeck with a new pair of the same deck.";
	}

	/**
	 * get the deck to be modified
	 * @return the deck given as field of this action
	 */
	public PermitsDeck getDeck() {
		return deck;
	}

	/**
	 * set the deck that will have its 
	 * face up permits changed
	 * @param deck the deck that face up permits will
	 * be changed
	 */
	public void setDeck(PermitsDeck deck) {
		this.deck = deck;
	}
	
	
}
