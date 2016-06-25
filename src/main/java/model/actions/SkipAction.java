package model.actions;

import java.util.List;
import java.util.Timer;

import controller.ErrorChange;
import model.game.Game;
import model.game.Player;
import view.EndState;
import view.MarketBuyingState;
import view.StartState;

public class SkipAction extends Action
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7504135483550600131L;

	/**
	 * This action allows the player to skip the turn. 
	 * If the next player is the first of the match, the gameState is changed.
	 * @return true if the action succeeded
	 * @throws IllegalStateException if the player has still main actions to do.
	 */
	@Override
	public boolean takeAction(Game game)
	{
		if(!this.checkAction(game)&&game.getGameState().getClass().equals(StartState.class))
			throw new IllegalStateException("You have to use all the main actions before passing");
		else
		{
			//game.getTimer().cancel();
			//game.getTimer().purge();
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
				if(!game.isLastTurn())
					game.nextState();
				if(game.getGameState().getClass().equals(MarketBuyingState.class)){
					ref=game.getShuffledPlayers();
				}else{
					ref=game.getPlayers();
				}
				nextPlayer=ref.get(0);
				game.setCurrentPlayer(nextPlayer);
				
			}else{
				nextPlayer=ref.get(currentIndex+1);
				game.setCurrentPlayer(nextPlayer);	
			}
			
			if(game.getMainActionCounter()==0)
				game.incrementMainActionCounter();
			
			if(game.getQuickActionCounter()==0){
				game.incrementQuickActionCounter();
			}
			
			if(game.isLastTurn()){
				game.decrementLastRemainingPlayers();
			}
			
			
			game.getTimer().cancel();
			game.setTimer(new Timer());
			game.getTimer().schedule(new DisconnectionTimer(game), Game.DISCONNECTION_TIME);
			
			
			if(game.getPlayers().size()==1){
				game.notifyObservers(new ErrorChange("Player "+game.getCurrentPlayer().getName()+" - "+game.getCurrentPlayer().getPlayerID()+
						", you are the last online player in this match, so the game is finished and you have won!"));
				game.setGameState(new EndState(game.getCurrentPlayer()));
				game.getTimer().cancel();
			}
			
			if(game.getGameState().getClass().equals(StartState.class)){
				game.getPoliticsDeck().drawCard(game.getCurrentPlayer());
			}
			
			super.takeAction(game);
			return true;
		}
		
	}
	
	/**
	 * the check that this action must perform
	 * to ensures that the player is able to skip the turn
	 * @return true if player can skip the turn
	 * 			false otherwise
	 */
	@Override
	public boolean checkAction(Game game) {
		if(game.getMainActionCounter()!=0){
			return false;
		}
		return true;
	}
	
}
