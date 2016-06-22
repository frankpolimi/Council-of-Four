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
					p.changeFaceUpPermits();
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

	public PermitsDeck getDeck() {
		return deck;
	}

	public void setDeck(PermitsDeck deck) {
		this.deck = deck;
	}
	
	
}
