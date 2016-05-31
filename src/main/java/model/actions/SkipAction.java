package model.actions;

import java.util.List;

import model.game.Game;
import model.game.Player;
import view.MarketBuyingState;
import view.StartState;

public class SkipAction extends Action
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
		if(!this.checkAction(game)&&game.getGameState().getClass().equals(StartState.class))
			throw new IllegalStateException("You have to use all the main actions before passing");
		else
		{
			Player nextPlayer;
			Player current=game.getCurrentPlayer();
			List<Player> ref;
			if(game.getGameState().getClass().equals(MarketBuyingState.class)){
				ref=game.getShuffledPlayers();
			}else{
				ref=game.getPlayers();
			}
			
			int currentIndex=ref.indexOf(current);
			
			if(currentIndex+1==ref.size()){
				nextPlayer=ref.get(0);
				game.setCurrentPlayer(nextPlayer);
				if(!game.isLastTurn())
					game.nextState();
			}else{
				nextPlayer=ref.get(currentIndex+1);
				game.setCurrentPlayer(nextPlayer);	
			}
			
			game.incrementMainActionCounter();
			if(game.getQuickActionCounter()==0){
				game.incrementQuickActionCounter();
			}
			
			if(game.isLastTurn()){
				game.decrementLastRemainingPlayers();
			}
			super.takeAction(game);
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
