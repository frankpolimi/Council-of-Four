package model.actions;

import controller.StateChange;
import model.game.Game;
import model.game.Player;
import view.ActionRequest;
import view.MarketRequest;
import view.MarketSellingState;

public class SkipAction extends QuickAction
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7504135483550600131L;

	/**
	 * This action allows the player to skip the turn. If the next player is the first of the match, the gameState is changed.
	 * @throws IllegalStateException if the player has already the main action to do.
	 *
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game)||game.getMainActionCounter()>0)
			throw new IllegalStateException("You have to use all the main actions before passing");
		else
		{
			Player nextPlayer;
			
			int currentIndex=game.getPlayers().indexOf(game.getCurrentPlayer());
			
			if(currentIndex+1==game.getPlayers().size()){
				nextPlayer=game.getPlayers().get(0);
				game.setCurrentPlayer(nextPlayer);
				game.nextState();
			}else{
				nextPlayer=game.getPlayers().get(currentIndex+1);
				game.setCurrentPlayer(nextPlayer);
				
			}
			return true;
		}
		
	}
	
	@Override
	public boolean checkAction(Game game) {
		if(game.getMainActionCounter()!=0){
			return false;
		}
		return true;
	}
	
}
