package model.actions;

import java.util.List;

import controller.StateChange;
import model.game.Game;
import model.game.Player;
import view.ActionRequest;
import view.EndState;
import view.MarketBuyingState;
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
			List<Player> ref;
			if(game.getGameState().getClass().equals(MarketBuyingState.class)){
				ref=game.getShuffledPlayers();
			}else{
				ref=game.getPlayers();
			}
			
			int currentIndex=ref.indexOf(game.getCurrentPlayer());
			
			if(currentIndex+1==ref.size()){
				nextPlayer=ref.get(0);
				game.setCurrentPlayer(nextPlayer);
				if(!game.getGameState().getClass().equals(EndState.class))
					game.nextState();
			}else{
				nextPlayer=ref.get(currentIndex+1);
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
