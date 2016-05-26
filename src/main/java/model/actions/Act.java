package model.actions;

import model.game.Game;

/**
 * @author Vitaliy Pakholko
 */
public interface Act 
{
	public boolean takeAction(Game game);
	public boolean checkAction(Game game);
}
