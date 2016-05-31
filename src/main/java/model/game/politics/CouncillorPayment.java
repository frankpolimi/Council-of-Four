package model.game.politics;

import model.game.council.Councillor;

@FunctionalInterface
public interface CouncillorPayment 
{
	
	/**
	 * A card tries to pay a given councillor. True if success, false if failure.
	 * @param councillor is the councillor that the card is trying to pay
	 * @return true if the operation is a success, false if it's a failure
	 */
	public boolean payCouncillor(Councillor councillor);
}
